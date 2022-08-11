package com.app.presentation.ui.onBoarding

import android.os.Bundle
import com.app.base.BaseAppCompatActivity
import com.app.databinding.ActivitySignInBinding
import com.app.presentation.extension.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseAppCompatActivity() {
    private val binding by viewBinding(ActivitySignInBinding::inflate)

    override fun layout() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}