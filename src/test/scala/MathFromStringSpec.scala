import org.scalatest._
import com.grafluxe.MathFromString

class MathFromStringSpec extends FlatSpec with Matchers {

  it should "pass as 123.678+0.01 = 123.688" in {
    MathFromString.parse("123.678+0.01").shouldBe(123.688)
  }

  it should "pass as 123.678+0.1 = 123.77799999999999" in {
    MathFromString.parse("123.678+0.1").shouldBe(123.77799999999999)
  }

  it should "pass as (2+8)*2+(100.34-10+(2+5.4)) = 117.74000000000001" in {
    MathFromString.parse("(2+8)*2+(100.34-10+(2+5.4))").shouldBe(117.74000000000001)
  }

  it should "pass as (2*2)*3*(4*4) = 192" in {
    MathFromString.parse("(2*2)*3*(4*4)").shouldBe(192)
  }

  it should "pass as (2*8)*2*(100.34*10*(2*5.4)) = 346775.04000000004" in {
    MathFromString.parse("(2*8)*2*(100.34*10*(2*5.4))").shouldBe(346775.04000000004)
  }

  it should "pass as 2*-3 = -6.0" in {
    MathFromString.parse("2*-3").shouldBe(-6.0)
  }

  it should "pass as 2**3 = 8.0" in {
    MathFromString.parse("2**3").shouldBe(8.0)
  }

  it should "pass as (2+8)*2 = 20.0" in {
    MathFromString.parse("(2+8)*2").shouldBe(20.0)
  }

  it should "pass as -2+8 = 6.0" in {
    MathFromString.parse("-2+8").shouldBe(6.0)
  }

  it should "pass as 2+-8 = -6.0" in {
    MathFromString.parse("2+-8").shouldBe(-6.0)
  }

  it should "pass as -2+-8 = -10.0" in {
    MathFromString.parse("-2+-8").shouldBe(-10.0)
  }

  it should "fail as (2+8)*2+(100.3.4-10+(2+5.4)) is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("(2+8)*2+(100.3.4-10+(2+5.4))")
    } should have message """The value "100.3.4" is not a valid number."""
  }

  it should "fail as 2+8.0.0 is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("2+8.0.0")
    } should have message """The value "8.0.0" is not a valid number."""
  }

  it should "fail as +2+8 is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("+2+8")
     } should have message """The string at/near "+2+8" is malformed."""
  }

  it should "fail as /2+8 is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("/2+8")
     } should have message """The string at/near "/2+8" is malformed."""
  }

  it should "fail as *2+8 is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("*2+8")
     } should have message """The string at/near "*2+8" is malformed."""
  }

  it should "fail as 2++8 is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("2++8")
     } should have message """The string at/near "2++8" has a malformed operator."""
  }

  it should "fail as 2+/8 is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("2+/8")
     } should have message """The string at/near "2+/8" has a malformed operator."""
  }

  it should "fail as 2+*8 is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("2+*8")
     } should have message """The string at/near "2+*8" has a malformed operator."""
  }

  it should "fail as 1+(1) is an invalid equation" in {
    the [Exception] thrownBy {
      MathFromString.parse("1+(1)")
     } should have message """The string at/near "1" is malformed."""
  }

}
