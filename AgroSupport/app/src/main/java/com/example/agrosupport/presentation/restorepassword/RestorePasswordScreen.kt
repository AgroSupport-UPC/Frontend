package com.example.agrosupport.presentation.restorepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrosupport.R

@Composable
fun RestorePasswordScreen(viewModel: RestorePasswordViewModel) {
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
                    .fillMaxHeight(0.15f)
            ) {
                // Imagen de fondo (starheader)
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.starheader),
                    contentDescription = "Header star Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.FillBounds
                )

                // Botón de retroceso
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 14.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                        onClick = { viewModel.goBack() },
                        modifier = Modifier
                            .size(40.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retroceder",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black
                        )
                    }
                }
            }

            Text(
                text = "Restablecer contraseña",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
                textAlign = TextAlign.Left
            )

            Text(
                text = "Por favor ingresa tu nueva contraseña",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Left
            )

            Spacer(modifier = Modifier.height(35.dp))


            // Campos para ingresar nueva contraseña y confirmación
            val newPasswordState = remember { mutableStateOf("") }
            val confirmPasswordState = remember { mutableStateOf("") }

            PasswordTextField(
                value = newPasswordState.value,
                onValueChange = { newPasswordState.value = it },
                label = "Nueva Contraseña"
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                value = confirmPasswordState.value,
                onValueChange = { confirmPasswordState.value = it },
                label = "Confirmar Contraseña"
            )

            Button(
                onClick = {
                    // Lógica para restablecer la contraseña
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF092C4C))
            ) {
                Text(text = "Restablecer Contraseña", color = Color.White)
            }

            // Spacer para empujar el texto hacia abajo
            Spacer(modifier = Modifier.weight(1f))

            // Texto clickable
            val text = buildAnnotatedString {
                append("¿Ya tienes una cuenta? ")
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

@Composable
fun PasswordTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    var showPassword by remember { mutableStateOf(false) }
    val passwordVisualTransformation = remember { PasswordVisualTransformation() }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            passwordVisualTransformation
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp)),
        trailingIcon = {
            Icon(
                imageVector = if (showPassword) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                },
                contentDescription = "Toggle password visibility",
                modifier = Modifier
                    .clickable { showPassword = !showPassword }
            )
        }
    )
}
