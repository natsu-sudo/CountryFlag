package com.assignment.onefitplus.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.onefitplus.database.CountryRepository
import com.assignment.onefitplus.pojo.CountryDetail
import com.assignment.onefitplus.pojo.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryViewModel(context: Context):ViewModel() {
    private val database=CountryRepository(context)
    private var liveStatus= MutableLiveData<LoadingStatus>()
    val getList:LiveData<List<CountryDetail>> = getCountryList()
    val status get() = liveStatus

    private fun getCountryList(): LiveData<List<CountryDetail>> {
        return database.getCountryList()
    }

    fun fetchFromNetwork(){
        liveStatus.value=LoadingStatus.loading()
        viewModelScope.launch {
            liveStatus.value = withContext(Dispatchers.IO){
                database.fetchFromNetwork()
            }!!
        }
    }

    fun deleteData() {
        viewModelScope.launch {
            database.deleteFromDataBase()
        }
    }




}