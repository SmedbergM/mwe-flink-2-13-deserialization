package smedbergm.mwe

import com.typesafe.scalalogging.LazyLogging
import io.circe.{Decoder, Json}
import io.circe.generic.semiauto._
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.util.Collector

case class Book(author: String, title: String, isbn: Option[String], price: BigDecimal)

object Book {
  implicit val decoder: Decoder[Book] = deriveDecoder
}

case object BookSelector extends ProcessFunction[Json, Book] {
  override def processElement(message: Json,
                              ctx: ProcessFunction[Json, Book]#Context,
                              out: Collector[Book]): Unit = {
    message.as[Book].foreach(out.collect)
  }
}

case object BookSink extends SinkFunction[Book] with LazyLogging {
  override def invoke(book: Book,
                      context: SinkFunction.Context): Unit = {
    try {
      book.isbn.fold {
        logger.info("{}: {} ${} (no ISBN)", book.author, book.title, book.price)
      } { isbn =>
        logger.info("{}: {} (ISBN {}) ${}", book.author, book.title, isbn, book.price)
      }
    } catch { case _: NoSuchElementException =>
      logger.warn(
        "Book.isbn: {} with identityHashCode {} reports isEmpty {}; true None is {}",
        book.isbn, System.identityHashCode(book.isbn), book.isbn.isEmpty, System.identityHashCode(None)
      )
    }
  }
}
