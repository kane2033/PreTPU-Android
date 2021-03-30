package ru.tpu.pretpu.features.auth.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentLoginBinding
import ru.tpu.pretpu.features.auth.presentation.AuthViewModel
import ru.tpu.pretpu.features.auth.presentation.LoginViewModel

@AndroidEntryPoint
class LoginFragment: BaseFragment(R.layout.fragment_login) {

    private val parentViewModel: AuthViewModel by parentViewModels()

    override val viewModel: LoginViewModel by viewModels()

    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.isLoggedIn.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { loggedIn ->
                if (loggedIn) {
                    Navigation.findNavController(requireActivity(), R.id.main_nav_host_fragment)
                        .navigate(R.id.action_authNavHostFragment_to_newsNavHostFragment)
                }
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            parentViewModel.setIsLoadingValue(it)
        })

        handleFailure()
    }
}