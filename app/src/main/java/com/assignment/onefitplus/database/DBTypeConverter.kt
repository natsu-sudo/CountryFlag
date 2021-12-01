package com.assignment.onefitplus.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.assignment.onefitplus.pojo.Coordinate
import com.google.gson.Gson

@TypeConverters
class DBTypeConverter {

    @TypeConverter
    fun fromCoordinateToString(coordinate:Coordinate?):String?{
        return if (coordinate==null) null else Gson().toJson(coordinate)
    }

    @TypeConverter
    fun fromStringToCoordinate(string: String?):Coordinate?{
        return Gson().fromJson(string,Coordinate::class.java)
    }


}