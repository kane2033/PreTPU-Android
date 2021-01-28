package ru.tpu.pretpu.features.auth.ui

import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.features.auth.presentation.AuthViewModel

@AndroidEntryPoint
class RegisterFragment: BaseFragment(R.layout.fragment_register) {

    override val viewModel: BaseViewModel
        get() = TODO()

/*    override val viewModel: AuthViewModel by navGraphViewModels(R.id.authGraph) {
        defaultViewModelProviderFactory
    }*/
}