package com.assignment.onefitplus.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.onefitplus.pojo.CountryDetail

@Dao
interface CountryListDao {

    @Query("select * from country_table")
    fun geCountyListFromDB():LiveData<List<CountryDetail>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoDb(list: List<CountryDetail>)

    @Query("delete from country_table")
    suspend fun deleteAllData()
}