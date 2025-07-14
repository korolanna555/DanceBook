package com.students.salonapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.students.salonapp.ui.theme.salonGreenOnPrimaryContainer
import com.students.salonapp.ui.theme.salonGreenPrimaryContainer
import com.students.salonapp.ui.theme.salonSurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseScreen(
    works: List<ShowcaseWork> = listOf(
        ShowcaseWork(
            id = "4",
            title = "Современный джаз-фанк",
            description = "Энергичный танец с элементами хип-хопа и джаза, раскрывающий пластичность и ритм.",
            imageUrl = "https://avatars.mds.yandex.net/get-altay/5437182/2a0000017a20b0dc510e1d5a17a8057387e6/XXXL",
            category = "Танцы",
            master = "Александра Волкова",
            date = "2025-05-20"
        ),
        ShowcaseWork(
            id = "5",
            title = "Классический балет",
            description = "Уроки по основам балета: осанка, позиции и грация в движении.",
            imageUrl = "https://avatars.mds.yandex.net/get-altay/4447702/2a0000017ba9c15f4525e51e40d073777bf5/XXXL",
            category = "Танцы",
            master = "Ольга Сергеева",
            date = "2025-06-05"
        ),
        ShowcaseWork(
            id = "6",
            title = "Латина – сальса и бачата",
            description = "Зажигательные латинские ритмы, парные и сольные комбинации.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=79be1d2e84ef4d4f2d364faed2db68eec0c5dffb-10768194-images-thumbs&n=13",
            category = "Танцы",
            master = "Дмитрий Орлов",
            date = "2025-06-12"
        ),
        ShowcaseWork(
            id = "7",
            title = "Хип-хоп для начинающих",
            description = "Базовые движения, ритмика и свобода самовыражения в танце.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=12a597dc22106c5a0b75e1b037b3b1d866f158b4-10372823-images-thumbs&n=13",
            category = "Танцы",
            master = "Кирилл Егоров",
            date = "2025-06-18"
        )
    ),
    onWorkSelected: (ShowcaseWork) -> Unit,
    onBack: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showFilters by remember { mutableStateOf(false) }

    rememberInfiniteTransition(label = "filter")

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
                    Text(
                        text = "Портфолио",
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
                    IconButton(
                        onClick = { showFilters = !showFilters }
                    ) {
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    AnimatedVisibility(
                        visible = showFilters,
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Категории",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }

                items(
                    works.filter { work ->
                        selectedCategory == null || selectedCategory == "Все" || work.category == selectedCategory
                    }
                ) { work ->
                    WorkCard(
                        work = work,
                        onClick = { onWorkSelected(work) }
                    )
                }
            }
        }
    }
}

@Composable
private fun WorkCard(
    work: ShowcaseWork,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = onClick
    ) {
        Column {
            AsyncImage(
                model = work.imageUrl,
                contentDescription = work.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = work.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = work.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = work.master,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    TextButton(
                        onClick = onClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Подробнее")
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

data class ShowcaseWork(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val category: String,
    val master: String,
    val date: String
)
