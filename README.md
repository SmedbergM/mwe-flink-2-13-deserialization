# mwe-flink-2-13-deserialization
MWE demonstrating a deserialization bug in Flink under Scala 2.13

The bug occurs because, when records are serialized and then deserialized between operators, multiple copies of singleton objects (such as case objects) are created.


# Build and Run

To build a Flink application JAR, just

```sh
sbt clean assembly
```

Then submit this JAR to your favorite Flink cluster.

## Build and Run in IntelliJ

Before running in IntelliJ, you will need to create a run configuration for the `smedbergm.mwe.MWE` main class.
Open "Modify Options" and select "Add dependencies with provided scope to classpath" to give access to the Flink runtime.
