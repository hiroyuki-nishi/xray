package example

import com.typesafe.scalalogging.LazyLogging

import scala.util.Try

object RepositoryErrorConverter extends LazyLogging {
  implicit class ToRepositoryError[E](val e: Try[E]) extends AnyVal {
    def toRepositoryError(item: String): Either[RepositoryError, E] =
      e.fold(
        {
          case e =>
            logger.error(e.getMessage)
            Left(RepositorySystemError(item))
        },
        Right(_)
      )
  }
}
