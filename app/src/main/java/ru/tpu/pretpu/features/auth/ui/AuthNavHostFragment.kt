package ru.tpu.pretpu.features.auth.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentHostAuthBinding
import ru.tpu.pretpu.features.auth.presentation.AuthViewModel

@AndroidEntryPoint
class AuthNavHostFragment : BaseFragment(R.layout.fragment_host_auth) {

    override val viewModel: AuthViewModel by viewModels()

    private val binding: FragmentHostAuthBinding by viewBinding(FragmentHostAuthBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}