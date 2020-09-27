package example

import com.amazonaws.xray.interceptors.TracingInterceptor
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model._

import scala.util.Try

trait SqsWrapper {
  protected val queueName: String
  protected val region: Region
  protected lazy val sqsClient: SqsClient =
    SqsClient
      .builder()
      .region(region)
      .overrideConfiguration(
        ClientOverrideConfiguration
          .builder()
          .addExecutionInterceptor(new TracingInterceptor())
          .build()
      )
      .build()

  private def _getQueueUrl(queueName: String): Try[GetQueueUrlResponse] =
    Try {
      sqsClient.getQueueUrl(
        GetQueueUrlRequest
          .builder()
          .queueName(queueName)
          .build()
      )
    }

  private def _receiveMessage(queueUrl: String, count: Int): Try[ReceiveMessageResponse] =
    Try {
      sqsClient.receiveMessage(
        ReceiveMessageRequest
          .builder()
          .maxNumberOfMessages(count)
          .queueUrl(queueUrl)
          .build()
      )
    }

  private def _sendMessage(queueUrl: String, messageBody: String): Try[SendMessageResponse] =
    Try {
      sqsClient.sendMessage(
        SendMessageRequest
          .builder()
          .queueUrl(queueUrl)
          .messageBody(messageBody)
          .build()
      )
    }

  private def _deleteMessage(queueUrl: String, receiptHandle: String): Try[DeleteMessageResponse] =
    Try {
      sqsClient.deleteMessage(
        DeleteMessageRequest
          .builder()
          .queueUrl(queueUrl)
          .receiptHandle(receiptHandle)
          .build()
      )
    }

  protected def receiveMessage(count: Int): Try[ReceiveMessageResponse] =
    for {
      url      <- _getQueueUrl(queueName)
      response <- _receiveMessage(url.queueUrl, count)
    } yield response

  def sendMessage(messageBody: String): Try[Unit] =
    for {
      url <- _getQueueUrl(queueName)
      _   <- _sendMessage(url.queueUrl, messageBody)
    } yield ()

  protected def deleteMessage(receiptHandle: String): Try[Unit] =
    for {
      url <- _getQueueUrl(queueName)
      _   <- _deleteMessage(url.queueUrl, receiptHandle)
    } yield ()
}
