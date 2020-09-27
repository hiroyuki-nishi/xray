import software.amazon.awscdk.core.{Construct, Duration, Stack, StackProps}
import software.amazon.awscdk.services.lambda.{Code, Function, FunctionProps, Runtime, Tracing}
import software.amazon.awscdk.services.sqs.{Queue, QueueProps}

import scala.jdk.CollectionConverters._

class LambdaStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {
  private val presentationPath = "modules/adapter/presentation"

  private val function = new Function(
    this,
    "account-test",
    FunctionProps
      .builder()
      .functionName(s"notify-days-shift-test")
      .code(
        Code.fromAsset(
          s"${presentationPath}/notifydaysshift/target/scala-2.13/notifyDaysShift.jar"
        )
      )
      .runtime(Runtime.JAVA_8)
      .handler("shiftdays.App::handleRequest")
      .timeout(Duration.seconds(180))
      .memorySize(1024)
      .environment(Map("REGION" -> "ap-northeast-1").asJava)
      .tracing(Tracing.ACTIVE)
      .build()
  )

  // 別Stackから渡す方法を探りたい
  new Queue(
    this,
    s"account-test-sqs",
    QueueProps
      .builder()
      .queueName(s"account-test")
      .retentionPeriod(Duration.seconds(1209600))
      .build()
  ).grantSendMessages(function)
}
