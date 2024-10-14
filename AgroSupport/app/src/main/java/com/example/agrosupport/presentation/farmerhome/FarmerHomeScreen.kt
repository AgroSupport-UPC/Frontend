package com.example.agrosupport.presentation.farmerhome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.agrosupport.R
import com.example.agrosupport.presentation.farmerhistory.AdvisorAppointmentCard
import com.example.agrosupport.presentation.farmerhistory.AppointmentCard

@Composable
fun FarmerHomeScreen(viewModel: FarmerHomeViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getFarmerName()
        viewModel.getAppointment()
    }

    val cardItems = listOf(
        CardItem(
            image = painterResource(id = R.drawable.icon_advisors),
            text = "Asesores",
            onClick = { viewModel.goToAdvisorList() }
        ),
        CardItem(
            image = painterResource(id = R.drawable.icon_appointments),
            text = "Citas",
            onClick = { viewModel.goToAppointmentList() }
        ),
        CardItem(
            image = painterResource(id = R.drawable.icon_publications),
            text = "Publicaciones"
        ),
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues).padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bienvenido, ${viewModel.state.value.data?.firstName ?: "Granjero"}",
                    modifier = Modifier.weight(1f),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.padding(horizontal = 8.dp).size(32.dp)
                )
                IconButton(onClick = { viewModel.setExpanded(true) }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        modifier = Modifier.padding(horizontal = 8.dp).size(32.dp)
                    )
                }
                // Menú emergente
                DropdownMenu(
                    expanded = viewModel.expanded.value,
                    onDismissRequest = { viewModel.setExpanded(false) },
                    offset = DpOffset(x = (2000).dp, y = 0.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Cerrar Sesión") },
                        onClick = {
                            viewModel.signOut()
                            viewModel.setExpanded(false)
                        }
                    )
                }
            }

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "Hero Image",
                    modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f).padding(bottom = 16.dp)
                )
                if (viewModel.appointmentCard.value.data == null) {
                    Text(
                        text = "No hay citas próximas",
                        modifier = Modifier.padding(8.dp),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                } else {
                    Text(
                        text = "Tu próxima cita",
                        modifier = Modifier.padding(8.dp).padding(bottom = 16.dp),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.titleLarge
                    )
                    AppointmentCard(
                        appointment = viewModel.appointmentCard.value.data!!,
                        onClick = {})
                }
            }

            Text(
                text = "Elige tu Próximo Paso",
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    count = 3,
                    itemContent = { index ->
                        NavigationCard(index, cardItems)
                    }
                )
            }

        }
    }
}