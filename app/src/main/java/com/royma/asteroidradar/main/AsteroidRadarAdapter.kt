package com.royma.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.royma.asteroidradar.Asteroid
import com.royma.asteroidradar.AsteroidItemViewHolder
import com.royma.asteroidradar.R

class AsteroidRadarAdapter: RecyclerView.Adapter<AsteroidItemViewHolder>(){

    var data = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.asteroid_item_view, parent, false) as TextView

        return AsteroidItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.codename
    }

}
