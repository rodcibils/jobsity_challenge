package com.rodrigocibils.jobsity.models.net

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiShow(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: ApiImage?,

    @SerializedName("genres")
    val genres: List<String>,

    @SerializedName("schedule")
    val schedule: ApiSchedule,

    @SerializedName("summary")
    val summary: String?
): Serializable