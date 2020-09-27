import software.amazon.awscdk.core.{Construct, Duration, Stack, StackProps}
import software.amazon.awscdk.services.sqs.{Queue, QueueProps}

class SqsStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {

  // SQS
  new Queue(
    this,
    s"account-test",
    QueueProps
      .builder()
      .queueName(s"account-test")
      .retentionPeriod(Duration.seconds(1209600))
      .build()
  )
}
