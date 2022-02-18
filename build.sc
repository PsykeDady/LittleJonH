import mill._
import mill.scalalib._

object littlejonh extends JavaModule {
  // to use the root as sources directory
  def millSourcePath = super.millSourcePath / os.up
  object test extends Tests with TestModule.Junit4 {
    def ivyDeps = Agg(
      ivy"com.github.sbt:junit-interface:0.13.2"
    )
  }
}
