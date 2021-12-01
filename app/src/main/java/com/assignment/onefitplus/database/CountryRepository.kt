package com.assignment.onefitplus.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.assignment.onefitplus.network.ApiService
import com.assignment.onefitplus.pojo.CountryDetail
import com.assignment.onefitplus.pojo.ErrorCode
import com.assignment.onefitplus.pojo.LoadingStatus
import java.net.UnknownHostException

private const val TAG = "CountryRepository"
class CountryRepository(context: Context) {
    private val countryListRepo=CountryDatabase.getInstance(context).countryListDao()
        private val apiService by lazy { ApiService.getInstance() }

    fun getCountryList(): LiveData<List<CountryDetail>> {
        return countryListRepo.geCountyListFromDB()
    }

    suspend fun deleteFromDataBase(){
        countryListRepo.deleteAllData()
    }

    suspend fun fetchFromNetwork()=try {
        val result=apiService.getJsonRespondList()
        Log.d(TAG, "fetchFromNetwork 1: $result")
        if (result.isSuccessful){
            val listOfCountry=result.body()
            Log.d(TAG, "fetchFromNetwork: $listOfCountry")
            listOfCountry?.let {
                countryListRepo.insertIntoDb(it)
            }
            LoadingStatus.success()
        }else{
            Log.e(TAG, "fetchFromNetwork: No Data")
            LoadingStatus.error(ErrorCode.NO_DATA)
        }
    }catch (ex: UnknownHostException){
        Log.e(TAG, ex.message.toString() )
        LoadingStatus.error(ErrorCode.NETWORK_ERROR)
    }catch (ex: Exception){
        Log.e(TAG, ex.message.toString())
        LoadingStatus.error(ErrorCode.UNKNOWN_ERROR)
    }





}