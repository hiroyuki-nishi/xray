package example.group

import com.typesafe.scalalogging.LazyLogging
import example.{DynamoDBWrapper, RepositoryError, RepositorySystemError}
import software.amazon.awssdk.services.dynamodb.model.{
  AttributeValue,
  GetItemRequest,
  GetItemResponse
}

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

trait GroupRepositoryOnDynamoDB extends DynamoDBWrapper with LazyLogging {
  override val tableName: String = "sample-group"
  val AttrGroupId                = "id"
  lazy val partitionKeyName      = ":gid"

  def findBy(group: Group): Either[RepositoryError, Group] =
    (for {
      item <- getItem(
        GetItemRequest
          .builder()
          .key(
            Map(AttrGroupId -> AttributeValue.builder().s(group.id.value).build()).asJava
          )
          .tableName(tableName)
          .build()
      )
      entity <- record2Entity(item)
    } yield entity) match {
      case Success(value) => Right(value)
      case Failure(e)     => Left(RepositorySystemError(s"group: $group", e))
    }

  private def record2Entity(item: GetItemResponse): Try[Group] =
    Try {
      item2Group(item)
    }

  private def item2Group(item: GetItemResponse): Group = {
    val i = item.item()
    println(item)
    Group(GroupId(i.get(AttrGroupId).s()))
  }
}
