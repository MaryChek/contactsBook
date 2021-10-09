package com.example.ft_hangouts.presentation.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ft_hangouts.R
import com.example.ft_hangouts.databinding.ActivityMainBinding
import com.example.ft_hangouts.presentation.App
import com.example.ft_hangouts.presentation.navigation.FromMainActivity
import com.example.ft_hangouts.presentation.viewmodels.RootViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RootViewModel
    private lateinit var intentFilter: IntentFilter

//    private val intentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            viewModel.onSmsReceive(intent)
//            onResume()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        createViewModel()
        setupObserves()
        initNavigation()
        initIntentFilter()
    }

    private fun createViewModel() {
        val app: App = applicationContext as App
        val viewModelFactory = app.viewModelFactory
        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        viewModel = viewModelProvider.get(RootViewModel::class.java)
    }

    private fun setupObserves() {
        viewModel.actionUpdated.observe(this, Observer(this::navigateTo))
    }

    private fun navigateTo(destination: FromMainActivity) {
        when (destination) {
            is FromMainActivity.Navigate.ShowToast ->
                showMessageBackgroundTime(destination.time)
        }
    }

    private fun showMessageBackgroundTime(time: Int) {
        val message: String = resources.getQuantityString(
            R.plurals.message_background_time, time, time)
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
            .show()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(navController)
    }

    private fun initIntentFilter() {
        intentFilter = IntentFilter()
        intentFilter.addAction("SMS_RECEIVED_ACTION")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
//
    override fun onResume() {
        super.onResume()
        viewModel.onActivityResume()
//        registerReceiver(intentReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onActivityPause()
//        unregisterReceiver(intentReceiver)
    }
}