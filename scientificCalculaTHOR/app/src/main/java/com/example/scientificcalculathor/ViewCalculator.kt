package com.example.scientificcalculathor

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ellasCalculator = CalculatorInput()
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Calculator(modifier: Modifier = Modifier){
    var calValue: String? by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(5.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        CalculatorOutput(
            calValue = calValue, modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )
        CalculatorButtons(modifier){str ->
            calValue = ellasCalculator.inputHandler(str, calValue)}
    }
}

@Composable
fun CalculatorOutput(calValue: String?, modifier: Modifier = Modifier){
    OutlinedCard (
        modifier = modifier.heightIn(min = 80.dp),
        colors = CardDefaults.outlinedCardColors(Color.Transparent)
    ){
        Text("$calValue",
            modifier = modifier
                .padding(20.dp),
            fontSize = 20.sp
        )
    }
}

@Composable
fun CalculatorButtons(modifier: Modifier = Modifier, onClickBtn: (String) -> Unit){
    Column (modifier = modifier.fillMaxWidth()){
        ScientificBtns(onClickBtn = onClickBtn)
        Row (modifier = modifier
            .padding(top = 4.dp)
            .fillMaxWidth()){
            NumBtns(onClickBtn = onClickBtn)
            OperatorBtns(onClickBtn = onClickBtn)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NumBtns(modifier: Modifier = Modifier, onClickBtn: (String) -> Unit){
    val numList : List <String> = listOf("9","8","7","6","5","4","3","2","1","0",".")
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        FlowRow(
            modifier.fillMaxWidth(0.6f),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.End),
        ) {
            val btnModifier = modifier
                .padding(2.dp)
                .weight(1f)
            for (i in 0..numList.size - 1) {
                val item = numList[i]
                CalculatorBtn(
                    item,
                    modifier = btnModifier,
                    txtColor = Color(0xFF122F4D),
                    btnColor = Color(0xFFCDD5E1),
                    fontSize = 16.sp,
                    onClickBtn = onClickBtn)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OperatorBtns(modifier : Modifier = Modifier, onClickBtn: (String) -> Unit){
    val operatorList : List <String> = listOf("C", "AC", "%", "÷", "x", "-", "+", "=")
    FlowRow(
        maxItemsInEachRow = 2) {
        val btnModifier = modifier
            .padding(2.dp)
            .weight(1f)
        for (i in 0 .. operatorList.size - 1){
            val item = operatorList[i]
            CalculatorBtn(
                item,
                btnModifier,
                Color.White,
                Color(0XFF5C6C7C),
                onClickBtn = onClickBtn
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScientificBtns(modifier: Modifier = Modifier, onClickBtn: (String) -> Unit){
    var flowColHeight by remember { mutableStateOf(0) }
    var inverseStatus by remember { mutableStateOf(false) }
    val sciList : List <String> = listOf("1/x", "x!", "x^y", "√x",  "log", "ln", "sin", "cos", "tan")
    val inverseList : List <String> = listOf("arcsin", "arccos", "arctan")
    Row(modifier = modifier.fillMaxWidth()) {
        val btnModifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(1.dp)
        //Ini tombol-tombol yang gelap
        FlowRow(
            verticalArrangement = Arrangement.SpaceBetween,
            maxItemsInEachRow = 2,
            modifier = modifier
                .weight(2f)
                .onSizeChanged { size -> flowColHeight = size.height }
        ) {

            for (i in 0..sciList.size - 4) {
                val item = sciList[i]
                CalculatorBtn(
                    item,
                    modifier = btnModifier,
                    Color.White,
                    Color(0xFF122F4D),
                    onClickBtn = onClickBtn
                )
            }
        }
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(horizontal = 2.dp)
                .height(with(LocalDensity.current) { flowColHeight.toDp() })
                .weight(1f)){
            if (inverseStatus){
                for (i in 0 .. inverseList.size -1){
                    val item = inverseList[i]
                    CalculatorBtn(
                        txtBtn = item,
                        modifier = btnModifier.fillMaxWidth(),
                        txtColor = Color.White,
                        btnColor = Color(0xFF215288),
                        fontSize = 12.sp,
                        onClickBtn = onClickBtn)
                }
            } else{
                for(i in 6 .. sciList.size -1){
                    val item = sciList[i]
                    CalculatorBtn(
                        txtBtn = item,
                        modifier = btnModifier.fillMaxWidth(),
                        txtColor = Color.White,
                        btnColor = Color(0xFF215288),
                        onClickBtn = onClickBtn)
                }
            }
        }

        Box (
            modifier = modifier
                .weight(1f)
                .height(with(LocalDensity.current) {flowColHeight.toDp()})
        ){
            CalculatorBtn(
                "Inv",
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(1.dp),
                Color.White,
                Color(0XFF0A7DF0),
                onClickBtn = {inverseStatus = !inverseStatus}
            )
        }

    }
}

@Composable
fun CalculatorBtn(txtBtn : String,
                  modifier: Modifier = Modifier,
                  txtColor : Color = Color.Black,
                  btnColor: Color = Color.White,
                  fontSize : TextUnit = TextUnit.Unspecified,
                  onClickBtn : (String) -> Unit){
    ElevatedButton (
        onClick = {onClickBtn(txtBtn)},
        modifier,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = btnColor,
            contentColor = txtColor
        )
    ) {
        Text("$txtBtn", fontSize = fontSize)
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview(showBackground = true)
@Composable
fun AppPreview(modifier: Modifier = Modifier) {
    Calculator()
}