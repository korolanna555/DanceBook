package com.students.salonapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.students.salonapp.ui.theme.salonGreenOnPrimaryContainer
import com.students.salonapp.ui.theme.salonGreenPrimaryContainer
import com.students.salonapp.ui.theme.salonSurfaceLight


// Мок-данные для постов
private val samplePosts = listOf(
    InstagramPost(
        id = "1",
        title = "Новые групповые занятия по сальсе",
        description = "Приглашаем всех желающих на увлекательные уроки сальсы! Развивайте пластичность и ритм с нами.",
        imageUrl = "https://images.unsplash.com/photo-1526947425960-945c6e72847b?auto=format&fit=crop&w=800&q=80",
        likes = 178,
        date = "3 часа назад"
    ),
    InstagramPost(
        id = "2",
        title = "Мастер-класс по хип-хопу",
        description = "Наш преподаватель Алексей провёл мощный мастер-класс по хип-хопу. Смотрите лучшие моменты в фотоотчёте!",
        imageUrl = "https://images.unsplash.com/photo-1546484959-f2bb631dd160?auto=format&fit=crop&w=800&q=80",
        likes = 142,
        date = "6 часов назад"
    ),
    InstagramPost(
        id = "3",
        title = "Приглашаем на открытые уроки по танго",
        description = "Испытайте страсть и эмоции аргентинского танго вместе с нами. Записывайтесь на бесплатные пробные занятия!",
        imageUrl = "https://images.unsplash.com/photo-1508609349937-5ec4ae374ebf?auto=format&fit=crop&w=800&q=80",
        likes = 210,
        date = "1 день назад"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstagramFeedScreen(
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Фоновый градиент
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
            // Верхняя панель
            TopAppBar(
                title = {
                    Text(
                        text = "Instagram",
                        style = MaterialTheme.typography.headlineSmall,
                        color = salonGreenOnPrimaryContainer
                    )
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
                    val context = LocalContext.current
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, "https://instagram.com".toUri())
                        context.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Launch,
                            contentDescription = "Открыть Instagram",
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

            // Основной контент
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(samplePosts) { post ->
                    PostCard(post = post)
                }
            }
        }
    }
}

@Composable
private fun PostCard(post: InstagramPost) {
    var isLiked by remember { mutableStateOf(false) }
    var likesCount by remember { mutableIntStateOf(post.likes) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            // Изображение
            AsyncImage(
                model = post.imageUrl,
                contentDescription = post.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            // Информация о посте
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Заголовок и описание
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Кнопки действий
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Кнопка лайка
                    IconButton(
                        onClick = {
                            isLiked = !isLiked
                            likesCount += if (isLiked) 1 else -1
                        }
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Лайк",
                            tint = if (isLiked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Счетчик лайков
                    Text(
                        text = "$likesCount",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Дата публикации
                    Text(
                        text = post.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

data class InstagramPost(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val likes: Int,
    val date: String
) 