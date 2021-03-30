package ru.tpu.pretpu.features.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.validation.exception.ValidationFailure
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.core.base.presentation.Event
import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.features.auth.domain.entity.LoginUser
import ru.tpu.pretpu.features.auth.domain.interactor.Login
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(private val login: Login) : BaseViewModel() {

    val email = MutableLiveData("")
    val emailFailure = MutableLiveData<ValidationFailure>()

    val password = MutableLiveData("")
    val passwordFailure = MutableLiveData<ValidationFailure>()

    val rememberMe = MutableLiveData(false)

    private val _isLoggedIn = MutableLiveData<Event<Boolean>>()
    val isLoggedIn: LiveData<Event<Boolean>>
        get() = _isLoggedIn

    fun login() {
        _isLoading.value = true
        login.invoke(
            params = LoginUser(email.value!!, password.value!!, rememberMe.value!!),
            onResult = { result -> result.fold(::handleLoginFailure, ::handleLogin) },
            job = job
        )
    }

    private fun handleLogin(credentials: Credentials) {
        _isLoading.value = false
        this._isLoggedIn.value = Event(true)
    }

    private fun handleLoginFailure(failure: Failure) {
        _isLoading.value = false
        if (failure is ValidationFailure.FormFailures<*>) {
            emailFailure.value =
                failure.validationFailures.filterKeys {
                    LoginUser::email.name == it.name
                }.values.elementAtOrNull(0)
            passwordFailure.value =
                failure.validationFailures.filterKeys {
                    LoginUser::password.name == it.name
                }.values.elementAtOrNull(0)
        } else {
            _failure.value = Event(failure)
        }
    }
}