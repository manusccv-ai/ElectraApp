package com.electraapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.electraapp.data.model.Subscriber
import com.electraapp.data.repository.SubscriberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ElectraViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val allSubscribers: StateFlow<List<Subscriber>> = repository.allSubscribers.asStateFlow()

    private val _monthlyAmperePrice = MutableStateFlow(10.0) // Default price
    val monthlyAmperePrice: StateFlow<Double> = _monthlyAmperePrice.asStateFlow()

    private val _selectedSubscriber = MutableStateFlow<Subscriber?>(null)
    val selectedSubscriber: StateFlow<Subscriber?> = _selectedSubscriber.asStateFlow()

    fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.insert(subscriber)
    }

    fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.update(subscriber)
    }

    fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
    }

    fun setMonthlyAmperePrice(price: Double) {
        _monthlyAmperePrice.value = price
    }

    fun selectSubscriber(subscriberId: Int?) = viewModelScope.launch {
        _selectedSubscriber.value = if (subscriberId == null) null else repository.getSubscriberById(subscriberId)
    }

    fun togglePaidStatus(subscriber: Subscriber) = viewModelScope.launch {
        val updatedSubscriber = subscriber.copy(isPaid = !subscriber.isPaid)
        repository.update(updatedSubscriber)
    }
}

class ElectraViewModelFactory(private val repository: SubscriberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ElectraViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


