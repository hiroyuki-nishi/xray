import software.amazon.awscdk.core.{Construct, RemovalPolicy, Stack, StackProps}
import software.amazon.awscdk.services.dynamodb.TableProps
import software.amazon.awscdk.services.dynamodb._

class DynamoDBStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {

  new Table(
    this,
    "MyTable",
    TableProps
      .builder()
      .tableName("sample-group")
      .billingMode(BillingMode.PAY_PER_REQUEST)
      .partitionKey(
        Attribute
          .builder()
          .name("id")
          .`type`(AttributeType.STRING)
          .build()
      )
      .removalPolicy(RemovalPolicy.DESTROY)
      .build()
  )
}
