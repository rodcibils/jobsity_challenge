package com.rodrigocibils.jobsity.models.net

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiEpisode(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("season")
    val season: Int,

    @SerializedName("number")
    val number: Int,

    @SerializedName("summary")
    val summary: String,

    @SerializedName("image")
    val image: ApiImage?
): Serializable