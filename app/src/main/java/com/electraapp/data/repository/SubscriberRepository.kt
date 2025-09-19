package com.electraapp.data.repository

import com.electraapp.data.dao.SubscriberDao
import com.electraapp.data.model.Subscriber
import kotlinx.coroutines.flow.Flow

class SubscriberRepository(private val subscriberDao: SubscriberDao) {

    val allSubscribers: Flow<List<Subscriber>> = subscriberDao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber) {
        subscriberDao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber) {
        subscriberDao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber) {
        subscriberDao.deleteSubscriber(subscriber)
    }

    suspend fun getSubscriberById(id: Int): Subscriber? {
        return subscriberDao.getSubscriberById(id)
    }
}


