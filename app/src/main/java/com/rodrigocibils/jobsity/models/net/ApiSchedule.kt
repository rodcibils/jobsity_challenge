package com.rodrigocibils.jobsity.models.net

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiSchedule(
    @SerializedName("time")
    val time: String,

    @SerializedName("days")
    val days: List<String>
): Serializable