package com.elifox.legocatalog.api


import com.google.gson.annotations.SerializedName

data class ResultsResponse<T>(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<T>
)