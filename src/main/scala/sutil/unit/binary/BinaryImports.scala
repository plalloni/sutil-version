package sutil.unit.binary

import sutil.Imports._
import scala.math.{ BigDecimal ⇒ BigDec }
import scalaz.Scalaz._

trait BinaryImports {

  private val units = Seq("B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB")

  private val factors = units.zip(powers(2, 0, 10)).map(_.swap).reverse.view

  def human(number: BigInt): (BigDec, String) =
    factors
      .map(factor ⇒ (BigDec(number) / BigDec(factor._1), factor._2))
      .find(value ⇒ value._1 >= 1)
      .getOrElse(BigDec(0) -> "B")

  def humans(number: BigInt, decimals: Int = 1): String =
    human(number) |> (value ⇒ ("%1." + decimals + "f %s") format (value._1, value._2))

}