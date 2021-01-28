package ru.tpu.pretpu.core.base.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.domain.validation.exception.ValidationFailure

object BindingAdapters {

    @JvmStatic
    @BindingAdapter(value = ["validationFailure", "errorText"], requireAll = false)
    fun setErrorMessage(view: TextInputEditText, failure: ValidationFailure?, errorText: String?) {
        failure?.let { view.error = errorText ?: view.context.getString(R.string.validation_error)}
    }
}