package example.account

import org.scalamock.scalatest.MockFactory
import org.scalatest.diagrams.Diagrams
import org.scalatest.wordspec.AnyWordSpec

class CreateAccountUseCaseTest extends AnyWordSpec with Diagrams with MockFactory {
  "CreateAccountUseCaseTest" when {
    "test" should {
      "y" in {
        Array(1) == Array(1)
        assert(true)
      }
    }
  }
}
