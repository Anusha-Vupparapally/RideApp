package com.example.rideapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rideapp.viewmodel.RideViewModel

@Composable
fun DropScreen(
    navController: NavController,
    viewModel: RideViewModel
) {

    var dropText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Column {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp)
            ) {

                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }

                Text(
                    text = "Drop",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFDFE3E3))
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF0A6E0C), CircleShape)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text("Pickup", color = Color(color = 0xFF097A0E))
                        var pickupText by remember {
                            mutableStateOf(viewModel.pickup.value)
                        }

                        Column(modifier = Modifier.fillMaxWidth()) {

                            Spacer(modifier = Modifier.height(4.dp))

                            BasicTextField(
                                value = pickupText,
                                onValueChange = {
                                    pickupText = it
                                    viewModel.pickup.value = it
                                },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                                cursorBrush = SolidColor(Color.Black)
                            ) {
                                if (pickupText.isEmpty()) {
                                    Text("Enter pickup", color = Color.Gray)
                                }
                                it()
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFDFE3E3))
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color.Red, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {

                        Text("Drop", color = Color.Red)

                        Spacer(modifier = Modifier.height(4.dp))

                        BasicTextField(
                            value = dropText,
                            onValueChange = { dropText = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            cursorBrush = SolidColor(Color.Black)
                        ) { innerTextField ->

                            if (dropText.isEmpty()) {
                                Text("Enter destination", color = Color.Gray)
                            }

                            innerTextField()
                        }
                    }
                }
            }
        }


        Button(
            onClick = {
                viewModel.drop.value = dropText
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .imePadding()
                .padding(20.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3B87E3),
                contentColor = Color.White
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Default.AddLocationAlt,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text("Select from map")
            }
        }
    }
}