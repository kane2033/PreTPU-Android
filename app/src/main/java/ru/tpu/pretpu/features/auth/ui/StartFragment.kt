package ru.tpu.pretpu.features.auth.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentStartBinding
import ru.tpu.pretpu.features.auth.presentation.AuthViewModel
import java.io.IOException

@AndroidEntryPoint
class StartFragment: BaseFragment(R.layout.fragment_start) {

    override val viewModel: AuthViewModel by navGraphViewModels(R.id.authGraph) {
        defaultViewModelProviderFactory
    }

    private lateinit var binding: FragmentStartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStartBinding.bind(view)
        binding.apply {
            // Включаем видео на заднем плане из ресурсов
            try {
                videoView.setRawData(R.raw.tpu480)
                videoView.prepare {
                    videoView.isLooping = true
                    videoView.start()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            gotoLogin.setOnClickListener {
                navigateTo(R.id.action_startFragment_to_loginFragment)
            }
            gotoRegister.setOnClickListener {
                navigateTo(R.id.action_startFragment_to_registerFragment)
            }
        }
    }
}