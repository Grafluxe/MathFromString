import scala.util.matching.Regex

/**
  * @author Leandro Silva
  * Parses a string as a mathematical expression.
  */

package com.grafluxe {
  object MathFromString {
    private val expectedStr: Regex = """^[\d-(.][\d+\-*/().]+[\d)]$""".r
    private val unexpectedOps: Regex = """(?!\*\*)[+\-*/][+/*]\d|[+\-*/]{3,}""".r
    private val parens: Regex = """\(([^(]+?)\)""".r
    private val hasMulDiv: Regex = """[/*]""".r
    private val mulDiv: Regex = """(-?[\d.]+)([/*]*?)(-?[\d.]+)(.*)""".r
    private val hasAddSub: Regex = """(?!^-)-|\+""".r
    private val addSub: Regex = """(-?[\d.]+)([+-])(-?[\d.]+)(.*)""".r

    /**
      * Parses a string as a mathematical expression. Supports addition, subtraction,
      * division, multiplication, and exponentiation.
      * @throws Exception The string at/near "&lt;value>" is malformed.
      * @throws Exception The string at/near "&lt;value>" has a malformed operator.
      * @param str        The string to parse.
      * @return           The end total.
      */
    def parse(str: String): Double = {
      var value: String = str

      if (expectedStr.findFirstIn(value).isEmpty) {
        throw new Exception(s"""The string at/near "$value" is malformed.""")
      }

      if (unexpectedOps.findFirstIn(value).isDefined) {
        throw new Exception(s"""The string at/near "$value" has a malformed operator.""")
      }

      // Do math inside parentheses first
      while (value.indexOf("(") > -1) {
        val nested = parens.findAllIn(value)
        value = value.replace(nested.group(0), parse(nested.group(1)).toString)
      }

      // Division and multiplication operators are done first
      while (hasMulDiv.findFirstIn(value).isDefined) {
        value = mulDiv.replaceSomeIn(value, matcher)
      }

      while (hasAddSub.findFirstIn(value).isDefined) {
        value = addSub.replaceSomeIn(value, matcher)
      }

      value.toDouble
    }


    /**
      * @throws Exception The value "&lt;number>" is not a valid number.
      * @param matched    The matched expression.
      * @return           An updated math string.
      */
    private def matcher(matched: Regex.Match): Option[String] = {
      var num1: Double = 0.0
      var num2: Double = 0.0

      try {
        num1 = matched.group(1).toDouble
      } catch {
        case err: Exception => throw new Exception(s"""The value "${matched.group(1)}" is not a valid number.""")
      }

      try {
        num2 = matched.group(3).toDouble
      } catch {
        case err: Exception => throw new Exception(s"""The value "${matched.group(3)}" is not a valid number.""")
      }

      Option(
        doMath(num1, matched.group(2), num2).toString + matched.group(4)
      )
    }

    /**
      * Parses math.
      * @param num1     The first number of the equation.
      * @param operator The operator.
      * @param num2     The second number of the equation.
      * @return         The equations value.
      */
    private def doMath(num1: Double, operator: String, num2: Double): Double = {
      operator match {
        case "/" => num1 / num2
        case "*" => num1 * num2
        case "+" => num1 + num2
        case "-" => num1 - num2
        case "**" => Math.pow(num1, num2)
      }
    }

  }
}
