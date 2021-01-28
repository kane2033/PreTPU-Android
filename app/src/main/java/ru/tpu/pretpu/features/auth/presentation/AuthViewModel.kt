package ru.tpu.pretpu.features.auth.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.validation.exception.ValidationFailure
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.core.base.presentation.Event
import ru.tpu.pretpu.core.credentials.domain.entity.Credentials
import ru.tpu.pretpu.features.auth.domain.entity.LoginUser
import ru.tpu.pretpu.features.auth.domain.interactor.Login
import ru.tpu.pretpu.features.auth.domain.interactor.Register

class AuthViewModel
@ViewModelInject constructor(
    private val login: Login,
    private val register: Register
) : BaseViewModel() {

    private var _isLoggedIn = MutableLiveData<Event<Boolean>>()

    val isLoggedIn: LiveData<Event<Boolean>>
        get() = _isLoggedIn

    private var _isRegistered = MutableLiveData<Event<Boolean>>()

    val isRegistered: LiveData<Event<Boolean>>
        get() = _isRegistered

    val email = MutableLiveData("")

    val emailFailure = MutableLiveData<ValidationFailure>()

    val password = MutableLiveData("")

    val passwordFailure = MutableLiveData<ValidationFailure>()

    val rememberMe = MutableLiveData(false)

    //val formErrors = ObservableArrayMap<Field, ValidationFailure>()

    fun login() {
        // validate at first
        login.invoke(
            params = LoginUser(email.value!!, password.value!!, rememberMe.value!!),
            onResult = { result -> result.fold(::handleLoginFailure, ::handleLogin) },
            job = job
        )
        //this._failure.value = Event(LoginFailure)
    }

    private fun handleLogin(credentials: Credentials) {
        this._isLoggedIn.value = Event(true)
    }

    private fun handleLoginFailure(failure: Failure) {
        if (failure is ValidationFailure.FormFailures<*>) {
            emailFailure.value =
                failure.validationFailures.filterKeys {
                    LoginUser::email.name == it.name
                }.values.elementAtOrNull(0)
            passwordFailure.value =
                failure.validationFailures.filterKeys {
                    LoginUser::password.name == it.name
                }.values.elementAtOrNull(0)
        }
        else {
            _failure.value = Event(failure)
        }
    }

    fun register(name: String, password: String) {
/*        register.invoke(
            params = User(name, password),
            onResult = { it.either(::handleFailure, ::handleRegister) },
            job = job
        )*/
    }

    private fun handleRegister(nothing: Any) {
        _isRegistered.value = Event(true)
    }

/*    val loans = MutableLiveData<List<Loan>>()

    var selectedLoan = MutableLiveData<Loan>()

    private var _isLoggedIn = MutableLiveData<Event<Boolean>>()

    val isLoggedIn: LiveData<Event<Boolean>>
        get() = _isLoggedIn

    init {
        // Проверка на пройденность авторизации
        // при запуске приложения
        checkLoggedIn()
    }

    fun getLoansList() {
        getLoansList.invoke(
            params = None(),
            onResult = { it.either(::handleFailure, ::handleLoans) },
            job = job
        )
    }

    private fun handleLoans(loans: List<Loan>) {
        this.loans.value = loans
    }

    private fun checkLoggedIn() {
        userLoggedIn.invoke(
            params = None(),
            onResult = { it.either(::handleFailure, ::handleCheckLoggedIn) },
            job = job
        )
    }*/

/*    private fun handleCheckLoggedIn(isLoggedIn: Boolean) {
        this._isLoggedIn.value = Event(isLoggedIn)
    }*/
}