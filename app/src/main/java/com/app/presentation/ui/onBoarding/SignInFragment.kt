package com.app.presentation.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.app.base.BaseFragment
import com.app.base.hideLoader
import com.app.base.showLoader
import com.app.databinding.FragmentSignInBinding
import com.app.domain.entity.request.SignInRequest
import com.app.domain.model.FirebaseCallResponse
import com.app.domain.model.ViewState
import com.app.presentation.extension.*
import com.app.presentation.vm.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignInFragment : BaseFragment(AppLayout.fragment_sign_in) {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<OnBoardingViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = _binding ?: FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(view: View) {
        initialize()
    }


    private fun initialize() {
        getFirebaseDeviceId()
        getFirebaseMessageToken()
        binding.btnSubmit.click {
            hideKeyboard()
            validateSignIn()
        }

        binding.linSignUp.click {
            hideKeyboard()
            startActivity<SignUpActivity>()
        }

    }

    private fun getFirebaseDeviceId() {
        fragmentActivity?.showLoader()
        viewModel.getDeviceId()
    }

    private fun getFirebaseMessageToken() {
        fragmentActivity?.showLoader()
        viewModel.getMessageToken()
    }

    private fun validateSignIn() {
        val signInRequest = SignInRequest(
            binding.etUsername.text.toString().trim(),
            binding.etPassword.text.toString().trim()
        )
        viewModel.validateSignInData(signInRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun observeLiveData() {

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, getString(it), Toast.LENGTH_LONG).show()
        }

        viewModel.firebaseDeviceIdResponse.observe(viewLifecycleOwner) {
            fragmentActivity?.hideLoader()
            Timber.d("FirebaseDeviceidResponse-->${it}")
            when (it) {
                is FirebaseCallResponse.Success -> {
                    it.data.let { data ->
                        Timber.d("device id-->${data.deviceId}")
                    }
                }
                is FirebaseCallResponse.Failure -> {
                    binding.constraintLayout.snackBar(it.throwable.message)
                }
                else -> {
                    binding.constraintLayout.snackBar(AppString.error_message)
                }
            }
        }

        viewModel.firebaseMessageTokenResponse.observe(viewLifecycleOwner) {
            fragmentActivity?.hideLoader()
            Timber.d("FirebaseTokenResponse-->${it}")
            when (it) {
                is FirebaseCallResponse.Success -> {
                    it.data.let { data ->
                        Timber.d("message token-->${data.token}")
                    }
                }
                is FirebaseCallResponse.Failure -> {
                    binding.constraintLayout.snackBar(it.throwable.message)
                }
                else -> {
                    binding.constraintLayout.snackBar(AppString.error_message)
                }
            }
        }



        viewModel.validationLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                fragmentActivity?.showLoader()
                viewModel.signIn()
            }
        }

        viewModel.observeSignInLive.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.Loading -> {
                    Timber.d("Observer Loading")
//                    fragmentActivity?.showLoader()
                }
                is ViewState.RenderFailure -> {
                    Timber.d("Observer Failure ")
                }
                is ViewState.RenderSuccess -> {
                    fragmentActivity?.hideLoader()
                    Timber.d("Observer Success :: ${viewState.output.fact}")
                }
            }
        }

    }

}