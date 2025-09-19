package com.electraapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.electraapp.data.model.Subscriber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSubscriberScreen(
    subscriber: Subscriber?,
    onSaveClick: (Subscriber) -> Unit,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf(subscriber?.name ?: "") }
    var nationalId by remember { mutableStateOf(subscriber?.nationalId ?: "") }
    var amperage by remember { mutableStateOf(subscriber?.amperage?.toString() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (subscriber == null) "إضافة مشترك جديد" else "تعديل مشترك") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "العودة")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("الاسم") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nationalId,
                onValueChange = { nationalId = it },
                label = { Text("رقم الهوية") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = amperage,
                onValueChange = { amperage = it },
                label = { Text("عدد الأمبيرات") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newSubscriber = (subscriber?.copy(
                        name = name,
                        nationalId = nationalId,
                        amperage = amperage.toIntOrNull() ?: 0
                    ) ?: Subscriber(
                        name = name,
                        nationalId = nationalId,
                        amperage = amperage.toIntOrNull() ?: 0
                    ))
                    onSaveClick(newSubscriber)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && nationalId.isNotBlank() && amperage.toIntOrNull() != null
            ) {
                Text(if (subscriber == null) "حفظ المشترك" else "تحديث المشترك")
            }
        }
    }
}


