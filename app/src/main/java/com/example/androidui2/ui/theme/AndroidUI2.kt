package com.example.androidui2.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AndroidUI_2(){
    Column (modifier = Modifier.fillMaxSize().background(Color(0xFFFDEBFF))){
        Row (modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top){
            Text(text = "interests", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Row (modifier = Modifier.fillMaxWidth().padding(8.dp)){
            Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Accessibility", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Box(modifier = Modifier.fillMaxWidth()){
                Row(modifier = Modifier, horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Outlined.Check, contentDescription = "",
                        modifier = Modifier.size(32.dp).clip(CircleShape),
                        tint = Color(0xFFFFB6C1)
                    )
                }
            }
        }

        Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom){
            Column {
                Icon(imageVector = Icons.Outlined.Build, contentDescription = "",
                    modifier = Modifier.size(16.dp))
                Text(text = "For You", fontSize = 12.sp, fontWeight = FontWeight.Normal)
            }
            Column {
                Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "",
                    modifier = Modifier.size(16.dp))
                Text(text = "Shared", fontSize = 12.sp, fontWeight = FontWeight.Normal)
            }
            Column {
                Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "",
                    modifier = Modifier.size(16.dp))
                Text(text = "Interested", fontSize = 12.sp, fontWeight = FontWeight.Normal)
            }
        }
    }
}