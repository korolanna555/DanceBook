package com.students.salonapp.ui.screens

import MapWebView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

data class Salon(
    val id: String,
    val name: String,
    val price: Float,
    val rating: Int,
    val location: LatLng
)

@Composable
fun MapSearchScreen() {
    var search by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(1000f) }
    var selectedRatings by remember { mutableStateOf(setOf<Int>()) }

    val salonsSample = listOf(
        Salon("1", "Салон Лайт", 700f, 3, LatLng(56.6388, 47.8908)),
        Salon("2", "Стиль Плюс", 500f, 5, LatLng(56.6398, 47.8958)),
        Salon("3", "Красота 24", 200f, 4, LatLng(56.6378, 47.8888)),
        Salon("4", "Шарм", 750f, 2, LatLng(56.6358, 47.8900)),
        Salon("5", "Эксперт", 1000f, 5, LatLng(56.6348, 47.8920)),
    )

    val filteredSalons = salonsSample.filter { salon ->
        salon.name.contains(search, ignoreCase = true)
                && salon.price <= price
                && (selectedRatings.isEmpty() || selectedRatings.contains(salon.rating))
    }

    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(LatLng(56.6388, 47.8908), 14f)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Поиск") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text("Максимальная цена: ${price.toInt()} ₽", modifier = Modifier.padding(horizontal = 16.dp))
        Slider(
            value = price,
            onValueChange = { price = it },
            valueRange = 0f..1000f,
            steps = 20,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Рейтинг", modifier = Modifier.padding(start = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (1..5).forEach { rating ->
                val selected = selectedRatings.contains(rating)
                FilterChip(
                    selected = selected,
                    onClick = {
                        selectedRatings = if (selected) {
                            selectedRatings - rating
                        } else {
                            selectedRatings + rating
                        }
                    },
                    label = { Text(rating.toString()) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            MapWebView(
                modifier = Modifier
                    .weight(1f) // карта занимает оставшееся пространство
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Результаты (${filteredSalons.size}):", modifier = Modifier.padding(horizontal = 16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredSalons) { salon ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = salon.name, style = MaterialTheme.typography.titleMedium, color = Color.DarkGray)
                        Text(text = "Цена: ${salon.price} ₽", color = Color.DarkGray)
                        Text(text = "Рейтинг: ${salon.rating}", color = Color.DarkGray)
                    }
                }
            }
        }
    }
}
