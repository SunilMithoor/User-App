package com.app.presentation.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.base.BaseViewModel
import com.app.domain.entity.request.SignInRequest
import com.app.domain.entity.request.SignUpRequest
import com.app.domain.entity.response.FireBaseDeviceId
import com.app.domain.entity.response.FireBaseMessageToken
import com.app.domain.entity.response.SignInData
import com.app.domain.entity.response.SignUpData
import com.app.domain.extension.isEmail
import com.app.domain.model.FirebaseCallResponse
import com.app.domain.model.ViewState
import com.app.domain.usecase.*
import com.app.domain.util.getViewStateFlowForAll
import com.app.domain.util.getViewStateFlowForNetworkCall
import com.app.presentation.extension.AppString
import com.app.presentation.util.CodeSnippet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    application: Application,
    private val isUserFirstTimeLaunchUseCase: IsUserFirstTimeLaunchUseCase,
    private val setUserFirstTimeLaunchUseCase: SetUserFirstTimeLaunchUseCase,
    private val signInUseCase: SignInUseCase,
    private val deviceIdUseCase: DeviceIdUseCase,
    private val messageTokenUseCase: MessageTokenUseCase,
    private val codeSnippet: CodeSnippet
) : BaseViewModel(application) {


    val observeSignInLive: MutableLiveData<ViewState<SignInData>> by lazy {
        MutableLiveData<ViewState<SignInData>>()
    }

    val observeSignUpLive: MutableLiveData<ViewState<SignUpData>> by lazy {
        MutableLiveData<ViewState<SignUpData>>()
    }

    val observeIsUserFirstTimeLaunchLive: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val observeSetUserFirstTimeLaunchLive: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


    private val _firebaseDeviceIdResponse =
        MutableLiveData<FirebaseCallResponse<FireBaseDeviceId>?>()
    val firebaseDeviceIdResponse get() = _firebaseDeviceIdResponse


    private val _firebaseMessageTokenResponse =
        MutableLiveData<FirebaseCallResponse<FireBaseMessageToken>?>()
    val firebaseMessageTokenResponse get() = _firebaseMessageTokenResponse


    @ExperimentalCoroutinesApi
    fun getFirstTimeUser() {
        viewModelScope.launch {
            getViewStateFlowForAll {
                isUserFirstTimeLaunchUseCase.execute(None)
            }.collect {
                populateLaunchData(it)
            }
        }
    }


    @ExperimentalCoroutinesApi
    fun setFirstTimeUser(value: Boolean) {
        viewModelScope.launch {
            getViewStateFlowForAll {
                setUserFirstTimeLaunchUseCase.execute(value)
            }.collect {
                getLaunchData(it)
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun signIn() {
        viewModelScope.launch {
            getViewStateFlowForNetworkCall {
                signInUseCase.execute(None)
            }.collect {
                populateData(it)
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun signUp() {
        viewModelScope.launch {
            getViewStateFlowForNetworkCall {
                signInUseCase.execute(None)
            }.collect {
                populateData(it)
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun getDeviceId() {
        viewModelScope.launch {
            _firebaseDeviceIdResponse.postValue(deviceIdUseCase.execute(None))
        }
    }

    @ExperimentalCoroutinesApi
    fun getMessageToken() {
        viewModelScope.launch {
            firebaseMessageTokenResponse.postValue(messageTokenUseCase.execute(None))
        }
    }


//    fun validateEmailMobile(): Boolean {
//        return if (email.isNullOrEmpty()) {
//            errorMessageLiveData.value = AppString.error_email_mobile_empty
//            false
//        } else if (codeSnippet.isValidMail(email) || codeSnippet.isValidMobile(email)) {
//            true
//        } else {
//            errorMessageLiveData.value = AppString.error_email_mobile_empty
//            false
//        }
//    }

//    fun validatePassword(): Boolean {
//        return when {
//            password.isNullOrEmpty() -> {
//                errorMessageLiveData.value = AppString.error_password_empty
//                false
//            }
//            password?.trim()?.length!! < 6 -> {
//                errorMessageLiveData.value = AppString.error_password_length
//                false
//            }
//            else -> {
//                true
//            }
//        }
//    }


    fun validateSignInData(signInRequest: SignInRequest?): Boolean {
        val email = signInRequest?.email
        val password = signInRequest?.password
        return when {
            email?.trim().isNullOrEmpty() -> {
                errorMessageLiveData.value = AppString.error_email_mobile_empty
                validationLiveData.value = false
                false
            }
            email?.isEmail() == false -> {
                errorMessageLiveData.value = AppString.error_valid_email_mobile
                validationLiveData.value = false
                false
            }
            password?.trim().isNullOrEmpty() -> {
                errorMessageLiveData.value = AppString.error_password_empty
                validationLiveData.value = false
                false
            }
            password?.trim()?.length!! < 6 -> {
                errorMessageLiveData.value = AppString.error_password_length
                validationLiveData.value = false
                false
            }
            else -> {
                validationLiveData.value = true
                true
            }
        }
    }

    fun validateSignUpData(signUpRequest: SignUpRequest?): Boolean {
        val email = signUpRequest?.email
        val password = signUpRequest?.password
        val confirmPassword = signUpRequest?.confirmPassword
        return when {
            email?.trim().isNullOrEmpty() -> {
                errorMessageLiveData.value = AppString.error_email_mobile_empty
                validationLiveData.value = false
                false
            }
            email?.isEmail() == false -> {
                errorMessageLiveData.value = AppString.error_valid_email_mobile
                validationLiveData.value = false
                false
            }
            password?.trim().isNullOrEmpty() -> {
                errorMessageLiveData.value = AppString.error_password_empty
                validationLiveData.value = false
                false
            }
            password?.trim()?.length!! < 6 -> {
                errorMessageLiveData.value = AppString.error_password_length
                validationLiveData.value = false
                false
            }
            else -> {
                validationLiveData.value = true
                true
            }
        }
    }

    private fun populateLaunchData(any: Any) {
        if (any is Boolean) {
            observeIsUserFirstTimeLaunchLive.value = any.toString().toBoolean()
        }
    }

    private fun getLaunchData(any: Any) {
        if (any is Boolean) {
            observeSetUserFirstTimeLaunchLive.value = any.toString().toBoolean()
        }
    }


    private fun populateData(signInDataState: ViewState<SignInData>) {
        when (signInDataState) {
            is ViewState.Loading -> {
                Timber.d("Loading")
            }
            is ViewState.RenderFailure -> {
                Timber.d("Failure ${signInDataState.throwable.message}")
            }
            is ViewState.RenderSuccess<SignInData> -> {
                observeSignInLive.value = signInDataState
            }
        }
    }



}
