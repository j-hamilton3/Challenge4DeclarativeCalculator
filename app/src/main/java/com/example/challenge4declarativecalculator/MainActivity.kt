/**
 * James Hamilton
 * February 1st, 2024
 * ADEV 3007: Zacharie Montreuil
 */

package com.example.challenge4declarativecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.challenge4declarativecalculator.ui.theme.Challenge4DeclarativeCalculatorTheme
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Challenge4DeclarativeCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.border(2.dp, androidx.compose.ui.graphics.Color.Black)
                           .background(androidx.compose.ui.graphics.Color.LightGray)
    ) {
        Text(
            text = text,
            fontSize = 40.sp,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 30.dp, top = 30.dp, end = 20.dp, start = 20.dp)
                .background(androidx.compose.ui.graphics.Color.White)
        )
    }
}

@Composable
fun OperatorButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button (
        onClick = onClick,
        modifier = Modifier.padding(3.dp)
                           .width(70.dp)
                           .height(50.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun NumericButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(3.dp)
                           .width(70.dp)
                           .height(50.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun CancelButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(3.dp)
                           .width(70.dp)
                           .height(50.dp)
    ) {
        Text(text = "C")
    }
}

@Composable
fun EqualsButton(screenText: String, onEqualsClick: (Any?) -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = {
            val equationAsText: String = screenText

            if (equationAsText.isEmpty()) {
                println("This should not calculate, the equation is empty.")
            } else if (equationAsText.last() == ' ') {
                println("This will not calculate, the equation ends with an operator.")
            } else {
                // Splitting the string into a series of tokens.
                val tokens = equationAsText.split(" ")

                var result = tokens[0].toDouble()

                var i = 1 // To increment through the tokens.

                // Loop through the tokens.
                while (i < tokens.size) {
                    val operator = tokens[i]
                    val nextOperand = tokens[i + 1].toDouble()

                    when (operator) {
                        "+" -> result += nextOperand
                        "-" -> result -= nextOperand
                        "*" -> result *= nextOperand
                        "/" -> result /= nextOperand
                    }

                    i += 2 // Increments by two because operators are every second token.
                }

                // Update the screenText with the result
                onEqualsClick(result.toString())
            }
        },
        modifier = Modifier.padding(3.dp)
                           .width(70.dp)
                           .height(50.dp)
    ) {
        Text(text = "=")
    }
}

@Composable
fun CalculatorApp() {
    var screenText by remember { mutableStateOf("") }
    var operatorEntered by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalculatorScreen(screenText)
        Row(
            modifier = Modifier.padding(top=15.dp)
        ) {
            CancelButton({ screenText = ""; operatorEntered = true })
            NumericButton(text = "7", { screenText += "7"; operatorEntered = false })
            NumericButton(text = "8", { screenText += "8"; operatorEntered = false })
            NumericButton(text = "9", { screenText += "9"; operatorEntered = false })
        }
        Row {
            OperatorButton(text = "/", {
                if (!operatorEntered) {
                    screenText += " / "; operatorEntered = true
                }
            })
            NumericButton(text = "4", { screenText += "4"; operatorEntered = false })
            NumericButton(text = "5", { screenText += "5"; operatorEntered = false })
            NumericButton(text = "6", { screenText += "6"; operatorEntered = false })
        }
        Row {
            OperatorButton(text = "*", {
                if (!operatorEntered) {
                    screenText += " * "; operatorEntered = true
                }
            })
            NumericButton(text = "1", { screenText += "1"; operatorEntered = false })
            NumericButton(text = "2", { screenText += "2"; operatorEntered = false })
            NumericButton(text = "3", { screenText += "3"; operatorEntered = false })
        }
        Row {
            OperatorButton(text = "-", {
                if (!operatorEntered) {
                    screenText += " - "; operatorEntered = true
                }
            })
            OperatorButton(text = "+", {
                if (!operatorEntered) {
                    screenText += " + "; operatorEntered = true
                }
            })
            EqualsButton(screenText, {result -> screenText = result.toString()})
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Challenge4DeclarativeCalculatorTheme {
        CalculatorApp()
    }
}