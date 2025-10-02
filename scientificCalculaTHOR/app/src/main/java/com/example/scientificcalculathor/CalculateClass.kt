package com.example.scientificcalculathor

import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class CalculateClass() : CalculatorInput(){
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun convertToPostfix(myString : MutableList <Any>): MutableList<Double> {
        val output = mutableListOf<Any>()
        val operatorStack = mutableListOf<String>()

        val precedence = mapOf(
            "+" to 2, "-" to 2,
            "x" to 3, "÷" to 3, "/" to 3, "*" to 3,
            "x^y" to 4,
            "%" to 5, "1/x" to 5, "x!" to 5, "√x" to 5,
            "log" to 5, "ln" to 5,
            "sin" to 5, "cos" to 5, "tan" to 5,
            "arcsin" to 5, "arccos" to 5, "arctan" to 5
        )

        val rightAssociative = setOf(
            "x^y", "√x", "log", "ln", "sin", "cos", "tan",
            "arcsin", "arccos", "arctan", "1/x"
        )

        for (token in myString) {
            when (token) {
                is Double -> output.add(token)
                is String -> {
                    val prec = precedence[token] ?: 0
                    val isRightAssoc = token in rightAssociative

                    while (operatorStack.isNotEmpty()) {
                        val top = operatorStack.last()
                        val topPrec = precedence[top] ?: 0

                        // aturan shunting yard
                        if ((!isRightAssoc && prec <= topPrec) || (isRightAssoc && prec < topPrec)) {
                            output.add(operatorStack.removeLast())
                        } else break
                    }
                    operatorStack.add(token)
                }
            }
        }

        // pindahin sisa operator ke output
        while (operatorStack.isNotEmpty()) {
            output.add(operatorStack.removeLast())
        }

        return calculatePostfix(output)
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun calculatePostfix(postfixList: MutableList<Any>): MutableList<Double> {
        val stack : MutableList<Double> = mutableListOf()
        for (i in postfixList){
            when (i) {
                is Double -> stack.add(i)
                is Int -> stack.add(i.toDouble())
                is String -> {
                    when(i){
                        "+","-","x","*","/","÷","x^y" -> {
                            if (stack.size < 2) continue
                            val x = stack.removeLast()
                            val y = stack.removeLast()
                            stack.add(requireTwoValues(y, x, i))
                        }
                        "1/x", "x!", "√x",  "log", "ln", "sin", "cos", "tan", "arcsin", "arccos", "arctan" -> {
                            if (stack.isEmpty()) continue
                            val x = stack.removeLast()
                            stack.add(requireOneValue(x, i))
                        }
                    }
                }
            }
        }
        return stack
    }
    fun requireTwoValues(x: Double, y: Double, operator: String): Double {
        when (operator){
            "+" -> {
                return Addition(x, y)
            }
            "-" -> {
                return Substraction(x, y)
            }
            "÷", "/" -> {
                return Division(x, y)
            }
            "x", "*" -> {
                return Multiply(x, y)
            }
            "x^y" -> {
                return PowerOf(x, y)
            }
        }

        return 0.0
    }

    fun requireOneValue(x : Double, operator: String) : Double{
        when (operator){
            "log" -> return Log(x)
            "ln" -> return Ln(x)
            "%" -> return Percentage(x)
            "x!" -> return Factorial(x)
            "√x" -> return SqrtOperator(x)
            "1/x" -> return Reciprocal(x)
            //Trigonometri
            "sin" -> return TrigSin(x)
            "cos" -> return TrigCos(x)
            "tan" -> return TrigTan(x)
            //Trigonometri Inverse
            "arcsin" -> return TrigArcsin(x)
            "arccos" -> return TrigArccos(x)
            "arctan" -> return TrigArctan(x)
        }
        return 0.0
    }

    //ini buwtuh 2 angka:
    fun Multiply(x:Double, y: Double) : Double{
        return  x * y
    }

    fun Division(x:Double, y:Double) : Double{
        return  x / y
    }

    fun Addition(x:Double, y:Double) : Double{
        return x + y
    }

    fun Substraction(x : Double, y : Double) : Double{
        return x - y
    }

    fun PowerOf(x : Double, y : Double): Double {
        return x.pow(y)
    }

    //butuh 1 angka ajah
    fun Percentage(number: Double) : Double {
        return  number / 100
    }

    fun SqrtOperator(number: Double) : Double {
        return sqrt(number)
    }

    fun Reciprocal(number: Double) : Double {
        return 1/(number)
    }

    fun Factorial(number: Double) : Double{
        require(number >= 0)
        var result : Double = 1.0
        for (i in 1 .. number.toInt()){
            result *= i
        }
        return result
    }

    fun Log(number: Double) : Double{
        return log10(number)
    }

    fun Ln(number: Double) : Double{
        return ln(number)
    }

    fun TrigSin(number: Double) : Double{
        return sin(number)
    }

    fun TrigCos(number: Double) : Double{
        return cos(number)
    }

    fun TrigTan(number: Double) : Double{
        return tan(number)
    }

    fun TrigArcsin(number: Double) : Double{
        return asin(number)
    }

    fun TrigArccos(number: Double) : Double{
        return acos(number)
    }

    fun TrigArctan(number: Double) : Double{
        return atan(number)
    }
}

//@TestOnly
//fun main(){
//    val ella = CalculateClass()
//    print(ella.TrigArctan(1.0))
//}