import software.amazon.awscdk.core.{Construct, RemovalPolicy, Stack, StackProps}
import software.amazon.awscdk.services.s3.{Bucket, BucketProps}

class S3Stack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {
  new Bucket(
    this,
    "notify-days-shift",
    BucketProps
      .builder()
      .bucketName("notify-days-shift")
      .removalPolicy(RemovalPolicy.DESTROY)
      .build()
  )
}
