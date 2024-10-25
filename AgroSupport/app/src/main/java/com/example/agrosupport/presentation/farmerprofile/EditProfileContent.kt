package com.example.agrosupport.presentation.farmerprofile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.agrosupport.R
import com.example.agrosupport.domain.profile.Profile
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun EditProfileContent(profile: Profile, viewModel: FarmerProfileViewModel) {
    var firstName by remember { mutableStateOf(profile.firstName) }
    var lastName by remember { mutableStateOf(profile.lastName) }
    var birthDate by remember { mutableStateOf(profile.birthDate) }
    var description by remember { mutableStateOf(profile.description) }
    var photo by remember { mutableStateOf(profile.photo) }

    val isModified by remember {
        derivedStateOf {
            firstName != profile.firstName ||
                    lastName != profile.lastName ||
                    birthDate != profile.birthDate ||
                    description != profile.description ||
                    photo != profile.photo
        }
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.updateProfileWithImage(it, profile.copy(photo = it.toString()))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp), // Deja espacio para el botón flotante
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    }
            ) {

                GlideImage(
                    imageModel = {
                        if (photo.isBlank()) R.drawable.placeholder else photo
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )


                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset((-8).dp, (-8).dp)
                        .size(32.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                        .padding(4.dp)
                        .zIndex(2f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Image",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campos de texto para editar el perfil
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text("Fecha de nacimiento") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
        }


        Button(
            onClick = {
                val updatedProfile = profile.copy(
                    firstName = firstName,
                    lastName = lastName,
                    birthDate = birthDate,
                    description = description,
                    photo = photo
                )
                viewModel.updateFarmerProfile(updatedProfile)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E64FF)), // Azul similar al de la imagen
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            enabled = isModified
        ) {
            Text(text = "Guardar", color = Color.White, fontSize = 16.sp)
        }
    }
}