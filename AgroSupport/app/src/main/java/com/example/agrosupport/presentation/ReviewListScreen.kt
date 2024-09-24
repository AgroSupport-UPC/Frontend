package com.example.agrosupport.presentation

import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.agrosupport.R
import com.squareup.picasso.Picasso

@Composable
fun ReviewListScreen(viewModel: ReviewListViewModel, advisorId: Long) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getAdvisorReviewList(advisorId)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues).padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { viewModel.goBack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
                Text(
                    text = "Reseñas",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Filter"
                )
            }
            if (state.data.isNullOrEmpty()) {
                Text(
                    text = "No se encontraron reseñas para este asesor",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF222B45),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    state.data.let {
                        items(count = it.size, itemContent = { index ->
                            ReviewCard(it[index])
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewCard(review: ReviewCard) {
    val rating = review.rating.toInt()
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = Color(0xFFF9EAE1),
            disabledContentColor = Color.White,
            disabledContainerColor = Color(0xFFF4B696),
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            AndroidView(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { view ->
                    Picasso.get()
                        .load(review.farmerLink)
                        .error(R.drawable.placeholder)
                        .into(view)
                }
            )
            Text(
                text = review.farmerName,
                color = Color(0xFF222B45),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Row {
                for (i in 1..rating) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFD9A722)
                    )
                }
            }
            Text(
                text = review.comment,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            )
        }
    }
}