package com.couchbase;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SimpleApp {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("Simple Application")
                .setMaster("local[*]")
                .set("com.couchbase.bucket.test", "");
        JavaSparkContext sc = new JavaSparkContext(conf);
        Spark spark = new Spark(sc);
//        spark.csvToCouchbase("C:\\NareshCode\\BabyNames\\src\\main\\resources\\NationalNames.csv");
        spark.getPopularNames("M", 50000);
        /*RxJava rxJava = new RxJava("http://localhost:8091", "default", "/Users/nraboy/Desktop/couchbase-spark-vs-rxjava-example/data/NationalNames.csv");
        //rxJava.csvToCouchbase();
        rxJava.getPopularNames("F", 5000);*/
        System.exit(0);
    }
}
