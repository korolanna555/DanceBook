package com.students.salonapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.students.salonapp.data.models.Service
import com.students.salonapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(
    onBack: () -> Unit,
    onServiceClick: (Service) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showFilters by remember { mutableStateOf(false) }
    
    val services = listOf(
        Service(
            id = "f3a7d9e2-c23b-4f0a-9d1e-42b7a9d564e4",
            name = "Lady-Dance",
            description = "Танцы для настоящих леди",
            duration = 90,
            price = 500.0,
            image_url = "ladydance",
            category_id = "2a5e8db6-97b9-4a3a-81d4-289ab8b0dc22"
        ),
        Service(
            id = "7e81b514-3f6a-4a0c-b4d8-938cfa0f2e3b",
            name = "High Hills",
            description = "Будь на высоте",
            duration = 60,
            price = 600.0,
            image_url = "highthills",
            category_id = "e08679a8-dd3f-4bb2-a019-a45c158dfac8"
        ),
        Service(
            id = "2a5e8db6-97b9-4a3a-81d4-289ab8b0dc22",
            name = "Бачата",
            description = "Страстный и чувственный латиноамериканский танец для всех уровней",
            price = 1000.0,
            duration = 130,
            image_url = "bachata",
            category_id = "2a5e8db6-97b9-4a3a-81d4-289ab8b0dc22"
        ),
        Service(
            id = "d4f5a6c1-8e72-4b39-9a4d-57c9b84f2e12",
            name = "Зумба",
            description = "Динамичная фитнес-программа с элементами латиноамериканских танцев и заводной музыкой",
            price = 900.0,
            duration = 90,
            image_url = "zumba",
            category_id = "a9b2f5d4-3c7e-4f9a-b2d6-12e3456789ab\n"
        ),
        Service(
            id = "d4e5f678-90ab-cdef-1234-567890abcdef",
            name = "Кизомба",
            description = "Плавный и чувственный афро-латинский танец для глубокого погружения в музыку и движение",
            price = 1000.0,
            duration = 45,
            image_url = "kizombi",
            category_id = "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
        ),
        Service(
            id = "e5f67890-abcd-ef12-3456-7890abcdef12",
            name = "Танго",
            description = "Элегантный и страстный аргентинский танец с глубокими эмоциями и выразительной хореографией",
            price = 1200.0,
            duration = 60,
            image_url = "tango",
            category_id = "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
        ),
        Service(
            id = "f67890ab-cdef-1234-5678-90abcdef1234",
            name = "Стриптиз",
            description = "Искусство соблазнительных движений и пластики тела для раскрепощения и уверенности",
            price = 2000.0,
            duration = 120,
            image_url = "striptiz",
            category_id = "b2c3d4e5-f678-90ab-cdef-1234567890ab"
        )
    )
    updateTransition(showFilters, label = "filter")
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            salonGreenPrimaryContainer.copy(alpha = 0.1f),
                            salonSurfaceLight
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Услуги",
                            style = MaterialTheme.typography.headlineSmall,
                            color = salonGreenOnPrimaryContainer
                        )
                        Text(
                            text = "${services.size} услуг",
                            style = MaterialTheme.typography.bodyMedium,
                            color = salonGreenOnPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = salonGreenOnPrimaryContainer
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Фильтры",
                            tint = salonGreenOnPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = salonGreenPrimaryContainer,
                    navigationIconContentColor = salonGreenOnPrimaryContainer,
                    actionIconContentColor = salonGreenOnPrimaryContainer
                )
            )
            AnimatedVisibility(
                visible = showFilters,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Фильтры",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = { Text("Цена") },
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) }
                            )
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = { Text("Длительность") },
                                leadingIcon = { Icon(Icons.Default.Timer, contentDescription = null) }
                            )
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = { Text("Популярность") },
                                leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) }
                            )
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(
                    items = services,
                    key = { it.id }
                ) { service ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                                .clickable { onServiceClick(service) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            val context = LocalContext.current
                            val imageResId = remember(service.image_url) {
                                context.resources.getIdentifier(service.image_url, "drawable", context.packageName)
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = imageResId,
                                    contentDescription = service.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = service.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = service.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "${service.price} ₽",
                                                style = MaterialTheme.typography.titleMedium,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "${service.duration} мин",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Button(
                                            onClick = { onServiceClick(service) },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = salonGreenPrimaryContainer,
                                                contentColor = salonGreenOnPrimaryContainer
                                            ),
                                            shape = RoundedCornerShape(24.dp)
                                        ) {
                                            Text("Выбрать")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}