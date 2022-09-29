package com.royma.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.royma.asteroidradar.R
import com.royma.asteroidradar.TestAsteroid1
import com.royma.asteroidradar.TestAsteroid2
import com.royma.asteroidradar.TestAsteroid3

class AsteroidRadarAdapter: RecyclerView.Adapter<AsteroidRadarAdapter.ViewHolder>(){

//    var data = listOf<Asteroid>()
//    TESTING
    var data = listOf(TestAsteroid1, TestAsteroid2, TestAsteroid3)
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.asteroid_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.asteroidName.text = item.codename
        holder.approachDate.text = item.closeApproachData

        holder.dangerImage.setImageResource(
            when (item.isPotentiallyHazardous){
                true -> R.drawable.ic_status_potentially_hazardous
                false -> R.drawable.ic_status_normal
            }
        )
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val asteroidName: TextView = itemView.findViewById(R.id.asteroid_name_string)
        val approachDate: TextView = itemView.findViewById(R.id.approach_date_string)
        val dangerImage: ImageView = itemView.findViewById(R.id.potentially_dangerous_image)
    }
}
