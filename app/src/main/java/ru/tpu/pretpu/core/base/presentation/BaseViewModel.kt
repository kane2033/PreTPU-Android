package ru.tpu.pretpu.core.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.tpu.pretpu.core.base.domain.exception.Failure
import kotlinx.coroutines.Job

/**
 * Базовый класс [ViewModel], хранящий элементы,
 * используемые в каждой viewmodel.
 * */
abstract class BaseViewModel : ViewModel() {

    // Работа, в скоупе которой будет выполняться
    // задача
    protected val job = Job()

    // Упаковка ошибки в event, чтобы
    // отображение ошибки возникало только один раз
    protected val _failure = MutableLiveData<Event<Failure>>()

    val failure: LiveData<Event<Failure>>
        get() = _failure

    // Стандартный метод обработки ошибки, упаковывающий ее
    // в event
    protected fun handleFailure(failure: Failure) {
        this._failure.value = Event(failure)
    }

    // Отменяем работу, когда viewmodel
    // прекратила жизненный цикл
    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}