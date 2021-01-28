package ru.tpu.pretpu.core.base.ui

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.di.entrypoint.LanguageChangeEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun attachBaseContext(newBase: Context) {
        // Инициализация репозитория юзера и настройщика конфига
        val entryPoint = EntryPointAccessors.fromApplication(
            newBase,
            LanguageChangeEntryPoint::class.java
        )
        val credentialRepository = entryPoint.repository
        val configChanger = entryPoint.configChanger

        // Если в репозитории нет языка (логаут, первый запуск), берем локаль устройства
        val languageCode = credentialRepository.languageName ?: Locale.getDefault().language
        // Смена языка в зависимости от локали, выбранной пользователем:
        val localeUpdatedContext: ContextWrapper = configChanger.updateLocale(newBase, languageCode)
        super.attachBaseContext(localeUpdatedContext)
    }


    // Метод перевода заголовка активити
/*    private fun resetTitle() {
        try {
            val label = packageManager.getActivityInfo(componentName, GET_META_DATA).labelRes
            if (label != 0) {
                setTitle(label);
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }*/

}