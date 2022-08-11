package com.app.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.base.BaseFragment
import com.app.data.local.sharedprefs.AppSharedPreferences
import com.app.data.utils.AppConstants.USER_DATA
import com.app.databinding.FragmentSplashBinding
import com.app.presentation.extension.AppLayout
import com.app.presentation.extension.startActivity
import com.app.presentation.ui.onBoarding.SignInActivity
import com.app.presentation.vm.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SplashFragment : BaseFragment(AppLayout.fragment_splash) {

    @Inject
    lateinit var appSharedPreferences: AppSharedPreferences

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<OnBoardingViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = _binding ?: FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(view: View) {
        initialize()
    }

    @ExperimentalCoroutinesApi
    private fun initialize() {
        lifecycleScope.launch {
            flow {
                delay(3000)
                emit(true)
            }.collect {
                val launch = appSharedPreferences.get(USER_DATA, String()::class.java)
                Timber.d("Shared User data :: $launch")
                viewModel.getFirstTimeUser()
            }
        }
    }


    override fun observeLiveData() {

        viewModel.observeIsUserFirstTimeLaunchLive.observe(viewLifecycleOwner) {
            Timber.d("Value :: $it")
            navigate(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate(data: Boolean) {
        Timber.d("navigate boolean :: $data")
        if (!data) {
            navController.navigate(SplashFragmentDirections.navigateToWalkThroughFragment())
        } else {
            startActivity<SignInActivity>()
            finish()
        }
    }

}