package ru.fitsuli.doubletappapp.presentation.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.get
import androidx.core.view.doOnNextLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.databinding.FragmentAddHabitBinding
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.Priority
import ru.fitsuli.doubletappapp.domain.models.Type
import ru.fitsuli.doubletappapp.presentation.Utils.Companion.EDIT_MODE_KEY
import ru.fitsuli.doubletappapp.presentation.Utils.Companion.ITEM_ID_KEY
import ru.fitsuli.doubletappapp.presentation.dpToPx
import ru.fitsuli.doubletappapp.presentation.shortToast
import ru.fitsuli.doubletappapp.presentation.toIntOrZero
import ru.fitsuli.doubletappapp.presentation.viewmodels.AddHabitViewModel
import java.time.OffsetDateTime
import java.util.*

class AddHabitFragment : Fragment(R.layout.fragment_add_habit) {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!
    private var itemRgb: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddHabitBinding.bind(view)
        val viewModel = ViewModelProvider(requireActivity())[AddHabitViewModel::class.java]

        val ctx = requireContext()
        with(binding) {
            var prevHabit: HabitItem? = null

            val isInEditMode = arguments?.getBoolean(EDIT_MODE_KEY, false) == true
            if (isInEditMode) {
                addButton.text = getString(R.string.change)
                arguments?.getString(ITEM_ID_KEY, "0aaa")?.let { id ->
                    viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
                        if (item == null) return@observe
                        prevHabit = item
                        removeButton.isVisible = true

                        restoreFromItem(item)
                    }

                    viewModel.runFindItemById(id)
                }
            } else {
                nameField.requestFocus()
            }


            val wh = ctx.dpToPx(48f).toInt()
            val half = wh / 2
            val ids = buildList(capacity = 16) {
                repeat(16) { _ ->
                    val vId = View.generateViewId().also { add(it) }
                    colorsLinear.addView(
                        ImageView(ctx).apply {
                            setImageResource(R.drawable.rect)
                            id = vId
                            layoutParams = ViewGroup.LayoutParams(wh, wh)
                        }
                    )
                }
            }

            colorsLinear.background = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                HUE_COLORS
            ).apply {
                cornerRadius = ctx.dpToPx(4f)
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
                    ctx.shortToast(getString(R.string.enter_name_hint))
                    return@setOnClickListener
                }
                if (descriptionField.text.isNullOrEmpty()) {
                    ctx.shortToast(getString(R.string.enter_desc_hint))
                    return@setOnClickListener
                }

                val habit = HabitItem(
                    name = nameField.text.toString(),
                    description = descriptionField.text.toString(),
                    priority = Priority.values()
                        .getOrElse(prioritySpinner.selectedItemPosition) { Priority.HIGH },
                    type = when (typeGroup.checkedRadioButtonId) {
                        R.id.radio_good -> Type.GOOD
                        else -> Type.BAD
                    },
                    count = countField.text.toString().toIntOrZero(),
                    period = periodField.text.toString().toIntOrZero(),
                    srgbColor = itemRgb,
                    modifiedDate = OffsetDateTime.now(),
                    id = UUID.randomUUID().mostSignificantBits.toString().let { uuid ->
                        arguments?.getString(ITEM_ID_KEY, uuid) ?: uuid
                    }
                )

                if (prevHabit == habit) {
                    ctx.shortToast(getString(R.string.no_changes_made))

                    findNavController().popBackStack()
                    return@setOnClickListener
                }

                if (isInEditMode) viewModel.update(habit)
                else viewModel.add(habit)

                findNavController().popBackStack()
            }

            removeButton.setOnClickListener {
                if (prevHabit != null) {
                    viewModel.delete(prevHabit!!)
                }
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

    private fun restoreFromItem(item: HabitItem) = with(binding) {
        nameField.setText(item.name)
        descriptionField.setText(item.description)
        prioritySpinner.setSelection(item.priority.ordinal)
        typeGroup.check(
            when (item.type) {
                Type.GOOD -> R.id.radio_good
                else -> R.id.radio_bad
            }
        )
        countField.setText(item.count.toString())
        periodField.setText(item.period.toString())
        item.srgbColor?.let { color ->
            itemRgb = color
            setSelectedColorInt(color)
        }
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