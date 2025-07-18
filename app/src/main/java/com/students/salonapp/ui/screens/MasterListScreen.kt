package com.students.salonapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.students.salonapp.ui.theme.salonGreenOnPrimaryContainer
import com.students.salonapp.ui.theme.salonGreenPrimaryContainer
import com.students.salonapp.ui.theme.salonSurfaceLight

data class Master(
    val id: String,
    val name: String,
    val rating: Double,
    val bio: String,
    val image_url: String,
    val photo_url: String = image_url,
    val specialization: String = bio
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterListScreen(
    onBack: () -> Unit,
    onMasterClick: (masterId: String, masterName: String) -> Unit
) {
    var showFilters by remember { mutableStateOf(false) }
    val masters = listOf(
        Master(
            id = "master_bachata_1",
            name = "Марина Смирнова",
            rating = 4.9,
            bio = "Профессиональный преподаватель бачаты с 7-летним опытом.",
            image_url = "https://images.unsplash.com/photo-1520975696911-2a79d7dd56db?auto=format&fit=crop&w=800&q=80",
            specialization = "Бачата"
        ),
        Master(
            id = "master_zumba_1",
            name = "Дмитрий Кузнецов",
            rating = 4.7,
            bio = "Заводной тренер по зумбе, который вдохновляет на движение.",
            image_url = "https://images.unsplash.com/photo-1531058020387-3be344556be6?auto=format&fit=crop&w=800&q=80",
            specialization = "Зумба"
        ),
        Master(
            id = "master_kizomba_1",
            name = "Екатерина Лебедева",
            rating = 4.8,
            bio = "Эксперт по кизомбе с международным опытом выступлений.",
            image_url = "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?auto=format&fit=crop&w=800&q=80",
            specialization = "Кизомба"
        ),
        Master(
            id = "master_high_hills_1",
            name = "Ольга Романова",
            rating = 4.6,
            bio = "Специалист по стилю High Hills — грация и уверенность на каблуках.",
            image_url = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=800&q=80",
            specialization = "High Hills"
        ),
        Master(
            id = "master_lady_dance_1",
            name = "Александра Воронова",
            rating = 4.9,
            bio = "Профессиональный тренер Lady Dance с индивидуальным подходом.",
            image_url = "https://images.unsplash.com/photo-1508214751197-f4f14a9c14b8?auto=format&fit=crop&w=800&q=80",
            specialization = "Lady Dance"
        ),
        Master(
            id = "master_striptease_1",
            name = "Ирина Волкова",
            rating = 4.7,
            bio = "Профессиональная преподавательница стриптиза с опытом более 6 лет. Помогает раскрыть уверенность и женственность.",
            image_url = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=800&q=80",
            specialization = "Стриптиз"
        )

    )
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
                            text = "Выберите мастера",
                            style = MaterialTheme.typography.headlineSmall,
                            color = salonGreenOnPrimaryContainer
                        )
                        Text(
                            text = "${masters.size} мастеров",
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
                                label = { Text("Рейтинг") },
                                leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) }
                            )
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = { Text("Опыт") },
                                leadingIcon = { Icon(Icons.Default.Work, contentDescription = null) }
                            )
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = { Text("Цена") },
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) }
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
                    items = masters,
                    key = { it.id }
                ) { master ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                                .clickable {
                                    onMasterClick(master.id, master.name)
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = master.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = master.specialization,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "4.9",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        Text(
                                            text = "(45 отзывов)",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = { onMasterClick(master.id, master.name) },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            salonGreenPrimaryContainer,
                                            RoundedCornerShape(20.dp)
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Выбрать",
                                        tint = salonGreenOnPrimaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}