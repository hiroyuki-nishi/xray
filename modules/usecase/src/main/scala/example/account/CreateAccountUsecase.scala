package example.account

import example.ApplicationErrorConverter._
import example.{AccountId, AccountService, ApplicationError}

trait CreateAccountUseCase {
  protected val accountRepository: AccountService

  def run(): Either[ApplicationError, Unit] = {
    // TODO: nishi EFFでユースケースを表現する
    accountRepository.register(AccountId("test")).toApplicationError("error")
  }
}
