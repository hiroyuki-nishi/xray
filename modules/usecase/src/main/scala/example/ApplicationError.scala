package example

sealed class ApplicationError(message: String = null, cause: Throwable = null)
    extends RuntimeException(message, cause)
case class AuthorizedError(item: String, cause: Throwable = null)
    extends ApplicationError(s"example.AuthorizedError: $item", cause)
case class NotFoundError(item: String, cause: Throwable = null)
    extends ApplicationError(s"example.NotFoundError: $item", cause)
case class ConflictError(item: String, cause: Throwable = null)
    extends ApplicationError(s"example.ConflictError: $item", cause)
case class InternalServerError(item: String, cause: Throwable = null)
    extends ApplicationError(s"example.InternalServerError: $item", cause)
