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
//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText
//    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

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


//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)
//
//        //  Data input buttons
//        val button0: Button = findViewById(R.id.button0)
//        val button1: Button = findViewById(R.id.button1)
//        val button2: Button = findViewById(R.id.button2)
//        val button3: Button = findViewById(R.id.button3)
//        val button4: Button = findViewById(R.id.button4)
//        val button5: Button = findViewById(R.id.button5)
//        val button6: Button = findViewById(R.id.button6)
//        val button7: Button = findViewById(R.id.button7)
//        val button8: Button = findViewById(R.id.button8)
//        val button9: Button = findViewById(R.id.button9)
//        val buttonDot: Button = findViewById(R.id.buttonDot)
//
//        //  Operation Buttons
//        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
//        val buttonDivide = findViewById<Button>(R.id.buttonDevide)
//        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
//        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
//        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)

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
            when {
                newNumber.text.toString() == "-" -> newNumber.setText("")
                newNumber.text.isNotEmpty() -> {
                    val negatedNum = newNumber.text.toString().toDouble() * -1
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
