package example

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.{GetItemRequest, GetItemResponse}

import scala.util.Try

trait DynamoDBWrapper {
  protected val region: Region
  protected val tableName: String
  protected lazy val dynamoDBClient: DynamoDbClient = DynamoDbClient
    .builder()
    .region(region)
    .build()

  protected def getItem[E](request: GetItemRequest): Try[GetItemResponse] =
    Try(dynamoDBClient.getItem(request))
}
