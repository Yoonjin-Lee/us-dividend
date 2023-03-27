package com.example.usdividend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StockScreen(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.gray), shape = RectangleShape)
            .wrapContentHeight(align = Alignment.Top)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(18.dp, 23.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dollar_exchange),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = modifier.padding(8.dp, 0.dp, 0.dp, 0.dp))
            Text(
                text = "0000",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            )
            Text(
                text = stringResource(id = R.string.won),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.reset_icon),
                    contentDescription = stringResource(id = R.string.reset)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(18.dp, 0.dp, 0.dp, 23.dp)
        ) {
            Text(
                text = stringResource(id = R.string.my_dollars),
                style = TextStyle(
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
            Text(
                text = "00",
                style = TextStyle(fontSize = 15.sp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewStockScreen() {
    StockScreen(modifier = Modifier)
}