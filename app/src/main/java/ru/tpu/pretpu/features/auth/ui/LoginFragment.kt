package ru.tpu.pretpu.features.auth.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentLoginBinding
import ru.tpu.pretpu.databinding.FragmentLoginBindingImpl
import ru.tpu.pretpu.features.auth.presentation.AuthViewModel

@AndroidEntryPoint
class LoginFragment: BaseFragment(R.layout.fragment_login) {

    override val viewModel: AuthViewModel by navGraphViewModels(R.id.authGraph) {
        defaultViewModelProviderFactory
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isLoggedIn.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { loggedIn ->
                if (loggedIn) {
                    makeToast("ура залогинились")
                }
            }
        })

        handleFailure()
    }
}