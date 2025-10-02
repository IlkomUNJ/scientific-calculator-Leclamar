package com.example.scientificcalculathor

import android.os.Build
import androidx.annotation.RequiresApi
import org.jetbrains.annotations.TestOnly

open class CalculatorInput(
    protected var myString: MutableList<Any> = mutableListOf(),
    protected var newNumber : String = ""
) {
    val rightAssociative = setOf(
        "x^y", "√x","1/x",
        "log", "ln",
        "sin", "cos", "tan",
        "arcsin", "arccos", "arctan"
    )
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun inputHandler(newInput: String, myString: String?) : String {
        var myoutput = myString
        if (newInput.toDoubleOrNull() != null) {
            this.newNumber += newInput
        } else {
            if (newInput == "=") {
                if (this.newNumber.isNotEmpty()) {
                    this.myString.add(this.newNumber.toDouble())
                    this.newNumber = ""
                }
                return handleEqual()
            } else {
                if (myString?.isNotEmpty() == true && this.newNumber.isNotEmpty() && newInput in rightAssociative){
                    myoutput += "x"
                }
                if (newInput in rightAssociative && this.newNumber.isEmpty()) {
                    myoutput += "1"
                }
                addStackNumber(newInput)
            }
        }

        // validasi input spesial macam aljabar, C, AC, desimal
        when(newInput){
            "x^y" -> return (myString ?: "") + "^"
            "1/x" -> return (myString ?: "") + "1/"
            "√x" -> return (myString ?: "") + "√"
            "AC" -> {
                deleteAll()
                return ""
            }
            "C" -> {
                if (myString?.isEmpty() == false){
                    deletaChar()
                    return myString.dropLast(1)
                } else{
                    return ""
                }
            }
        }
        return myoutput + newInput
    }

    private fun addStackNumber(operator : String){


        if (this.newNumber.isNotEmpty()) {
            this.myString.add(this.newNumber.toDouble())
            this.newNumber = ""
        } else {
            if (operator in rightAssociative) {
                //auto add (khusus), jangan lupa tambah juga di inputHandler
                this.myString.add(1.0)
            }
        }

        if (operator in rightAssociative) {
            if (this.myString.isNotEmpty() && this.myString.last() is Double) {
                //auto add (khusus), jangan lupa tambah juga di inputHandler
                this.myString.add("x")
            }
            this.myString.add(operator)
        } else {
            if (this.myString.isEmpty()) {
                if (operator == "-") {
                    this.newNumber = "-" // ini buat bisa nginput angka negatif
                    return
                } else {
                    this.myString.add(0.0)
                }
            }
            this.myString.add(operator)
        }
    }

    private fun deleteAll(){
        this.myString = mutableListOf()
        this.newNumber = ""
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun deletaChar(){
        this.myString.removeLast()
    }

    private fun formatResult(list: MutableList<Double>): String {
        if (list.isEmpty()) return "0"
        val value = list.last()
        return if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun handleEqual(): String {
        val ella = CalculateClass()
        // pastikan kalau ada angka pending, sudah masuk (inputHandler juga memanggil ini, tapi aman double-check)
        if (newNumber.isNotEmpty()) {
            this.myString.add(this.newNumber.toDouble())
            this.newNumber = ""
        }
        val resultList = ella.convertToPostfix(this.myString)
        return formatResult(resultList)
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@TestOnly
fun main(){
    var myString : String? = ""
    val ella = CalculatorInput()
    myString = ella.inputHandler("1", myString)
    myString = ella.inputHandler("sin", myString)
    myString = ella.inputHandler("90", myString)
    myString = ella.inputHandler("=", myString)

    print(myString)
}