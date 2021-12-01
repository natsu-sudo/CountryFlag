package com.assignment.onefitplus.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "country_table")
data class CountryDetail(
    @SerializedName("country")
    @ColumnInfo(name="country")
    val country:String,
    val name:String,
    @PrimaryKey
    @SerializedName("_id")
    @ColumnInfo(name = "id")
    val id:Long,
    @SerializedName("coord")
    @ColumnInfo(name = "coordinate")
    val coordinate:Coordinate


)