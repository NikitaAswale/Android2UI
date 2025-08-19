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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

        Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top){
            Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "",
                modifier = Modifier.size(48.dp).background(Color(0xFFFFB6C1))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "Accessibility", fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

            Box(modifier = Modifier.fillMaxWidth()){
                Row(modifier = Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "",
                        modifier = Modifier.size(32.dp).background(Color(0xFFFFB6C1))
                    )
                }
            }

            Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Outlined.Build, contentDescription = "",
                    modifier = Modifier.size(48.dp).background(Color(0xFFFFB6C1))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Android Auto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        //horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add, contentDescription = "",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
                //horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Outlined.Call, contentDescription = "",
                    modifier = Modifier.size(48.dp).background(Color(0xFFFFB6C1))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Android Studio & Tools",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                       // horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle, contentDescription = "",
                            modifier = Modifier.size(32.dp).background(Color(0xFFFFB6C1))
                        )
                    }
                }
            }

            Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
                //horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Outlined.Create, contentDescription = "",
                    modifier = Modifier.size(48.dp).background(Color(0xFFFFB6C1))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Android TV",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add, contentDescription = "",
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }
            }

            Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
                //horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Home, contentDescription = "",
                    modifier = Modifier.size(48.dp).background(Color(0xFFFFB6C1))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Architecture",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                       // horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle, contentDescription = "",
                            modifier = Modifier.size(32.dp).background(Color(0xFFFFB6C1))
                        )
                    }
                }

                Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
                   // horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Menu, contentDescription = "",
                        modifier = Modifier.size(48.dp).background(Color(0xFFFFB6C1))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Compose",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle, contentDescription = "",
                                modifier = Modifier.size(32.dp).background(Color(0xFFFFB6C1))
                            )
                        }
                    }
                }
            }

            Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
              //  horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn, contentDescription = "",
                    modifier = Modifier.size(48.dp).background(Color(0xFFFFB6C1))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Camera & Media",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add, contentDescription = "",
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }
            }
        }

        Row (modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom){
            Column (modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                Icon(imageVector = Icons.Outlined.Build, contentDescription = "",
                    modifier = Modifier.size(16.dp))
                Text(text = "For You", fontSize = 12.sp, fontWeight = FontWeight.Normal)
            }
            Column (modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "",
                    modifier = Modifier.size(16.dp))
                Text(text = "Shared", fontSize = 12.sp, fontWeight = FontWeight.Normal)
            }
            Column (modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "",
                    modifier = Modifier.size(16.dp).background(Color(0xFFFFB6C1))
                )
                Text(text = "Interested", fontSize = 12.sp, fontWeight = FontWeight.Normal)
            }
        }
    }
}