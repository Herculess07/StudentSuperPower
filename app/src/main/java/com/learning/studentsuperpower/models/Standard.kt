package com.learning.studentsuperpower.models

import com.google.gson.annotations.SerializedName
import com.learning.studentsuperpower.adapters.HomeStdAdapter

data class Standard(
    @SerializedName("id")
    val stdId: Int,
    @SerializedName("standard_name")
    val stdName: String
)