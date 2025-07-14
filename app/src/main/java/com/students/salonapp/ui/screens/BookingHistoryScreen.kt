package com.students.salonapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.students.salonapp.data.uiState.BookingHistoryUiState
import com.students.salonapp.ui.theme.salonGreenOnPrimaryContainer
import com.students.salonapp.ui.theme.salonGreenPrimaryContainer
import com.students.salonapp.ui.theme.salonSurfaceLight
import com.students.salonapp.viewmodel.BookingViewModel

data class BookingDisplay(
    val id: String,
    val service_name: String,
    val master_name: String,
    val date: String,
    val time: String,
    val total_price: Double,
    val status: String = "Предстоящая"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(
    onBack: () -> Unit,
    bookingViewModel: BookingViewModel = viewModel()
) {
    val userId = "test_user"
    LaunchedEffect(userId) {
        println("[DEBUG] BookingHistoryScreen userId: $userId")
    }

    val historyUiState by bookingViewModel.historyUiState.collectAsState()
    val repository = remember { com.students.salonapp.data.supabase.SupabaseRepository(useStub = false) }
    var displayBookings by remember { mutableStateOf<List<BookingDisplay>>(emptyList()) }
    var isDisplayLoading by remember { mutableStateOf(false) }
    var displayError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId) {
        bookingViewModel.loadUserBookingsWithFallback(userId)
    }

    when (historyUiState) {
        is BookingHistoryUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }
        is BookingHistoryUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ошибка: " + (historyUiState as BookingHistoryUiState.Error).message)
            }
            return
        }
        is BookingHistoryUiState.Success -> {
            val bookings = (historyUiState as BookingHistoryUiState.Success).bookings
            // Подгружаем имена услуги и мастера только если ещё не отображали
            LaunchedEffect(bookings) {
                isDisplayLoading = true
                displayError = null
                try {
                    val displayList = bookings.map { booking ->
                        val service = repository.getServiceById(booking.service_id)
                        val master = repository.getMasterById(booking.master_id)
                        BookingDisplay(
                            id = booking.id,
                            service_name = service?.name ?: booking.service_id,
                            master_name = master?.name ?: booking.master_id,
                            date = booking.date,
                            time = booking.time,
                            total_price = booking.total_price
                        )
                    }
                    displayBookings = displayList
                } catch (e: Exception) {
                    displayError = e.message
                } finally {
                    isDisplayLoading = false
                }
            }
            if (isDisplayLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                return
            }
            if (displayError != null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка: $displayError")
                }
                return
            }

            var selectedStatus by remember { mutableStateOf<String?>(null) }
            var showFilters by remember { mutableStateOf(false) }

            // Анимация для фильтров

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
                            Column {
                                Text(
                                    text = "История записей",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = salonGreenOnPrimaryContainer
                                )
                                Text(
                                    text = "${bookings.size} записей",
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

                    // Статусы
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Все", "Предстоящие", "Завершенные").forEach { status ->
                            FilterChip(
                                selected = status == selectedStatus,
                                onClick = { selectedStatus = status },
                                label = { Text(status) },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }

                    // Фильтры с анимацией
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
                                        label = { Text("Дата") },
                                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }
                                    )
                                    FilterChip(
                                        selected = false,
                                        onClick = { },
                                        label = { Text("Услуга") },
                                        leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) }
                                    )
                                    FilterChip(
                                        selected = false,
                                        onClick = { },
                                        label = { Text("Мастер") },
                                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
                                    )
                                }
                            }
                        }
                    }

                    // Список записей
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(
                            items = displayBookings,
                            key = { it.id }
                        ) { booking ->
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn() + slideInVertically()
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .shadow(2.dp, RoundedCornerShape(12.dp)),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        // Заголовок
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = booking.service_name,
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            StatusChip(booking.status)
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Информация
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = booking.master_name,
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.DateRange,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "${booking.date} в ${booking.time}",
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AttachMoney,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "${booking.total_price} ₽",
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }

                                        if (booking.status == "Предстоящая") {
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                TextButton(
                                                    onClick = { },
                                                    colors = ButtonDefaults.textButtonColors(
                                                        contentColor = MaterialTheme.colorScheme.error
                                                    )
                                                ) {
                                                    Text("Отменить")
                                                }
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Button(
                                                    onClick = { },
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = salonGreenPrimaryContainer,
                                                        contentColor = salonGreenOnPrimaryContainer
                                                    ),
                                                    shape = RoundedCornerShape(24.dp)
                                                ) {
                                                    Text("Перенести")
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
        BookingHistoryUiState.Idle -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Нет данных для отображения")
            }
            return
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (backgroundColor, contentColor) = when (status) {
        "Предстоящая" -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.primary
        "Завершенная" -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.secondary
        "Отмененная" -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        color = backgroundColor
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelMedium,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
