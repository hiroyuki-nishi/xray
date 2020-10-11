package shiftdays

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.typesafe.scalalogging.LazyLogging
import example.group.{GroupRepository, GroupRepositoryOnDynamoDB, GroupUsecase}
import software.amazon.awssdk.regions.Region

class App extends RequestHandler[SQSEvent, Unit] with LazyLogging {
  lazy val REGION: Region = Region.of(sys.env.getOrElse("REGION", ""))

  val groupUseCase: GroupUsecase = new GroupUsecase {
    override protected val groupRepository: GroupRepository = new GroupRepository
      with GroupRepositoryOnDynamoDB {
      override protected val region: Region = REGION
    }
  }

  override def handleRequest(input: SQSEvent, context: Context): Unit = {
    (for {
      g <- groupUseCase.run()
    } yield g) match {
      case Right(v) => println(v)
      case Left(e)  => logger.error(e.getMessage)
    }
  }
}
