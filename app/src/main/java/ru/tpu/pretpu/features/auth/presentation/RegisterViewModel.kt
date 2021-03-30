package ru.tpu.pretpu.features.auth.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.features.auth.domain.interactor.Register
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(private val register: Register) : BaseViewModel() {


}