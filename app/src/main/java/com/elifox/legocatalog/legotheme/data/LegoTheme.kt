package com.elifox.legocatalog.legotheme.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "themes")
data class LegoTheme(
        @PrimaryKey
        @field:SerializedName("id")
        val id: Int,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("parent_id")
        val parentId: Int
) {
    override fun toString() = name
}