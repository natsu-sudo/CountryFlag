package com.assignment.onefitplus.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.assignment.onefitplus.pojo.CountryDetail

@TypeConverters(DBTypeConverter::class)
@Database(entities = [CountryDetail::class],version = 1)
abstract class CountryDatabase: RoomDatabase() {
    abstract fun countryListDao():CountryListDao

    companion object{
        @Volatile
        private var instance:CountryDatabase?=null

        fun getInstance(context: Context)= instance?: synchronized(this){
            Room.databaseBuilder(context.applicationContext,CountryDatabase::class.java
                ,"movie_database").build().also {
                instance=it
            }
        }
    }
}