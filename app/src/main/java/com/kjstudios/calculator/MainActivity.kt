package com.kjstudios.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kjstudios.calculator.ui.theme.CalculatorTheme
import com.kjstudios.calculator.ui.theme.MediumGrey

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state
                val buttonSpacing = 8.dp
                Calculator(
                    state = state, onAction = viewModel::onAction,
                    buttonSpacing = buttonSpacing,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MediumGrey)
                        .padding(16.dp)
                )
            }

        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Box(modifier = Modifier.fillMaxSize().background(Color.Red))

//    CalculatorTheme {
//        val viewModel = viewModel<CalculatorViewMOdel>()
//        val state = viewModel.state
//        val buttonSpacing = 8.dp
//        Calculator(
//            state = state, onAction = viewModel::onAction,
//            buttonSpacing = buttonSpacing,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(MediumGrey)
//                .padding(16.dp)
//        )
//    }
//}
