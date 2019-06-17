package com.example.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.lang.NumberFormatException
import kotlinx.android.synthetic.main.activity_main.*

private const val CURRENT_OPERATION = "currentOperation"
private const val CURRENT_OPERAND1 = "Operand1"
private const val OPERAND1_STORED = "Operand1_Stored"

class MainActivity : AppCompatActivity() {

    //    Variables to hold the operands and type of calculations.
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState.getBoolean(OPERAND1_STORED, false)) {
            operand1 = savedInstanceState.getDouble(CURRENT_OPERAND1)
        } else {
            operand1 = null
        }
        pendingOperation = savedInstanceState.getString(CURRENT_OPERATION, "")
        operation.text = pendingOperation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            operation.text = pendingOperation
        }

        buttonEquals.setOnClickListener(opListener)
        buttonSubtract.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)

        val negListener = View.OnClickListener {
            val value = newNumber.text.toString()
            when {
                value == "-" || value == "." -> newNumber.setText("")
                newNumber.text.isNotEmpty() -> {
                    val negatedNum = value.toDouble() * -1
                    newNumber.setText(negatedNum.toString())
                }
                else -> newNumber.append("-")
            }
        }

        buttonNeg.setOnClickListener(negListener)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(CURRENT_OPERATION, pendingOperation)
        if (operand1 != null) {
            outState?.putDouble(CURRENT_OPERAND1, operand1!!)
            outState?.putBoolean(OPERAND1_STORED, true)
        }
    }


    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }
            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN // handle attempt to divide by zero
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

}
