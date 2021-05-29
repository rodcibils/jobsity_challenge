package com.rodrigocibils.jobsity.models.net

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiImage(
    @SerializedName("medium")
    val medium: String?
): Serializable