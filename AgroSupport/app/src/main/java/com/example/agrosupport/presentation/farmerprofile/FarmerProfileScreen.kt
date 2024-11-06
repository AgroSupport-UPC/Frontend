package com.example.agrosupport.presentation.farmerprofile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun FarmerProfileScreen(viewModel: FarmerProfileViewModel) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getFarmerProfile()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { viewModel.goToHome() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
                Text(text = "Editar Perfil", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
                IconButton(onClick = { /* Opción de menú adicional */ }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More"
                    )
                }
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                state.data?.let { profile ->
                    EditProfileContent(profile = profile, viewModel = viewModel)
                } ?: run {
                    state.message?.let { errorMessage ->
                        if (errorMessage.isNotBlank()) {
                            AlertDialog(
                                onDismissRequest = { viewModel.reloadPage() },
                                confirmButton = {
                                    TextButton(onClick = { viewModel.reloadPage() }) {
                                        Text("OK")
                                    }
                                },
                                title = { Text("Error") },
                                text = { Text(errorMessage) }
                            )
                        }
                    }
                }
            }
        }
    }
}



