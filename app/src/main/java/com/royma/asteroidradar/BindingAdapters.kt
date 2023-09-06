package com.royma.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.royma.asteroidradar.domain.Asteroid
import com.royma.asteroidradar.domain.PictureOfDay
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun ImageView.bindAsteroidStatusImage(item: Asteroid?) {
    item?.let {
        contentDescription = if (item.isPotentiallyHazardous) {
            setImageResource(R.drawable.ic_status_potentially_hazardous)
            "Potentially Dangerous"
        } else {
            setImageResource(R.drawable.ic_status_normal)
            "Not Dangerous"
        }
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = "Hazardous"
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = "Not Hazardous"
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("pictureOfDay")
fun ImageView.bindPictureOfDayImage(pictureOfDay: PictureOfDay?){
    pictureOfDay.let {
        val imgUri = pictureOfDay?.url?.toUri()
        Picasso.get()
            .load(imgUri)
            .placeholder(R.drawable.no_image_today_bw)
            .into(this)
    }
}
