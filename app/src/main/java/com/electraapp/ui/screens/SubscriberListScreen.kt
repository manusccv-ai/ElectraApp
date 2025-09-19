package com.electraapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.electraapp.data.model.Subscriber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriberListScreen(
    subscribers: List<Subscriber>,
    onAddSubscriberClick: () -> Unit,
    onSubscriberClick: (Subscriber) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("قائمة المشتركين") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSubscriberClick) {
                Icon(Icons.Filled.Add, "إضافة مشترك جديد")
            }
        }
    ) {\ paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(subscribers) {
                SubscriberListItem(subscriber = it, onSubscriberClick = onSubscriberClick)
            }
        }
    }
}

@Composable
fun SubscriberListItem(subscriber: Subscriber, onSubscriberClick: (Subscriber) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onSubscriberClick(subscriber) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "الاسم: ${subscriber.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "رقم الهوية: ${subscriber.nationalId}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "عدد الأمبيرات: ${subscriber.amperage}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "حالة الدفع: ${if (subscriber.isPaid) "مدفوع ✅" else "غير مدفوع ❌"}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


