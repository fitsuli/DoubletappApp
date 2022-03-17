package ru.fitsuli.doubletappapp.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.get
import androidx.core.os.bundleOf
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import ru.fitsuli.doubletappapp.HabitItem
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.Utils.Companion.EDITED_ITEM_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.EDIT_MODE_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.FRAGMENT_REQUEST_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.HABIT_ITEM_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.ITEM_ID_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.NEW_ITEM_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.Priority
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.Utils.Companion.dpToPx
import ru.fitsuli.doubletappapp.databinding.FragmentAddHabitBinding

class AddHabitFragment : Fragment(R.layout.fragment_add_habit) {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddHabitBinding.bind(view)

        val ctx = requireContext()
        with(binding) {
            var itemRgb: Int? = null

            val isInEditMode = arguments?.getBoolean(EDIT_MODE_KEY, false) == true
            if (isInEditMode) {
                addButton.text = getString(R.string.change)
                arguments?.getParcelable<HabitItem>(HABIT_ITEM_KEY)?.let {
                    nameField.setText(it.name)
                    descriptionField.setText(it.description)
                    prioritySpinner.setSelection(it.priority.ordinal)
                    typeGroup.check(
                        when (it.type) {
                            Type.GOOD -> R.id.radio_good
                            Type.BAD -> R.id.radio_bad
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


            val wh = ctx.dpToPx(48f).toInt()
            val half = wh / 2
            val ids = mutableListOf<Int>()
            repeat(16) { _ ->
                val vId = View.generateViewId().also { ids += it }
                colorsLinear.addView(
                    ImageView(ctx).apply {
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
                cornerRadius = requireContext().dpToPx(4f)
            }

            colorsLinear.doOnNextLayout {
                // after gradient is constructed
                val gradient = colorsLinear.background.toBitmap(
                    colorsLinear.measuredWidth,
                    colorsLinear.measuredHeight
                )
                repeat(16) { i ->
                    view.findViewById<ImageView>(ids[i]).let {
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
                        requireContext(),
                        getString(R.string.enter_name_hint),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                setFragmentResult(
                    FRAGMENT_REQUEST_KEY,
                    bundleOf(
                        (if (isInEditMode) EDITED_ITEM_KEY else NEW_ITEM_KEY) to HabitItem(
                            name = nameField.text.toString(),
                            description = descriptionField.text.toString(),
                            priority = Priority.values()[prioritySpinner.selectedItemPosition],
                            type = when (typeGroup.checkedRadioButtonId) {
                                R.id.radio_good -> Type.GOOD
                                R.id.radio_bad -> Type.BAD
                                else -> Type.NEUTRAL
                            },
                            count = countField.text.toString(),
                            period = periodField.text.toString(),
                            srgbColor = itemRgb,
                            id = arguments?.getInt(ITEM_ID_KEY, 0) ?: 0
                        )
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun setSelectedColorInt(@ColorInt pixel: Int) {
        binding.selectedColor.setImageDrawable(
            pixel.toDrawable()
        )
        val hsl = floatArrayOf(0f, 0f, 0f)
        ColorUtils.colorToHSL(
            pixel, hsl
        )

        binding.selectedColorText.text = getString(
            R.string.color_results, hsl[0], hsl[1], hsl[2],
            Color.red(pixel), Color.green(pixel), Color.blue(pixel)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val HUE_COLORS = intArrayOf(
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.BLUE,
            Color.MAGENTA,
            Color.RED
        )
    }
}