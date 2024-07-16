package com.example.assignment_1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    data class CurrencyInfo(val symbol: String, val rate: Double)

    private val exchangeRates = mapOf(
        "USD" to CurrencyInfo("$", 1.0),
        "EUR" to CurrencyInfo("€", 0.92),
        "JPY" to CurrencyInfo("¥", 158.10),
        "GBP" to CurrencyInfo("£", 0.77),
        "AUD" to CurrencyInfo("A$", 1.48),
        "CAD" to CurrencyInfo("C$", 1.36),
        "CHF" to CurrencyInfo("CHF", 0.90),
        "CNY" to CurrencyInfo("¥", 7.26),
        "SEK" to CurrencyInfo("kr", 10.59),
        "NZD" to CurrencyInfo("NZ$", 1.64),
        "INR" to CurrencyInfo("₹", 83.60)
    )

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView
    private lateinit var buttonReverse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        editTextAmount = findViewById(R.id.editTextAmount)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)
        buttonReverse = findViewById(R.id.buttonReverse)

        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        buttonConvert.setOnClickListener {
            handleConversion()
        }

        buttonReverse.setOnClickListener {
            swapSpinnersSelection()
        }
    }

    private fun swapSpinnersSelection() {
        val fromIndex = spinnerFrom.selectedItemPosition
        val toIndex = spinnerTo.selectedItemPosition

        spinnerFrom.setSelection(toIndex)
        spinnerTo.setSelection(fromIndex)

        handleConversion()
    }

    private fun handleConversion() {
        val fromCurrency = spinnerFrom.selectedItem.toString()
        val toCurrency = spinnerTo.selectedItem.toString()
        val amountText = editTextAmount.text.toString()

        val amount = amountText.toDoubleOrNull()

        if (amount != null) {
            val fromCurrencyInfo = exchangeRates[fromCurrency]
            val toCurrencyInfo = exchangeRates[toCurrency]

            if (fromCurrencyInfo != null && toCurrencyInfo != null) {
                val conversionRate = toCurrencyInfo.rate / fromCurrencyInfo.rate
                val result = amount * conversionRate
                val fromSymbol = fromCurrencyInfo.symbol
                val toSymbol = toCurrencyInfo.symbol

                textViewResult.text = String.format(
                    "Conversion Rate: 1 %s = %.2f %s\nResult: %.2f %s",
                    fromSymbol, conversionRate, toSymbol, result, toSymbol
                )
            } else {
                textViewResult.text = "Currency information not found."
            }
        } else {
            textViewResult.text = "Please enter a valid amount."
        }
    }
}
