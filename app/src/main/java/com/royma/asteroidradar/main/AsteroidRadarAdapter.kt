package com.royma.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.royma.asteroidradar.Asteroid
import com.royma.asteroidradar.R

class AsteroidRadarAdapter: ListAdapter<Asteroid, AsteroidRadarAdapter.ViewHolder>(AsteroidDiffCallback()){

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.asteroidName.text = item.codename
        holder.approachDate.text = item.closeApproachData

        holder.bind(item)
    }


    class ViewHolder private constructor (itemView: View): RecyclerView.ViewHolder(itemView){
        val asteroidName: TextView = itemView.findViewById(R.id.asteroid_name_string)
        val approachDate: TextView = itemView.findViewById(R.id.approach_date_string)
        val dangerImage: ImageView = itemView.findViewById(R.id.potentially_dangerous_image)

        fun bind(item: Asteroid) {
            dangerImage.setImageResource(
                when (item.isPotentiallyHazardous) {
                    true -> R.drawable.ic_status_potentially_hazardous
                    false -> R.drawable.ic_status_normal
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.asteroid_item_view, parent, false)

                return ViewHolder(view)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by [ListAdapter] to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class AsteroidDiffCallback: DiffUtil.ItemCallback<Asteroid>(){

    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.asteroidId == newItem.asteroidId
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}
