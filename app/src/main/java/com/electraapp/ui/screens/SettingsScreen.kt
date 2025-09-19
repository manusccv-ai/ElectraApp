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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentAmperePrice: Double,
    onSavePrice: (Double) -> Unit,
    onBackClick: () -> Unit
) {
    var amperePriceText by remember { mutableStateOf(currentAmperePrice.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("الإعدادات") },
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
                value = amperePriceText,
                onValueChange = { newValue ->
                    amperePriceText = newValue
                },
                label = { Text("سعر الأمبير الشهري") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newPrice = amperePriceText.toDoubleOrNull()
                    if (newPrice != null) {
                        onSavePrice(newPrice)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = amperePriceText.toDoubleOrNull() != null && amperePriceText.isNotBlank()
            ) {
                Text("حفظ السعر")
            }
        }
    }
}


