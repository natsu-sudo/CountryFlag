package com.assignment.onefitplus.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.onefitplus.constants.Constants
import com.assignment.onefitplus.pojo.User
import com.assignment.onefitplus.sharedprefernces.UserPreferences

private const val TAG = "LoginViewModel"
class LoginViewModel(private val context: Context): ViewModel() {
     var username: MutableLiveData<String> = MutableLiveData()
     var password: MutableLiveData<String> = MutableLiveData()
     var usernameError: MutableLiveData<String> = MutableLiveData()
     var passwordError: MutableLiveData<String> = MutableLiveData()
     var isCheckBoxed:MutableLiveData<Boolean> = MutableLiveData()
    private val _authenticated:MutableLiveData<Boolean> = MutableLiveData(false)
    val authenticated get() = isCredentialMatch()

    private fun isCredentialMatch(): LiveData<Boolean> {
        return _authenticated
    }


    fun initializeValue(){
        val user_name=
            context.let { UserPreferences.initializeSecureSharedPrefernces(it).getString(Constants.USER_NAME,"") }
        val pass_word=
            context.let { UserPreferences.initializeSecureSharedPrefernces(it).getString(Constants.PASSWORD,"") }
        username.value=user_name
        password.value=pass_word
        isCheckBoxed.value=false
    }

    fun onSignInClicked() {
        usernameError.value = null
        passwordError.value = null
        val user = User(username.value!!, password.value!!)
        if (username.value!!.isEmpty()) {
            usernameError.value = "Enter User Name "
            return
        }

        if (password.value == null || password.value!!.isEmpty()) {
            passwordError.value = "Enter password."
            return
        }
        Log.d(TAG, "onSignInClicked: "+isCheckBoxed.value)
        if (isCheckBoxed.value == true){
            UserPreferences.initializeSecureSharedPrefernces(context).edit()
                .putString(Constants.USER_NAME,username.value)
                .putString(Constants.PASSWORD,password.value)
                .apply()
        }
        _authenticated.value = username.value.equals(Constants.USER_NAME_CREDENTIAL) && password.value.equals(Constants.PASSWORD_CREDENTIAL)
    }
}