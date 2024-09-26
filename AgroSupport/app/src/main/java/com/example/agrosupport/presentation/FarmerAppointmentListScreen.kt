package com.example.agrosupport.presentation

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import com.example.agrosupport.R
import com.example.agrosupport.common.Constants
import com.example.agrosupport.data.remote.AdvisorService
import com.example.agrosupport.data.remote.AppointmentService
import com.example.agrosupport.data.remote.ProfileService
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.AppointmentRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FarmerAppointmentListScreen(viewModel: FarmerAppointmentListViewModel) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getAdvisorAppointmentListByFarmer()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Encabezado con el botón de regreso y título
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón de regreso
                IconButton(onClick = { viewModel.goBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Go back"
                    )
                }

                // Título "Citas"
                Text(
                    text = "Citas",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleLarge
                )

                Row {

                    IconButton(onClick = { viewModel.goHistory() }) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = "Historial"
                        )
                    }

                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }
                }
            }


            // Lista de citas
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                state.data?.let { appointments ->
                    items(count = appointments.size) { index ->
                        AppointmentCard(
                            appointment = appointments[index],

                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AppointmentCard(appointment: AdvisorAppointmentCard) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Establece el color de fondo blanco
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del asesor
            AndroidView(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.Gray, CircleShape), // Agrega un borde de 2.dp de color gris
                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { imageView ->
                    Picasso.get()
                        .load(appointment.advisorPhoto)
                        .error(R.drawable.placeholder)
                        .into(imageView)
                }
            )


            Spacer(modifier = Modifier.width(16.dp)) // Espacio entre la imagen y el texto

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start // Mantiene el contenido alineado a la izquierda
            ) {
                // Nombre del asesor
                Text(
                    text = appointment.advisorName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFF2B2B2B)
                    ),
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Línea divisoria
                Divider(
                    color = Color.Black, // Línea negra
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)

                )

                // Fecha y hora de la cita
                Text(
                    text = "${appointment.scheduledDate} (${appointment.startTime} - ${appointment.endTime})",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold, // Hora en negrita
                        color = Color(0xFF06204A) // Color hexadecimal con 0xFF para opacidad completa
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)

                )
            }

        }
    }
}


