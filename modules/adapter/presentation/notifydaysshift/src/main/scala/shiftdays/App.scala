package shiftdays

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.typesafe.scalalogging.LazyLogging
import example.account.CreateAccountUseCase
import example.{AccountRepositoryOnSqs, AccountService}
import software.amazon.awssdk.regions.Region

class App extends RequestHandler[SQSEvent, Unit] with LazyLogging {
  lazy val REGION: Region = Region.of(sys.env.getOrElse("REGION", ""))

  val createAccountUseCase = new CreateAccountUseCase {
    override protected val accountRepository: AccountService = new AccountService
      with AccountRepositoryOnSqs {
      protected lazy val queueName: String = "account-test"
      protected lazy val region: Region    = REGION
    }
  }

  override def handleRequest(input: SQSEvent, context: Context): Unit = {
    (for {
      _ <- createAccountUseCase.run()
    } yield ()) match {
      case Right(_) => ()
      case Left(e)  => logger.error(e.getMessage)
    }
  }
}
