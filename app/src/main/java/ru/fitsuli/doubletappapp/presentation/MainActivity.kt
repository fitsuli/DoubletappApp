package ru.fitsuli.doubletappapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import coil.ImageLoader
import coil.load
import coil.size.ViewSizeResolver
import com.google.android.material.imageview.ShapeableImageView
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.data.storage.network.OkHttpSingleton
import ru.fitsuli.doubletappapp.databinding.ActivityMainBinding
import ru.fitsuli.doubletappapp.presentation.viewmodels.ListViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawer)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    navController.navigate(R.id.MainFragment)
                    true
                }
                R.id.action_about -> {
                    navController.navigate(R.id.AboutAppHabit)
                    true
                }
                else -> false
            }.also { binding.drawer.close() }
        }

        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient(OkHttpSingleton.getInstance())
            .crossfade(true)
            .build()

        val avatar = binding.navView.getHeaderView(0).findViewById<ShapeableImageView>(R.id.avatar)

        avatar.load(
            "https://sushishef1.ru/upload/shop_3/4/1/5/item_415/item_415.png",
            imageLoader = imageLoader
        ) {
            size(ViewSizeResolver(avatar))
            placeholder(R.drawable.ic_round_downloading_24)
            error(R.drawable.ic_round_error_outline_24)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_secondary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        when (item.itemId) {
            R.id.action_update -> {
                viewModel.updateHabitsFromNet { reason ->
                    shortToast(reason.hintStringResId)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
}