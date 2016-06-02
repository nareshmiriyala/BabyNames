# Couchbase Spark vs RxJava Example

Demonstrate transforming large amounts of raw data to JSON and storing into Couchbase using RxJava and the Couchbase SDK or Apache Spark and the Couchbase Connector.

## Software Requirements

* Apache Spark 1.6+
* Java Developer Kit 1.8+
* Couchbase Server 4.1+
* Maven

## Data Requirements

This uses the [US Baby Names](https://www.kaggle.com/kaggle/us-baby-names) sample from Kaggle.  The data is not included and should first be downloaded to a **data** directory at the root of the project.

## Usage

From the Command Prompt or Terminal, execute the following from within the project directory:

```
mvn package
```

This will build two JAR files, one containing the Couchbase dependencies and the other without.  We only care about the bundle that contains the dependencies.

From within the root of your project execute the following, replacing any pathing information with that of your own:

```
/path/to/apache/spark/bin/spark-submit --class "com.couchbase.SimpleApp" target/simple-project-1.0-jar-with-dependencies.jar
```

If using the [US Baby Names](https://www.kaggle.com/kaggle/us-baby-names) sample, it will take a while as there are 1,825,433 documents being transformed from CSV and inserted into your **default** bucket.
