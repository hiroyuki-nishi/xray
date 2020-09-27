import Dependencies._

name := "xray"

lazy val commonSettings = Seq(
  scalaVersion := "2.13.3",
  scalacOptions := Seq(
    "-deprecation",
    "-feature",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Xfatal-warnings"
  ),
  scalafmtOnCompile in ThisBuild := true,
  test in assembly := {}
)

val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case _ => MergeStrategy.first
  },
  assemblyJarName in assembly := s"${name.value}.jar",
  publishArtifact in(Compile, packageBin) := false,
  publishArtifact in(Compile, packageSrc) := false,
  publishArtifact in(Compile, packageDoc) := false
)

lazy val root = (project in file("."))
  .aggregate(
    presentation,
    usecase,
    infrastructure
  )
  .settings(commonSettings: _*)
  .settings(
    commonSettings,
    publishArtifact := false
  )
  .settings(
    commands += Command.command("assemblyAll") { state =>
      "notifyDaysShift / assembly" ::
        state
    }
  )

lazy val cdk = (project in file("modules/cdk"))
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= cdkDependencies
  )

// domain
lazy val domain = (project in file("modules/domain"))
  .settings(commonSettings: _*)

// presentation
lazy val presentation = (project in file("modules/adapter/presentation"))
  .aggregate(
    notifyDaysShift
  )
  .settings(
    publishArtifact := false
  )

lazy val notifyDaysShift = (project in file("modules/adapter/presentation/notifydaysshift"))
  .dependsOn(usecase, service, infraSQS)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= presentationDependencies
  )

// usecase
lazy val usecase = (project in file("modules/usecase"))
  .dependsOn(service)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= usecaseDependencies
  )

// service
lazy val service = (project in file("modules/service"))
  .settings(commonSettings: _*)
  .dependsOn(domain)
  .settings(
    libraryDependencies ++= circeDependencies
  )

// infrastructure
lazy val infrastructure = (project in file("modules/adapter/infrastructure"))
  .settings(commonSettings: _*)
  .aggregate(
    infraS3,
    infraSQS
  )

lazy val infraS3 = (project in file("modules/adapter/infrastructure/s3"))
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(libraryDependencies ++= s3Dependencies)
  .settings(
    parallelExecution in Test := false
  )
  .dependsOn(domain)

lazy val infraSQS = (project in file("modules/adapter/infrastructure/sqs"))
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(libraryDependencies ++= sqsDependencies)
  .settings(
    parallelExecution in Test := false
  )
  .dependsOn(domain, service)
