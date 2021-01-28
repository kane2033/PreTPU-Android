package ru.tpu.pretpu.core.base.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NavigationRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.presentation.BaseViewModel

/**
 * Базовый класс [Fragment],
 * имеющий общие для других фрагментов методы.
 * */
abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected abstract val viewModel: BaseViewModel

    // Отображение Toast уведомления со строкой из ресурсов
    internal fun makeToast(@StringRes message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // Отображение Toast уведомления с любой строкой
    internal fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Стандартный метод обработки ошибок, который хранит ошибки,
     * присующие каждому фрагменту, а также стандартное сообщение ошибки.
     * @param [handleFailure] - обработка дополнительных ошибок, присущих какому-то
     * конкретному фрагменту.
     * @param [handleRequestFailure] - обработка HTTP кодов ошибок в зависимости от кода.
     * Если функция не передана, выведется сообщение ошибки, пришедшее с сервиса.
     * */
    protected fun handleFailure(
        handleFailure: (failure: Failure) -> Unit = {},
        handleRequestFailure: (code: Int, message: String) -> Unit = { _, message ->
            makeToast(
                message
            )
        }
    ) {
        viewModel.failure.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { failure ->
                handleFailure.invoke(failure)
                when (failure) {
                    is Failure.NetworkConnection -> makeToast(R.string.network_connection_error)
                    is Failure.RequestFailure -> handleRequestFailure.invoke(
                        failure.code,
                        failure.message ?: getString(R.string.request_base_error)
                    )
                    else -> makeToast(R.string.base_error)
                }
            }
        })
    }

    protected fun navigateTo(@IdRes action: Int) {
        findNavController().navigate(action)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}