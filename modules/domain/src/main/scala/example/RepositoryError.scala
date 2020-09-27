package example

sealed class RepositoryError(message: String = null, cause: Throwable = null)
    extends RuntimeException(message, cause)
case class RepositoryUnAuthorizedError(item: String, cause: Throwable = null)
    extends RepositoryError(s"RepositoryUnAuthorizedError: $item", cause)
case class RepositoryNotFoundError(item: String, cause: Throwable = null)
    extends RepositoryError(s"RepositoryNotFoundError: $item", cause)
case class RepositoryTransactionError(item: String, cause: Throwable = null)
    extends RepositoryError(s"RepositoryTransactionError: $item", cause)
case class RepositorySystemError(item: String, cause: Throwable = null)
    extends RepositoryError(s"RepositorySystemError: $item", cause)
