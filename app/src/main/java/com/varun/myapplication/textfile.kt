package com.varun.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun textfile(text1 :String,text2:String)
{
    Box(
        Modifier.padding(2.dp,2.dp,2.dp,2.dp)
    ) {
        Column {

            Text(
                text = "  " +text1,
                color = Color.Black,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1, // Prevents overflow
                overflow = TextOverflow.Ellipsis,


                )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text =" "+ text2,
                color = Color.Gray,
                fontSize = 15.sp,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Light),
                maxLines = 1, // Prevents overflow
                overflow = TextOverflow.Ellipsis,


                )
        }
    }
}