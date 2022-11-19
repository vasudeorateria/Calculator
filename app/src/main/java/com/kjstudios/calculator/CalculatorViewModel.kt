package com.kjstudios.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    companion object {
        val MAX_NUM_LENGTH = 8
    }

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(
        action: CalculatorActions
    ) {
        when (action) {
            is CalculatorActions.Clear -> state = CalculatorState()
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Delete -> performDelete()
            is CalculatorActions.Number -> enterNumber(action.number)
            is CalculatorActions.Operation -> enterOperation(action.operation)
            is CalculatorActions.Calculate -> performCalculate()
        }
    }

    private fun performCalculate() {

        var str = state.expression

        var num1 = 0.0
        var num2 = 0.0
        var op = ""
        var res2 = 0.0
        var i = -1
        while (str.isNotBlank()) {
            str.forEachIndexed { index, c ->
                if (i == -1 && index > 0 && operationSymbolsList().contains(c.toString())) {
                    i = index
                    return@forEachIndexed
                }
            }
            println(i)
            var s1 = ""
            if (i == -1) {
                s1 = str
                if (op != "") {
                    num2 = s1.toDouble()
                    res2 = calculate(num1, op, num2) ?: return
                    num1 = res2
                    num2 = 0.0
                }
                str = ""
            } else {
                s1 = str.substring(0, i)
                if (op == "") {
                    op = str[i].toString()
                    num1 = s1.toDouble()
                } else {
                    op = str[i].toString()
                    num2 = s1.toDouble()
                    res2 = calculate(num1, op, num2) ?: return
                    num1 = res2
                    num2 = 0.0
                }
                str = str.substring(i + 1)
            }
        }
        state = state.copy(
            expression = res2.toString().take(15).trimTrailingZero()
        )
    }

    private fun calculate(number1: Double, operation: String, number2: Double): Double? {
        return when (operation) {
            CalculatorOperations.Add.symbol -> number1 + number2
            CalculatorOperations.Subtract.symbol -> number1 - number2
            CalculatorOperations.Multiply.symbol -> number1 * number2
            CalculatorOperations.Divide.symbol -> number1 / number2
            else -> null
        }
    }

    private fun enterOperation(operation: CalculatorOperations) {
        if (state.expression.isNotBlank()) {
            state = if (operationSymbolsList().contains(state.expression.last().toString())
            ) {
                performDelete()
                state.copy(expression = state.expression + operation.symbol)
            } else {
                state.copy(expression = state.expression + operation.symbol)
            }
        } else {
            if (operation == CalculatorOperations.Subtract) {
                state = state.copy(expression = state.expression + operation.symbol)
            }
        }
    }

    private fun enterNumber(number: Int) {
        state = state.copy(expression = state.expression + number)
    }

    private fun performDelete() {
        if (state.expression.isNotBlank()) {
            state = state.copy(expression = state.expression.dropLast(1))
        }
    }

    private fun enterDecimal() {

//        if (state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
//            state = state.copy(number1 = state.number1 + ".")
//            return
//        }
//
//        if (!state.number2.contains(".") && state.number2.isNotBlank()) {
//            state = state.copy(number2 = state.number2 + ".")
//            return
//        }

        state = state.copy(expression = state.expression + ".")
        // todo add decimal to expression

    }

    private fun String.trimTrailingZero(): String {
        return if (contains(".")) {
            replace("0*$".toRegex(), "").replace("\\.$".toRegex(), "")
        } else {
            this
        }
    }

    private fun operationSymbolsList(): List<String> {
        return listOf(
            CalculatorOperations.Add.symbol,
            CalculatorOperations.Subtract.symbol,
            CalculatorOperations.Multiply.symbol,
            CalculatorOperations.Divide.symbol,
        )
    }
}