package com.rodrigocibils.jobsity.models.net

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiSearch(
    @SerializedName("score")
    val score: Float,

    @SerializedName("show")
    val show: ApiShow
): Serializable