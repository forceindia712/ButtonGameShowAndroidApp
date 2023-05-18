package com.gameshow.button.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gameshow.button.presentation.databinding.ActivityMainBinding
import com.gameshow.button.presentation.viewmodel.ProfileViewModel
import com.gameshow.button.presentation.viewmodel.SocketViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val profileViewModel by viewModel<ProfileViewModel>()
    private val socketViewModel by viewModel<SocketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}