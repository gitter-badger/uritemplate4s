// build.sc
import mill._
import mill.scalalib._
import mill.scalajslib._

object CommonSettings {
  val scalacOptions = List(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    //  "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates",            // Warn if a private member is unused.
    "-Ywarn-value-discard"               // Warn when non-Unit expression results are unused.
  )
}

trait UritemplateModule extends SbtModule {

  def platformSegment: String

  def scalaVersion = "2.12.4"

  def ivyDeps = Agg(
    ivy"com.lihaoyi::fastparse:1.0.0"
  )

  def sources = T.sources(
    millSourcePath / platformSegment / "src" / "main",
    millSourcePath / "shared" / "src" / "main"
  )

  def resources = T.sources(
    millSourcePath / platformSegment / "src" / "main" / "resources",
    millSourcePath / "shared" / "src" / "main" / "resources"
  )

  def scalacOptions = CommonSettings.scalacOptions

  trait Tests extends super.Tests {
    def ivyDeps = Agg(
      ivy"org.typelevel::cats-core:1.0.1",
      ivy"org.typelevel::cats-kernel:1.0.1",
      ivy"org.typelevel::cats-macros:1.0.1",
      ivy"com.lihaoyi::utest:0.6.0",
      ivy"io.circe::circe-core:0.9.1",
      ivy"io.circe::circe-generic:0.9.1",
      ivy"io.circe::circe-parser:0.9.1"
    )

    def testFrameworks = Seq("utest.runner.Framework")

    def sources = T.sources(
      millSourcePath / platformSegment / "src" / "test",
      millSourcePath / "shared" / "src" / "test"
    )

    def resources = T.sources(
      millSourcePath / platformSegment / "src" / "test" / "resources",
      millSourcePath / "shared" / "src" / "test" / "resources"
    )

    def scalacOptions = CommonSettings.scalacOptions
  }
}

object uritemplateJvm extends UritemplateModule {
  def platformSegment = "jvm"

  def millSourcePath = build.millSourcePath / "core"

  object test extends Tests
}

object uritemplateJs extends ScalaJSModule with UritemplateModule {
  def platformSegment = "js"

  def scalaJSVersion = "0.6.22"

  def millSourcePath = build.millSourcePath / "core"

  object test extends Tests
}
