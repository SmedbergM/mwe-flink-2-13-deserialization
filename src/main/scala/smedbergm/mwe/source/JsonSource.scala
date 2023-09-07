package smedbergm.mwe.source

import io.circe.Json
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}

import java.io.InputStream

case class JsonSource(resourceName: String) extends RichSourceFunction[Json] {

  @transient
  private lazy val byteStream: InputStream = getClass.getResourceAsStream(resourceName)

  override def run(ctx: SourceFunction.SourceContext[Json]): Unit = {
    scala.io.Source.fromInputStream(byteStream).getLines().foreach { line =>
      io.circe.parser.parse(line).foreach { json =>
        ctx.collect(json)
      }
    }
  }

  override def cancel(): Unit = {
    byteStream.close()
  }
}
