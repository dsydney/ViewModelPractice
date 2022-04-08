package com.example.viewmodelpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodelpractice.ui.theme.ViewModelPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenSetup()
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: DemoViewModel = DemoViewModel()) {
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.result,
        convertTemp = { viewModel.convertTemp(it) },
        switchChange = { viewModel.switchChange() }
    )
}

@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    switchChange: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var textState by remember { mutableStateOf("")}

        val onTextChange = { text: String ->
            textState = text
        }
        
        Text(
            "Temperature Converter", 
            modifier = Modifier.padding(20.dp), 
            style = MaterialTheme.typography.h4
        )
        
        InputRow(
            isFahrenheit = isFahrenheit,
            textState = textState,
            switchChange = switchChange,
            onTextChange = onTextChange
        )
        
        Text(
            result,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.h3
        )
        
        Button(
            onClick = { convertTemp(textState) }
        ) {
            Text(text = "Convert Temperature")
        }
    }
}

@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    switchChange: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = isFahrenheit,
            onCheckedChange = { switchChange() }
        )

        OutlinedTextField(
            value = textState,
            onValueChange = { onTextChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            label = { Text("Enter temperature")},
            modifier = Modifier.padding(10.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_ac_unit_24),
                    contentDescription = "frost",
                    modifier = Modifier.size(40.dp)
                )
            }
        )

        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(2000)
        ) { visible ->
            when (visible) {
                true -> Text("\u2109", style = MaterialTheme.typography.h4)
                false -> Text("\u2103", style = MaterialTheme.typography.h4)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview(model: DemoViewModel = DemoViewModel()) {
    ViewModelPracticeTheme {
        MainScreen(
            isFahrenheit = model.isFahrenheit,
            result = model.result,
            convertTemp = { model.convertTemp(it) },
            switchChange = { model.switchChange() }
        )
    }
}