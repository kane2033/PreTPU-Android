package ru.tpu.pretpu.features.news.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.tpu.pretpu.R

// Стартовый фрагмент необходим в любой графе,
// но для модуля news стартовый фрагмент зависит от результата запроса,
// поэтому LauncherFragment закрывается при запуске.
class LauncherFragment : Fragment(R.layout.fragment_launcher) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().popBackStack()
    }
}
