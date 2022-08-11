package com.app.presentation.ui.onBoarding

import android.os.Bundle
import com.app.base.BaseAppCompatActivity
import com.app.databinding.ActivitySignInBinding
import com.app.databinding.ActivitySignUpBinding
import com.app.presentation.extension.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseAppCompatActivity() {
    private val binding by viewBinding(ActivitySignUpBinding::inflate)

    override fun layout() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}