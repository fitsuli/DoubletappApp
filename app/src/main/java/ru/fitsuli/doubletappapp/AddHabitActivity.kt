package ru.fitsuli.doubletappapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import ru.fitsuli.doubletappapp.Utils.Companion.HabitType
import ru.fitsuli.doubletappapp.databinding.ActivityAddHabitBinding

class AddHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        with(binding.contentInclude) {
            val isInEditMode = intent.getBooleanExtra("edit_mode", false)
            if (isInEditMode) {
                intent.getParcelableExtra<HabitItem>("habit_data")?.let {
                    nameField.setText(it.name)
                    descriptionField.setText(it.description)
                    prioritySpinner.setSelection(it.priority.toInt()) // dirty
                    typeGroup.check(
                        when (it.type) {
                            HabitType.Good -> R.id.radio_good
                            HabitType.Bad -> R.id.radio_bad
                            else -> R.id.radio_neutral
                        }
                    )
                    countField.setText(it.count)
                    periodField.setText(it.period)
                }
            }

            addButton.setOnClickListener {
                if (nameField.text.isNullOrEmpty()) {
                    Toast.makeText(
                        this@AddHabitActivity,
                        getString(R.string.enter_name_hint),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                startActivity(
                    Intent(this@AddHabitActivity, MainActivity::class.java).apply {
                        putExtra(
                            if (isInEditMode) "edited_item" else "new_item", HabitItem(
                                name = nameField.text.toString(),
                                description = descriptionField.text.toString(),
                                priority = "${prioritySpinner.selectedItemPosition}",
                                type = when (typeGroup.checkedRadioButtonId) {
                                    R.id.radio_good -> HabitType.Good
                                    R.id.radio_bad -> HabitType.Bad
                                    else -> HabitType.Neutral
                                },
                                count = countField.text.toString(),
                                period = periodField.text.toString(),
                                id = intent.getIntExtra("item_id", 0)
                            )
                        )
                    }
                )
            }
        }
    }
}