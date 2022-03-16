package ru.fitsuli.doubletappapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.fitsuli.doubletappapp.Utils.Companion.HabitType
import ru.fitsuli.doubletappapp.Utils.Companion.Priority
import ru.fitsuli.doubletappapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listContent = mutableListOf<HabitItem>()

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var parentCard: MaterialCardView = view.findViewById(R.id.card_view)
        var name: TextView = view.findViewById(R.id.name)
        var description: TextView = view.findViewById(R.id.desc)
        var priority: TextView = view.findViewById(R.id.priority)
        var type: TextView = view.findViewById(R.id.type)
        var count: TextView = view.findViewById(R.id.count)
        var period: TextView = view.findViewById(R.id.period)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.contentInclude.recycler.adapter = object : RecyclerView.Adapter<ItemHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_habit, parent, false)
                )

            override fun getItemCount(): Int = listContent.size

            override fun onBindViewHolder(holder: ItemHolder, position: Int) {
                holder.apply {
                    parentCard.setOnClickListener {
                        startActivity(
                            Intent(this@MainActivity, AddHabitActivity::class.java).apply {
                                putExtra("edit_mode", true)
                                putExtra("item_id", position)
                                putExtra("habit_data", listContent[position])
                            }
                        )
                    }
                    listContent[position].srgbColor?.let {
                        parentCard.setCardBackgroundColor(it)
                    }
                    name.text = listContent[position].name
                    description.text = listContent[position].description.also {
                        description.isVisible = it.isNotEmpty()
                    }
                    priority.text = when (listContent[position].priorityPosition) {
                        Priority.High -> getString(R.string.high)
                        Priority.Medium -> getString(R.string.medium)
                        else -> getString(R.string.low)
                    }
                    type.text = when (listContent[position].type) {
                        HabitType.Good -> getString(R.string.good)
                        HabitType.Bad -> getString(R.string.bad)
                        else -> getString(R.string.neutral)
                    }
                    count.text =
                        listContent[position].count.also { count.isVisible = it.isNotEmpty() }

                    period.text = getString(
                        R.string.every_x,
                        listContent[position].period
                            .also { period.isVisible = it.isNotEmpty() })
                }
            }
        }


        binding.fab.setOnClickListener {
            startActivity(
                Intent(this, AddHabitActivity::class.java).apply {
                    putExtra("item_id", listContent.size)
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        intent?.getParcelableExtra<HabitItem>("new_item")?.let {
            listContent.add(it)
            binding.contentInclude.recycler.adapter?.notifyItemChanged(it.id)
        }
        intent?.getParcelableExtra<HabitItem>("edited_item")?.let {
            listContent[it.id] = it
            binding.contentInclude.recycler.adapter?.notifyItemChanged(it.id)
        }

        super.onNewIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            // R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}