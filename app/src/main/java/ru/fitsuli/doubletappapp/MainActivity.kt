package ru.fitsuli.doubletappapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fitsuli.doubletappapp.Utils.Companion.HabitType
import ru.fitsuli.doubletappapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listContent = mutableListOf<HabitItem>()

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var parentCard: View = view.findViewById(R.id.card_view)
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
                        name.text = listContent[position].name
                        parentCard.setOnClickListener {
                            startActivity(
                                Intent(this@MainActivity, AddHabitActivity::class.java).apply {
                                    putExtra("edit_mode", true)
                                    putExtra("item_id", position)
                                    putExtra("habit_data", listContent[position])
                                }
                            )
                        }
                        description.text = listContent[position].description
                        priority.text = listContent[position].priority
                        type.text = when (listContent[position].type) {
                            HabitType.Good -> getString(R.string.good)
                            HabitType.Bad -> getString(R.string.bad)
                            else -> getString(R.string.neutral)
                        }
                        count.text = listContent[position].count
                        period.text = listContent[position].period
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
        intent?.getParcelableExtra<HabitItem>("edited_item")?.let { item ->
            listContent[item.id] = item
            binding.contentInclude.recycler.adapter?.notifyItemChanged(item.id)
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