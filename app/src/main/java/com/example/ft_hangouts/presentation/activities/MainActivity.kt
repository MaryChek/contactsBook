package com.example.ft_hangouts.presentation.activities

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
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
import com.example.ft_hangouts.presentation.fragments.RegistrationActivityResult
import com.example.ft_hangouts.presentation.navigation.FromMainActivity
import com.example.ft_hangouts.presentation.viewmodels.RootViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RootViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        createViewModel()
        setupObserves()
        initNavigation()
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onActivityResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onActivityPause()
    }

    fun callNumber(number: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
    }
}