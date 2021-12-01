package com.assignment.onefitplus.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.onefitplus.R
import com.assignment.onefitplus.databinding.FragmentCountryBinding
import com.assignment.onefitplus.databinding.FragmentLoginBinding
import com.assignment.onefitplus.network.ApiService
import com.assignment.onefitplus.pojo.ErrorCode
import com.assignment.onefitplus.pojo.Status
import com.assignment.onefitplus.viewmodel.CountryViewModel
import com.assignment.onefitplus.viewmodel.CountryViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private const val TAG = "CountryFragment"
class CountryFragment : Fragment() {

    lateinit var binding:FragmentCountryBinding
    lateinit var countryViewModel:CountryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryViewModel=ViewModelProvider(this,CountryViewModelFactory(requireContext()))[CountryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCountryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerCateList.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=CountryAdapter{
                findNavController().navigate(CountryFragmentDirections.actionCountryFragmentToMapsFragment(it.name,it.coordinate.lat.toString(),it.coordinate.lon.toString()))
            }
            setHasFixedSize(false);
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        }

        countryViewModel.getList.observe(viewLifecycleOwner, Observer {
            (binding.recyclerCateList.adapter as CountryAdapter).submitList(it)
            if (it.isEmpty()) {
                countryViewModel.fetchFromNetwork()
            }
        })

        countryViewModel.status.observe(viewLifecycleOwner, Observer { loadingStatus ->
            when (loadingStatus?.status) {
                (Status.LOADING) -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(TAG, "onViewCreated: Status loading")
                }
                (Status.SUCCESS) -> {
                    binding.progressBar.visibility = View.GONE
                }
                (Status.ERROR) -> {
                    binding.loadingStatusText.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    showError(loadingStatus.error, loadingStatus.message)
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
            binding.swipeUp.isRefreshing = false
        })

        binding.swipeUp.setOnRefreshListener {
            Log.d(TAG, "onViewCreated: " + countryViewModel.getList.value?.isEmpty())
            if (countryViewModel.getList.value?.isEmpty() != true && isOnline(requireActivity())){
                countryViewModel.deleteData()
            }else{
                Snackbar.make(binding.root, getString(R.string.network_error), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.ok)) {

                    }
                    .show()
                binding.swipeUp.isRefreshing=false
            }

        }

    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun showError(errorCode: ErrorCode?, message: String?) {
        Log.d(TAG, "showError: ")
        when (errorCode) {
            ErrorCode.NO_DATA -> binding.loadingStatusText.text = getString(R.string.error_no_data)
            ErrorCode.NETWORK_ERROR -> binding.loadingStatusText.text =
                getString(R.string.error_network)
            ErrorCode.UNKNOWN_ERROR -> binding.loadingStatusText.text =
                getString(R.string.error_unknown, message)
        }
    }




}