package example

import example.RepositoryError

trait AccountService {
  def register(id: AccountId): Either[RepositoryError, Unit]
}
