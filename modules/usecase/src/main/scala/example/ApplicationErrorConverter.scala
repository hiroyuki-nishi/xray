package example

import com.typesafe.scalalogging.LazyLogging

import scala.util.Try

object ApplicationErrorConverter extends LazyLogging {
  implicit class ToApplicationError[E](val e: Either[RepositoryError, E]) extends AnyVal {
    def toApplicationError(item: String): Either[ApplicationError, E] =
      e.fold(
        {
          case _: RepositoryUnAuthorizedError => Left(AuthorizedError(item))
          case _: RepositoryNotFoundError     => Left(NotFoundError(item))
          case _: RepositoryTransactionError  => Left(ConflictError(item))
          case e: RepositorySystemError =>
            logger.error(e.getMessage)
            e.printStackTrace()
            Left(InternalServerError(item))
          case e =>
            logger.error(e.getMessage)
            e.printStackTrace()
            Left(InternalServerError(item))
        },
        Right(_)
      )
  }

  implicit class TryToApplicationError[E](val e: Try[E]) extends AnyVal {
    def toApplicationError(item: String): Either[ApplicationError, E] =
      e.fold(
        {
          case e =>
            logger.error(e.getMessage)
            e.printStackTrace()
            Left(InternalServerError(item))
        },
        Right(_)
      )
  }
}
