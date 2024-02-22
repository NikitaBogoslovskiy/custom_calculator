package com.example.custom_calculator.viewmodel

import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableField
import kotlin.math.floor

enum class Operation {
    ADD, SUB, PROD, DIV
}

class Calculator {
    private var lastNumber: Float = 0.0f
    private var lastOperation: Operation = Operation.ADD
    private var opWasExecuted: Boolean = false

    private var currentString: String = "0"
    var display = ObservableField(currentString)

    fun addChar(view: View) {
        if (view !is TextView) {
            return
        }

        if (currentString == "0") {
            currentString = ""
        }

        if (opWasExecuted) {
            currentString = ""
            opWasExecuted = false
        }

        val char = view.text.toString()
        if (currentString == "" && char == ".") {
            currentString = "0"
        }
        currentString += char
        display.set(currentString)
    }

    private fun execLastOp() {
        when (lastOperation) {
            Operation.ADD -> lastNumber += currentString.toFloat()
            Operation.SUB -> lastNumber -= currentString.toFloat()
            Operation.PROD -> lastNumber *= currentString.toFloat()
            Operation.DIV -> lastNumber /= currentString.toFloat()
        }
        currentString = if ((lastNumber - floor(lastNumber)) == 0.0f) {
            lastNumber.toLong().toString()
        } else {
            lastNumber.toString()
        }
        display.set(currentString)
        opWasExecuted = true
    }

    fun addOp(view: View) {
        execLastOp()
        lastOperation = Operation.ADD
    }

    fun subOp(view: View) {
        execLastOp()
        lastOperation = Operation.SUB
    }

    fun prodOp(view: View) {
        execLastOp()
        lastOperation = Operation.PROD
    }

    fun divOp(view: View) {
        execLastOp()
        lastOperation = Operation.DIV
    }

    fun equalOp(view: View) {
        execLastOp()
        lastNumber = 0.0f
        lastOperation = Operation.ADD
    }

    fun delOp(view: View) {
        currentString = "0"
        lastNumber = 0.0f
        lastOperation = Operation.ADD
        display.set(currentString)
    }
}