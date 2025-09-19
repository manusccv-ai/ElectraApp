package com.electraapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.electraapp.data.model.Subscriber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriberDetailScreen(
    subscriber: Subscriber?,
    onBackClick: () -> Unit,
    onEditClick: (Subscriber) -> Unit,
    onDeleteClick: (Subscriber) -> Unit,
    onTogglePaidStatus: (Subscriber) -> Unit,
    monthlyAmperePrice: Double
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تفاصيل المشترك") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "العودة")
                    }
                },
                actions = {
                    subscriber?.let {
                        IconButton(onClick = { onEditClick(it) }) {
                            Icon(Icons.Filled.Edit, "تعديل")
                        }
                        IconButton(onClick = { onDeleteClick(it) }) {
                            Icon(Icons.Filled.Delete, "حذف")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (subscriber == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("المشترك غير موجود")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(text = "الاسم: ${subscriber.name}", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "رقم الهوية: ${subscriber.nationalId}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "عدد الأمبيرات: ${subscriber.amperage}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))

                val amountDue = subscriber.amperage * monthlyAmperePrice
                Text(text = "المبلغ المستحق: ${String.format("%.2f", amountDue)} دينار", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Checkbox(checked = subscriber.isPaid, onCheckedChange = { onTogglePaidStatus(subscriber) })
                    Text(text = "مدفوع", modifier = Modifier.clickable { onTogglePaidStatus(subscriber) })
                }
            }
        }
    }
}


