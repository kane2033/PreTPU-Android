package ru.tpu.pretpu.features.auth.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentStartBinding
import ru.tpu.pretpu.features.auth.presentation.StartViewModel
import java.io.IOException

@AndroidEntryPoint
class StartFragment : BaseFragment(R.layout.fragment_start) {

    override val viewModel: StartViewModel by viewModels()

    private val binding: FragmentStartBinding by viewBinding(FragmentStartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            val navController = findNavController()
            gotoLogin.setOnClickListener {
                navController.navigate(R.id.action_startFragment_to_loginFragment)
            }
            gotoRegister.setOnClickListener {
                navController.navigate(R.id.action_startFragment_to_registerFragment)
            }

            viewModel.isAlreadyLoggedIn.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let {
                    if (it) {
                        //navigateTo(R.id.action_startFragment_to_newsGraph)
                        Navigation.findNavController(requireActivity(), R.id.main_nav_host_fragment)
                            .navigate(R.id.action_authNavHostFragment_to_newsNavHostFragment)
                    }
                }
            })
        }
    }
}