package com.electraapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscribers")
data class Subscriber(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val nationalId: String,
    val amperage: Int,
    var isPaid: Boolean = false
)


