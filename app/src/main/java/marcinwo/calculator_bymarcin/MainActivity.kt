package marcinwo.calculator_bymarcin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1Stored"

class MainActivity : AppCompatActivity() {
    // Variabels to hold the operands and type of calculation
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listener = View.OnClickListener { v ->
            val b =
                v as Button //(nie kazdy "views" maja "text" - zanim odniesiemy sie do wlasciwosci Text wiec musimy castowac
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
            val op = (v as Button).text.toString() // castuje i czyta tekst przypisany do string z EditText wiget
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            }catch (e: NumberFormatException){
                newNumber.setText("")
            }

            pendingOperation = op
            operation.text = pendingOperation
        }
        buttonDivide.setOnClickListener(opListener)
        buttonEquals.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
       // Log.d(STATE_PENDING_OPERATION, "onSaceinstanceState called")
        super.onSaveInstanceState(outState)
        if (operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1!!)
            var a = outState.putBoolean(STATE_OPERAND1_STORED, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(STATE_PENDING_OPERATION, "onRestoreInstanceState called")
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false)){
            operand1 = savedInstanceState.getDouble(STATE_OPERAND1)
        }else{
            operand1 = null
        }
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION, "=")
        operation.text = pendingOperation
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
                    Double.NaN   // handle attempt to divide by zero
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
