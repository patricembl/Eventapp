package com.example.android.eventapp.models

import java.io.Serializable

data class EventModel(
    val name: String,
    val category: String,
    val description: String,
    val date: String
) : Serializable
