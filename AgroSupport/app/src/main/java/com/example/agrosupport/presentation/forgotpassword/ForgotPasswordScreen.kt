package com.example.agrosupport.presentation.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrosupport.R

@Composable
fun ForgotPasswordScreen(viewModel: ForgotPasswordViewModel) {
    val emailState = remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Sección que ocupa el 15% de la pantalla para la imagen y el botón
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f) // Ocupa el 15% de la altura
            ) {
                // Imagen de fondo (starheader)
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.starheader),
                    contentDescription = "Header star Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(), // La imagen ocupa todo el 15% de la sección
                    contentScale = ContentScale.FillBounds
                )

                // Botón de retroceso superpuesto en la parte superior izquierda
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 14.dp), // Separación del borde izquierdo de la pantalla movil
                    contentAlignment = Alignment.CenterStart // Botón alineado a la izquierda y centrado verticalmente

                ) {
                    IconButton(
                        onClick = { viewModel.goBack() }, // Acción de retroceso
                        modifier = Modifier
                            .size(40.dp) // Tamaño del botón
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp) // Borde rectangular
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Ícono de retroceso
                            contentDescription = "Retroceder",
                            modifier = Modifier.size(24.dp), // Tamaño del ícono
                            tint = Color.Black // Color del ícono
                        )
                    }
                }
            }

            // Texto que comienza justo debajo del 15% de la pantalla
            Text(
                text = "¿Olvidaste tu contraseña?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), // Padding para dar espacio alrededor del texto
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold, // Negrita
                    fontSize = 28.sp // Tamaño de 30px
                ),
                textAlign = TextAlign.Center // Centrado
            )
            Spacer(modifier = Modifier.height(10.dp)) // Espacio de 10px
            // Segundo texto centrado y tamaño 16px
            Text(
                text = "¡No te preocupes! Por favor, brinda el correo electrónico de tu cuenta",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp // Tamaño 16px
                ),
                textAlign = TextAlign.Left // Centrado
            )

            Spacer(modifier = Modifier.height(25.dp)) // Espacio de 25px

            // TextField para correo electrónico
            TextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp)), // Fondo blanco y borde redondeado
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Sharp.Email,
                        contentDescription = "Email"
                    )
                }
            )

            Button(
                onClick = { viewModel.goToRestorePasswordScreen() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                colors = ButtonDefaults.buttonColors(Color(0xFF092C4C))
            ) {
                Text(text = "Enviar código", color = Color.White)
            }

            // Spacer para empujar el texto hacia abajo
            Spacer(modifier = Modifier.weight(1f))

            // Texto clickable
            val text = buildAnnotatedString {
                append("¿Recordaste tu contraseña? ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("Inicia sesión")
                pop()
            }

            ClickableText(
                text = text,
                onClick = { offset ->
                    // Obtener el índice de "Inicia sesión" en el texto
                    val startIndex = text.indexOf("Inicia sesión")
                    val endIndex = startIndex + "Inicia sesión".length

                    if (offset in startIndex until endIndex) {
                        viewModel.goToLoginScreen()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}