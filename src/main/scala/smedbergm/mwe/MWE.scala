package smedbergm.mwe

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import smedbergm.mwe.source.JsonSource

object MWE {
  def main(args: Array[String]): Unit = {
    val streamEnv = StreamExecutionEnvironment.createLocalEnvironment(2)

    val dataFilename = "/sample-data.jsonl"
    val jsonSource = streamEnv.addSource(JsonSource(dataFilename))
      .setParallelism(1)
      .name("json-source")

    val bookStream = jsonSource.process(BookSelector)
      .name("parse-book")

    bookStream.addSink(BookSink)
      .name("log-book")

    streamEnv.execute("mwe")
  }
}
