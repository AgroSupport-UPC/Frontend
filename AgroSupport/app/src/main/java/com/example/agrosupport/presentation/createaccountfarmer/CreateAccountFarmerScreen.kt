package com.example.agrosupport.presentation.createaccountfarmer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrosupport.R

@Composable
fun CreateAccountFarmerScreen(viewModel: CreateAccountFarmerViewModel) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
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
                    text = "Crear Cuenta",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp,
                    textAlign = TextAlign.Left
                )
                Spacer(modifier = Modifier.height(50.dp))

                // Fila de Nombre y Apellido
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(text = "Nombre*", style = MaterialTheme.typography.bodyMedium)
                        TextField(
                            value = viewModel.firstName.value,
                            onValueChange = { viewModel.firstName.value = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                        Text(text = "Apellido*", style = MaterialTheme.typography.bodyMedium)
                        TextField(
                            value = viewModel.lastName.value,
                            onValueChange = { viewModel.lastName.value = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de Email
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text(text = "Email*", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = viewModel.email.value,
                        onValueChange = { viewModel.email.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de Fecha de Nacimiento
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text(text = "Fecha de Nacimiento*", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = viewModel.birthDate.value,
                        onValueChange = { viewModel.birthDate.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de Contraseña
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text(text = "Contraseña*", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = viewModel.password.value,
                        onValueChange = { viewModel.password.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Fila de Ciudad y País
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(text = "Ciudad*", style = MaterialTheme.typography.bodyMedium)
                        TextField(
                            value = viewModel.city.value,
                            onValueChange = { viewModel.city.value = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                        Text(text = "País*", style = MaterialTheme.typography.bodyMedium)
                        TextField(
                            value = viewModel.country.value,
                            onValueChange = { viewModel.country.value = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Continuar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable { viewModel.goNextPart() }
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

                Text(
                    text = buildAnnotatedString {
                        append("¿Ya tienes una cuenta? ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Inicia sesión")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { viewModel.goToLoginScreen() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
