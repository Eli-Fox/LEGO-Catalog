package com.elifox.legocatalog.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "sets")
data class LegoSet(
        @PrimaryKey
        @field:SerializedName("set_num")
        val id: String,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("set_img_url")
        val imageUrl: String,
        @field:SerializedName("theme_id")
        val themeId: Int,
        @field:SerializedName("last_modified_dt")
        val lastModifiedDate: String,
        @field:SerializedName("num_parts")
        val numParts: Int,
        @field:SerializedName("set_url")
        val url: String,
        @field:SerializedName("year")
        val year: Int
) {
    override fun toString() = name
}