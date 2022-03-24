package ru.fitsuli.doubletappapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import ru.fitsuli.doubletappapp.databinding.ActivityMainBinding
import ru.fitsuli.doubletappapp.fragments.AddHabitFragment
import ru.fitsuli.doubletappapp.fragments.RecyclerFragment

class MainActivity : AppCompatActivity(), AddHabitFragment.SendInfo {

    companion object {
        private const val saveList = "SAVE_HABITS"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    var listContent = mutableListOf<HabitItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawer)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(saveList, listContent.toCollection(ArrayList()))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        listContent =
            savedInstanceState.getParcelableArrayList<HabitItem>(saveList)?.toMutableList()
                ?: mutableListOf()
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()

    override fun addItemToList(item: HabitItem) {
        listContent.add(item)
        repeat(2) {
            (findVp2FragmentAtPosition(supportFragmentManager, it) as RecyclerFragment)
                .addNewItemToList(
                    listContent, item.id
                )
        }
    }

    override fun updateItemInList(item: HabitItem) {
        listContent[item.id] = item
        repeat(2) {
            (findVp2FragmentAtPosition(supportFragmentManager, it) as RecyclerFragment)
                .updateItemInList(
                    listContent, item.id
                )
        }
    }

}