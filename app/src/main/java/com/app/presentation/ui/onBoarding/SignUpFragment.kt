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
import com.app.databinding.FragmentSignUpBinding
import com.app.domain.entity.request.SignUpRequest
import com.app.domain.model.ViewState
import com.app.presentation.extension.AppLayout
import com.app.presentation.extension.click
import com.app.presentation.extension.hideKeyboard
import com.app.presentation.vm.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignUpFragment : BaseFragment(AppLayout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<OnBoardingViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = _binding ?: FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(view: View) {
        initialize()
    }


    private fun initialize() {
//        getFirebaseDeviceId()
//        getFirebaseMessageToken()
        binding.btnSubmit.click {
            hideKeyboard()
            validateSignUp()
        }

        binding.linSignIn.click {
            finish()
        }

    }

//    private fun getFirebaseDeviceId() {
//        fragmentActivity?.showLoader()
//        viewModel.getDeviceId()
//    }

//    private fun getFirebaseMessageToken() {
//        fragmentActivity?.showLoader()
//        viewModel.getMessageToken()
//    }

    private fun validateSignUp() {
        val signUpRequest = SignUpRequest(
            binding.etUsername.text.toString().trim(),
            binding.etPassword.text.toString().trim(),
            binding.etConfirmPassword.text.toString().trim()
        )
        viewModel.validateSignUpData(signUpRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun observeLiveData() {

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, getString(it), Toast.LENGTH_LONG).show()
        }

//        viewModel.firebaseDeviceIdResponse.observe(viewLifecycleOwner) {
//            fragmentActivity?.hideLoader()
//            Timber.d("FirebaseDeviceidResponse-->${it}")
//            when (it) {
//                is FirebaseCallResponse.Success -> {
//                    it.data.let { data ->
//                        Timber.d("device id-->${data.deviceId}")
//                    }
//                }
//                is FirebaseCallResponse.Failure -> {
//                    binding.constraintLayout.snackBar(it.throwable.message)
//                }
//                else -> {
//                    binding.constraintLayout.snackBar(AppString.error_message)
//                }
//            }
//        }

//        viewModel.firebaseMessageTokenResponse.observe(viewLifecycleOwner) {
//            fragmentActivity?.hideLoader()
//            Timber.d("FirebaseTokenResponse-->${it}")
//            when (it) {
//                is FirebaseCallResponse.Success -> {
//                    it.data.let { data ->
//                        Timber.d("message token-->${data.token}")
//                    }
//                }
//                is FirebaseCallResponse.Failure -> {
//                    binding.constraintLayout.snackBar(it.throwable.message)
//                }
//                else -> {
//                    binding.constraintLayout.snackBar(AppString.error_message)
//                }
//            }
//        }


        viewModel.validationLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                fragmentActivity?.showLoader()
                viewModel.signUp()
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