package com.couchbase;

import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.Select;
import com.couchbase.client.java.query.Statement;
import com.couchbase.spark.japi.CouchbaseSparkContext;
import com.couchbase.spark.sql.DataFrameWriterFunctions;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import scala.collection.immutable.Map;

import static com.couchbase.client.java.query.dsl.Expression.*;
import static com.couchbase.spark.japi.CouchbaseSparkContext.couchbaseContext;

public class Spark {

    private SQLContext sqlContext;
    private JavaSparkContext javaSparkContext;
    private CouchbaseSparkContext couchbaseSparkContext;

    public Spark(JavaSparkContext sc) {
        this.javaSparkContext = sc;
        this.sqlContext = new SQLContext(sc);
        this.couchbaseSparkContext = couchbaseContext(sc);
    }

    public void csvToCouchbase(String csvFilePath) {
        DataFrame df = sqlContext.read()
                .format("com.databricks.spark.csv")
                .option("inferSchema", "true")
                .option("header", "true")
                .load(csvFilePath);
        // Taking only 0.1% for test
        df = df.sample(false, 0.10);
        // Infer the schema uses the integer type for the id but we need a string
        df = df.withColumn("Id", df.col("Id").cast("string"));
        DataFrameWriterFunctions dataFrameWriterFunctions = new DataFrameWriterFunctions(df.write());
        // this option ensure the Id field will be used as key
        Map<String, String> options = new Map.Map1<String, String>("idField", "Id");
        dataFrameWriterFunctions.couchbase(options);
    }

    public void getPopularNames(String gender, int threshold,String dbName) {
        Statement statement = Select.select("Name", "Gender", "SUM(Count) AS Total").from(i(dbName)).where(x("Gender").eq(s(gender))).groupBy(x("Name,Gender")).having(x("SUM(Count)").gte(threshold));
        N1qlQuery query = N1qlQuery.simple(statement);
        this.couchbaseSparkContext
                .couchbaseQuery(query)
                .foreach(queryResult -> System.out.println(queryResult));
    }

}
