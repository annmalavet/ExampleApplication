package com.malavet.exampleapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LayoutOrder(
    val profile: List<String>
)