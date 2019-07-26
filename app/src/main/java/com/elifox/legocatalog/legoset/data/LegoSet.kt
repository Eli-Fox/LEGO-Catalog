package com.elifox.legocatalog.legoset.data

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
        val imageUrl: String? = null,
        @field:SerializedName("theme_id")
        val themeId: Int,
        @field:SerializedName("last_modified_dt")
        val lastModifiedDate: String? = null,
        @field:SerializedName("num_parts")
        val numParts: Int? = null,
        @field:SerializedName("set_url")
        val url: String? = null,
        @field:SerializedName("year")
        val year: Int? = null
) {
    override fun toString() = name
}