package ru.tpu.pretpu.features.profile.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.features.profile.presentation.NotificationsViewModel
import ru.tpu.pretpu.features.profile.presentation.ProfileViewModel

class NotificationsFragment : BaseFragment(R.layout.fragment_notifications) {

    private val parentViewModel: ProfileViewModel by parentViewModels()

    override val viewModel: NotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}