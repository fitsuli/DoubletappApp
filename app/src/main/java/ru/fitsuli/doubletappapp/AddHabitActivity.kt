package ru.fitsuli.doubletappapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.get
import androidx.core.view.WindowCompat
import androidx.core.view.doOnNextLayout
import ru.fitsuli.doubletappapp.Utils.Companion.HUE_COLORS
import ru.fitsuli.doubletappapp.Utils.Companion.HabitType
import ru.fitsuli.doubletappapp.Utils.Companion.dpToPx
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
            var itemRgb: Int? = null

            val isInEditMode = intent.getBooleanExtra("edit_mode", false)
            if (isInEditMode) {
                addButton.text = getString(R.string.change)
                intent.getParcelableExtra<HabitItem>("habit_data")?.let {
                    nameField.setText(it.name)
                    descriptionField.setText(it.description)
                    prioritySpinner.setSelection(it.priorityPosition)
                    typeGroup.check(
                        when (it.type) {
                            HabitType.Good -> R.id.radio_good
                            HabitType.Bad -> R.id.radio_bad
                            else -> R.id.radio_neutral
                        }
                    )
                    countField.setText(it.count)
                    periodField.setText(it.period)
                    it.srgbColor?.let { color ->
                        itemRgb = color
                        setSelectedColorInt(color)
                    }
                }
            }


            val wh = dpToPx(48f).toInt()
            val half = wh / 2
            val ids = mutableListOf<Int>()
            repeat(16) { _ ->
                val vId = View.generateViewId().also { ids += it }
                colorsLinear.addView(
                    ImageView(this@AddHabitActivity).apply {
                        setImageResource(R.drawable.rect)
                        id = vId
                        layoutParams = ViewGroup.LayoutParams(wh, wh)
                    }
                )
            }

            colorsLinear.background = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                HUE_COLORS
            ).apply {
                cornerRadius = dpToPx(4f)
            }

            colorsLinear.doOnNextLayout {
                // after gradient is constructed
                val gradient = colorsLinear.background.toBitmap(
                    colorsLinear.measuredWidth,
                    colorsLinear.measuredHeight
                )
                repeat(16) { i ->
                    findViewById<ImageView>(ids[i]).let {
                        val pixel =
                            gradient[it.x.toInt() + half, it.y.toInt() + half]

                        it.setImageDrawable(pixel.toDrawable())
                        it.setOnClickListener {
                            itemRgb = pixel
                            setSelectedColorInt(pixel)
                        }
                    }
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
                                priorityPosition = prioritySpinner.selectedItemPosition,
                                type = when (typeGroup.checkedRadioButtonId) {
                                    R.id.radio_good -> HabitType.Good
                                    R.id.radio_bad -> HabitType.Bad
                                    else -> HabitType.Neutral
                                },
                                count = countField.text.toString(),
                                period = periodField.text.toString(),
                                srgbColor = itemRgb,
                                id = intent.getIntExtra("item_id", 0)
                            )
                        )
                    }
                )
            }
        }
    }

    private fun setSelectedColorInt(@ColorInt pixel: Int) {
        binding.contentInclude.selectedColor.setImageDrawable(
            pixel.toDrawable()
        )
        val hsl = floatArrayOf(0f, 0f, 0f)
        ColorUtils.colorToHSL(
            pixel, hsl
        )

        binding.contentInclude.selectedColorText.text = getString(
            R.string.color_results, hsl[0], hsl[1], hsl[2],
            Color.red(pixel), Color.green(pixel), Color.blue(pixel)
        )
    }
}