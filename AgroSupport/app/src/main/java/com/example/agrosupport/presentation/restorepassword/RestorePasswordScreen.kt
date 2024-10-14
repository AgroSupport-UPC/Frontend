package com.example.agrosupport.presentation.restorepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrosupport.R

@Composable
fun RestorePasswordScreen(viewModel: RestorePasswordViewModel) {
    val newPassword = viewModel.newPassword.value
    val confirmPassword = viewModel.confirmPassword.value
    val showNewPassword = viewModel.isNewPasswordVisible.value
    val showConfirmPassword = viewModel.isConfirmPasswordVisible.value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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

            PasswordTextField(
                label = "Nueva Contraseña",
                password = newPassword,
                onPasswordChange = { viewModel.setNewPassword(it) },
                passwordVisible = showNewPassword,
                onVisibilityChange = { viewModel.toggleNewPasswordVisibility() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                label = "Confirmar Contraseña",
                password = confirmPassword,
                onPasswordChange = { viewModel.setConfirmPassword(it) },
                passwordVisible = showConfirmPassword,
                onVisibilityChange = { viewModel.toggleConfirmPasswordVisibility() }
            )

            Button(
                onClick = { /* TODO: Implementar lógica de restablecimiento de contraseña */ },
                enabled = viewModel.validatePasswords(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF092C4C))
            ) {
                Text(text = "Restablecer Contraseña", color = Color.White)
            }

            Spacer(modifier = Modifier.weight(1f))

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
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun PasswordTextField(
    label: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibilityChange: () -> Unit
) {
    val passwordVisualTransformation = if (passwordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        visualTransformation = passwordVisualTransformation,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp)),
        trailingIcon = {
            Icon(
                imageVector = if (passwordVisible) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                },
                contentDescription = "Toggle password visibility",
                modifier = Modifier.clickable { onVisibilityChange() }
            )
        }
    )
}
