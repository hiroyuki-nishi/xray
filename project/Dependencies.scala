import sbt._

object Dependencies {
  // aws
  lazy val awsSdkV2Version = "2.13.70"
  lazy val awsS3 = "software.amazon.awssdk" % "s3" % awsSdkV2Version
  lazy val awsSqs = "software.amazon.awssdk" % "sqs" % awsSdkV2Version
  lazy val awsLambdaJavaCore = "com.amazonaws" % "aws-lambda-java-core" % "1.2.1"
  lazy val awsJavaEvents = "com.amazonaws" % "aws-lambda-java-events" % "2.2.7"

  // x-ray
  lazy val xraySdkVersion = "2.7.1"
  lazy val awsXraySdkCore = "com.amazonaws" % "aws-xray-recorder-sdk-core" % xraySdkVersion
  lazy val awsXraySdkV2 = "com.amazonaws" % "aws-xray-recorder-sdk-aws-sdk-v2" % xraySdkVersion
  lazy val xrayDependencies = Seq(
    awsXraySdkCore,
    awsXraySdkV2
  )

  // circe
  lazy val circeVersion = "0.12.3"
  lazy val circeCore = "io.circe" %% "circe-core" % circeVersion
  lazy val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  lazy val circeParser = "io.circe" %% "circe-parser" % circeVersion
  lazy val circeDependencies = Seq(
    circeCore,
    circeGeneric,
    circeParser
  )


  // log
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

  // test
  lazy val scalaTestVersion = "3.2.0"

  lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest-wordspec" % scalaTestVersion % "test",
    "org.scalamock" %% "scalamock" % "4.4.0" % Test
  )

  lazy val awsCdkVersion = "1.59.0"
  lazy val cdkDependencies = Seq(
    "software.amazon.awscdk" % "core" % awsCdkVersion,
    "software.amazon.awscdk" % "lambda" % awsCdkVersion,
    "software.amazon.awscdk" % "s3" % awsCdkVersion,
    "software.amazon.awscdk" % "events-targets" % awsCdkVersion
  )

  lazy val presentationDependencies: Seq[ModuleID] = Seq(
    awsLambdaJavaCore,
    awsJavaEvents,
    awsSqs
  ) ++ circeDependencies ++ testDependencies

  lazy val usecaseDependencies: Seq[ModuleID] = Seq(
    scalaLogging
  ) ++ testDependencies


  lazy val s3Dependencies: Seq[ModuleID] = Seq(
    awsS3,
    scalaLogging
  ) ++ testDependencies ++ circeDependencies

  lazy val sqsDependencies: Seq[ModuleID] = Seq(
    awsSqs,
    scalaLogging
  ) ++ testDependencies ++ circeDependencies ++ xrayDependencies
}
