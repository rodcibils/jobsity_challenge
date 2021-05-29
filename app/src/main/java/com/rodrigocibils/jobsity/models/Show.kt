package com.rodrigocibils.jobsity.models

import java.io.Serializable

data class Show(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val summary: String,
    val time: String,
    val days: List<String>,
    val genres: List<String>
): Serializable