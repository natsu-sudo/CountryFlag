package com.assignment.onefitplus.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.assignment.onefitplus.R
import com.assignment.onefitplus.databinding.FragmentLoginBinding
import com.assignment.onefitplus.viewmodel.LoginFactory
import com.assignment.onefitplus.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

private const val TAG = "LoginFragment"
class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    private lateinit var loginViewModel:LoginViewModel
    var isFirstTime=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel=ViewModelProvider(viewModelStore, LoginFactory(requireActivity()))[LoginViewModel::class.java]
        loginViewModel.initializeValue()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.authenticated.observe(viewLifecycleOwner, Observer {
            if (it){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCountryFragment())
            }else{
                if (isFirstTime){
                    Snackbar.make(binding.root,getString(R.string.wrong_credential),Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.ok)){
                            }
                            .show()
                }else{
                    isFirstTime=true
                }
            }
        })


    }


}