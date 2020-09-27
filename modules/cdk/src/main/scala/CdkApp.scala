import software.amazon.awscdk.core.{App, Environment, StackProps}

object CdkApp {
  private def createStackProps(stackName: String, environment: Environment): StackProps = {
    StackProps
      .builder()
      .stackName(s"$stackName")
      .description(s"$stackName")
      .env(environment)
      .build()
  }

  def main(args: Array[String]): Unit = {
    lazy val environment = Environment.builder().region("ap-northeast-1").build()
    val app              = new App
    new SqsStack(app, "sqs", createStackProps("notify-days-shift-sqs", environment))
    new LambdaStack(app, "lambda", createStackProps("notify-days-shift-lambda", environment))
    app.synth
  }
}
