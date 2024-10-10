package com.example.agrosupport.presentation.welcomesection


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.agrosupport.R

@Composable
fun WelcomeScreen(viewModel: WelcomeViewModel) {
    Scaffold(
        modifier = Modifier.background(Color.White)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.welcome),
                contentDescription = "Welcome image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f), // 50% de la pantalla
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = "Bienvenido a AgroSupport",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold, // Negrita
                    fontSize = 32.sp // Tamaño 32px
                ),
                textAlign = TextAlign.Center // Centrado
            )

            // Segundo texto centrado y tamaño 17px
            Text(
                text = "Tu Aliado en la Agricultura: Asesoría y Tecnología para crecer con confianza",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 17.sp // Tamaño 17px
                ),
                textAlign = TextAlign.Center // Centrado
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Botones en columna
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.goToLoginScreen() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF092C4C))
                ) {
                    Text(text = "Iniciar Sesión", color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = { viewModel.goToFarmerHomeScreen() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Crear cuenta", color = Color.Black)
                }
            }
        }
    }
}
