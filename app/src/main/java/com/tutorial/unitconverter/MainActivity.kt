package com.tutorial.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutorial.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ConvertorWindow()
                }
            }
        }
    }
}

@Composable
fun ConvertorWindow() {
    var inputText by remember { mutableStateOf("") }
    var outputText by remember { mutableDoubleStateOf(0.0) }

    // Default values for currency selectors
    var fromDisplayText by remember { mutableStateOf("From Currency") }
    var toDisplayText by remember { mutableStateOf("To Currency") }

    // Stores whether the dropdown should be expanded
    var fromExpand by remember { mutableStateOf(false) }
    var toExpand by remember { mutableStateOf(false) }

    // standardize base currency values
    fun standardizeFromCurrency(): Double {
        val inputAmount = inputText.toDoubleOrNull() ?: 0.0
        return when (fromDisplayText) {
            "PKR" -> inputAmount * 0.29 // Placeholder conversion rate for PKR
            "CAD" -> inputAmount * 62.5
            "AED" -> inputAmount * 22.7272
            "USD" -> inputAmount * 83.0
            "EUR" -> inputAmount * 88.0
            "GBP" -> inputAmount * 101.0
            else -> 0.0
        }
    }

    //  convert to the target currency
    fun convertToCurrency(standardized: Double): Double {
        return when (toDisplayText) {
            "PKR" -> standardized * 3.45 // conversion rates
            "CAD" -> standardized * 0.016
            "AED" -> standardized * 0.044
            "USD" -> standardized * 0.012
            "EUR" -> standardized * 0.011
            "GBP" -> standardized * 0.0098
            else -> 0.0
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Currency Converter",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Input field for amount
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                val standardized = standardizeFromCurrency()
                outputText = convertToCurrency(standardized)
            },
            label = { Text("Enter Amount") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // From Currency Dropdown
        Row {
            Box {
                Button(onClick = { fromExpand = !fromExpand }) {
                    Text(fromDisplayText)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "For From Drop down")
                }
                DropdownMenu(
                    expanded = fromExpand,
                    onDismissRequest = { fromExpand = false }
                ) {
                    listOf("PKR", "CAD", "AED", "USD", "EUR", "GBP").forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                fromExpand = false
                                fromDisplayText = currency
                                val standardized = standardizeFromCurrency()
                                outputText = convertToCurrency(standardized)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))

            //  Currency Dropdown
            Box {
                Button(onClick = { toExpand = !toExpand }) {
                    Text(toDisplayText)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "For To Drop down")
                }
                DropdownMenu(
                    expanded = toExpand,
                    onDismissRequest = { toExpand = false }
                ) {
                    listOf("PKR", "CAD", "AED", "USD", "EUR", "GBP").forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                toExpand = false
                                toDisplayText = currency
                                val standardized = standardizeFromCurrency()
                                outputText = convertToCurrency(standardized)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display  result
        Text("Result: ${outputText.toString()}", fontWeight = FontWeight.Bold, fontSize = 22.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnitConverterTheme {
        ConvertorWindow()
    }
}
