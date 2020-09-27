package example

import example.RepositoryErrorConverter.ToRepositoryError
import example.{RepositoryError, SqsWrapper}

trait AccountRepositoryOnSqs extends SqsWrapper {
  def register(id: AccountId): Either[RepositoryError, Unit] =
    sendMessage(id).toRepositoryError(s"send failed. accountID:$id")
}
