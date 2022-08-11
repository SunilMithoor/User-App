package com.app.domain.mapper

import com.app.data.local.sharedprefs.datasource.OnBoardingLocalDataSource
import com.app.domain.entity.response.SignInData
import com.app.domain.model.IOTaskResult
import com.app.domain.model.ViewState

fun saveUserData(
    ioTaskResult: IOTaskResult<SignInData>,
    onBoardingLocalDataSource: OnBoardingLocalDataSource
) {
    when (ioTaskResult) {
        is IOTaskResult.OnSuccess -> {
            val data = ViewState.RenderSuccess(ioTaskResult.data)
            onBoardingLocalDataSource.saveUserData(
                SignInData(
                    data.output.fact,
                    data.output.length
                )
            )
        }
        is IOTaskResult.OnFailed -> ViewState.RenderFailure(ioTaskResult.throwable)
    }
}