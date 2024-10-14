package com.example.agrosupport.presentation.createprofilefarmer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.agrosupport.R

@Composable
fun CreateProfileFarmerScreen(viewModel: CreateProfileFarmerViewModel) {
    val state by viewModel.state
    val snackbarMessage by viewModel.snackbarMessage
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbarMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f)
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(id = R.drawable.starheader),
                        contentDescription = "Header star Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Text(
                    text = "Crear Perfil",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp,
                    textAlign = TextAlign.Left
                )
                Spacer(modifier = Modifier.height(50.dp))

                Image(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.profile_icon),
                    contentDescription = "Profile Icon",
                    modifier = Modifier
                        .size(100.dp) // Adjust size as necessary
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .clickable { /* Handle image not implemented yet */ }
                        .background(Color(0xFF3E64FF), shape = MaterialTheme.shapes.medium)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Subir foto de perfil",
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "Descripción:",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp) // Alinear a la izquierda
                )

                // TextField for Description
                TextField(
                    value = viewModel.description.value,
                    onValueChange = { viewModel.description.value = it },
                    placeholder = { Text("Cuéntanos un poco sobre ti") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.height(80.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                            viewModel.createProfile()
                        }
                        .background(Color(0xFF092C4C), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Continuar",
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}