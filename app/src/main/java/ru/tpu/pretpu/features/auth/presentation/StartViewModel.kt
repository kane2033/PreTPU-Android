package ru.tpu.pretpu.features.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tpu.pretpu.core.base.domain.interactor.None
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.core.base.presentation.Event
import ru.tpu.pretpu.core.credentials.domain.interactor.UserLoggedIn
import javax.inject.Inject

@HiltViewModel
class StartViewModel
@Inject constructor(private val userLoggedIn: UserLoggedIn) : BaseViewModel() {

    private val _isAlreadyLoggedIn = MutableLiveData<Event<Boolean>>()
    val isAlreadyLoggedIn: LiveData<Event<Boolean>>
        get() = _isAlreadyLoggedIn

    init {
        userLoggedIn(
            params = None(),
            job = job,
            onResult = { result -> result.fold({}, ::handleAlreadyLoggedIn) }
        )
    }

    private fun handleAlreadyLoggedIn(isLoggedIn: Boolean) {
        this._isAlreadyLoggedIn.value = Event(isLoggedIn)
    }
}