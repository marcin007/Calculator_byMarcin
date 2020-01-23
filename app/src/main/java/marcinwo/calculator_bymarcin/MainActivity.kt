package marcinwo.calculator_bymarcin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    // Variabels to hold the operands and type of calculation
    private var operand1: Double? = null
    private var operand2: Double = 0.0
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
        newNumber = findViewById(R.id.newNumber)

        //Data input buttons
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDot)

        // Operand buttons
        val buttonEquals: Button = findViewById<Button>(R.id.buttonEquals)
        val buttonDivide: Button = findViewById<Button>(R.id.buttonDivide)
        val buttonMultiply: Button = findViewById<Button>(R.id.buttonMultiply)
        val buttonMinus: Button = findViewById<Button>(R.id.buttonMinus)
        val buttonPlus: Button = findViewById<Button>(R.id.buttonPlus)

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
            displayOperation.text = pendingOperation
        }
        buttonDivide.setOnClickListener(opListener)
        buttonEquals.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            operand2 = value

            if (pendingOperation == "=") {
                pendingOperation = operation
             }

            when (pendingOperation) {
                "=" -> operand1 = operand2
                "/" -> if (operand2 == 0.0) {
                    operand1 = Double.NaN // przypadek dzielenia przez zero
                } else {
                    operand1 = operand1!! / operand2
                }
                "*" -> operand1 = operand1!! * operand2
                "+" -> operand1 = operand1!! + operand2
                "-" -> operand1 = operand1!! - operand2
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }


}
