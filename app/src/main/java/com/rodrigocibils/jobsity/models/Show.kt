package com.rodrigocibils.jobsity.models

import java.io.Serializable

data class Show(
    val id: Int,
    val name: String,
    val imageUrl: String
): Serializable