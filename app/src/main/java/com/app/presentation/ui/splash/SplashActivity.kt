package com.app.presentation.ui.splash

import android.os.Bundle
import com.app.base.BaseAppCompatActivity
import com.app.databinding.ActivitySplashBinding
import com.app.presentation.extension.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseAppCompatActivity() {
    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun layout() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}