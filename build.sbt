name := "jpos-spark"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies += "io.github.binaryfoo" %% "lagotto" % "0.0.1-SNAPSHOT" % "compile"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.2.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.2.0"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

libraryDependencies += "org.scala-sbt" % "io" % "0.13.6"

resolvers += Resolver.url("typesafe-ivy-repo", url("http://typesafe.artifactoryonline.com/typesafe/releases"))(Resolver.ivyStylePatterns)