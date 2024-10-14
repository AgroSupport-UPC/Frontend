package com.example.agrosupport.presentation.farmerhistory

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date

@Composable
fun FarmerAppointmentHistoryListScreen(
    viewModel: FarmerAppointmentHistoryListViewModel
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val selectedDate = remember { mutableStateOf<Date?>(null) }

    // Dialogo del selector de fechas
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate.value = calendar.time
            viewModel.getAdvisorAppointmentHistoryListByFarmer(selectedDate.value)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Cargar todas las citas al inicio
    LaunchedEffect(Unit) {
        viewModel.getAdvisorAppointmentHistoryListByFarmer(null)
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { viewModel.goBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                    Text(
                        text = "Historial de Citas",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = { /* Acción de más opciones */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    state.data?.let { appointments ->
                        items(appointments) { appointment ->
                            AppointmentCard(
                                appointment = appointment,
                                onClick = { viewModel.onReviewAppointment(appointment.id) }
                            )
                        }
                    }
                }
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (state.data.isNullOrEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.message.ifEmpty { "No hay citas previas" },
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF222B45),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            // Columna para los botones flotantes en la parte inferior derecha
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botón flotante para seleccionar la fecha
                FloatingActionButton(
                    onClick = { datePickerDialog.show() }
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Seleccionar fecha"
                    )
                }

                // Botón flotante para mostrar todas las citas
                FloatingActionButton(
                    onClick = {
                        selectedDate.value = null // Establecer selectedDate en null para mostrar todas las citas
                        viewModel.getAdvisorAppointmentHistoryListByFarmer(null)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Mostrar todas las citas"
                    )
                }
            }
        }
    }
}
