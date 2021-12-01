package com.assignment.onefitplus.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.onefitplus.pojo.User
import java.lang.IllegalArgumentException

class LoginFactory(private val context: Context):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
                    return LoginViewModel(context) as T
                }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}