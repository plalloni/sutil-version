package sutil.version

import org.junit.runner.RunWith
import org.scalatest.{ FunSpec, Matchers }
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class VersionNumberSpec extends FunSpec with Matchers {

  describe("VersionNumber") {

    it("should not allow to be built without numbers") {
      an[IllegalArgumentException] should be thrownBy {
        N()
      }
    }

    it("should print nice version number string") {
      N(1, 2, 3).toString should be ("1.2.3")
      N(1).toString should be ("1")
      N(5, 2).toString should be ("5.2")

    }

    it("should sort in correct order") {
      Seq(N(1, 2, 3), N(2, 1, 1), N(1), N(5, 2), N(1, 2)).sorted should be (Seq(N(1), N(1, 2), N(1, 2, 3), N(2, 1, 1), N(5, 2)))
    }

    it("should not allow negative numbres") {
      an[IllegalArgumentException] should be thrownBy {
        N(-10)
      }
    }

    it("should be possible to pattern match on all numbers") {
      val matches = N(1, 2, 3) match {
        case N(1, 2, 3) ⇒ true
        case _          ⇒ false
      }
      matches should be (true)
    }

    it("should be possible to pattern match on some head") {
      (N(1, 2, 3) match {
        case N(1, 2, _*) ⇒ true
        case _           ⇒ false
      }) should be (true)
    }

    it("should increment correctly last number") {
      N(1, 2, 3).incrementLast should be (N(1, 2, 4))
    }

    it("should increment correctly major number") {
      N(1, 2, 3).increment(VersionNumberPosition.Major) should be (N(2, 2, 3))
    }

    it("should increment correctly minor number") {
      N(1, 2, 3).increment(VersionNumberPosition.Minor) should be (N(1, 3, 3))
    }

    it("should increment correctly fix number") {
      N(1, 2, 3).increment(VersionNumberPosition.Fix) should be (N(1, 2, 4))
    }

    it("should increment correctly some deep number") {
      N(1, 2, 3, 3, 3, 3, 3, 3).incrementAt(5) should be (N(1, 2, 3, 3, 3, 4, 3, 3))
    }

    it("should increment correctly  by another version number") {
      N(1, 2, 3, 3, 3, 3, 3, 3).incrementBy(N(0, 1, 2)) should be (N(1, 3, 5, 3, 3, 3, 3, 3))
    }

    it ("should increment by test") {
      VersionNumber(2, 1) increment Major should be (VersionNumber(3, 1))
      VersionNumber(2, 1) increment Fix should be (VersionNumber(2, 1, 1))
      VersionNumber(2, 1) incrementAt 5 should be (VersionNumber(2, 1, 0, 0, 0, 1))
      VersionNumber(2, 1) incrementBy VersionNumber(0, 1, 1) should be (VersionNumber(2, 2, 1))
    }

  }

}
