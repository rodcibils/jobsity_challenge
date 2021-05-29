package com.rodrigocibils.jobsity.models

import java.io.Serializable

data class Episode(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val summary: String,
    val imageUrl: String?
): Serializable