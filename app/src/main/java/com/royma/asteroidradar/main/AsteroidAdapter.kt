package com.royma.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.royma.asteroidradar.Asteroid
import com.royma.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter(val clickListener: AsteroidListener): ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()){

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

        holder.binding.asteroidNameString.text = item.codename
        holder.binding.approachDateString.text = item.closeApproachData
        holder.bind(clickListener, item)
    }


    class ViewHolder private constructor (val binding: ListItemAsteroidBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: AsteroidListener, item: Asteroid) {
            binding.asteroid = item
            binding.clickListener = clickListener
            // Good idea to always execute pending bindings when using BAs in the RecyclerView
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
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

class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit){
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}
