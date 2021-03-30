package ru.tpu.pretpu.core.base.ui

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.di.entrypoint.LanguageChangeEntryPoint
import ru.tpu.pretpu.databinding.ActivityMainBinding
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main_nested) {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        binding.lifecycleOwner = this

        // Установка тулбара для активити
        // и кнопки "сендвича" для боковой шторки
        binding.apply {
            setSupportActionBar(appBar.toolbar)
            val toggle = ActionBarDrawerToggle(
                this@MainActivity, drawerLayout, appBar.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }*/


        // Настройка навигации через Toolbar и NavigationDrawer
        //val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        //setupToolbarAndDrawerNavigation(navController)

/*        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/


    }

/*    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer, menu)
        return true
    }*/

/*    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/

/*    override fun getNavigationView() = binding.navigationView.navView

    override fun getDrawerLayout() = binding.drawerLayout

    override fun getToolbar() = binding.appBar.toolbar

    override fun setViewModelBinding(viewModel: DrawerToolbarViewModel) {
        binding.viewModel = viewModel
    }

    override fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        binding.lifecycleOwner = lifecycleOwner
    }*/

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