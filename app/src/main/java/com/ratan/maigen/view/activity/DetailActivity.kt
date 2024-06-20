package com.ratan.maigen.view.activity

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.ratan.maigen.R
import com.ratan.maigen.additional.Destination
import java.lang.reflect.Member

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSON = "key_destination"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataDestination = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Destination>(EXTRA_PERSON, Destination::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PERSON)
        }

        val tvDetailName = findViewById<TextView>(R.id.tv_detail_name)
        val tvDetailDescription = findViewById<TextView>(R.id.tv_detail_description)
        val ivDetailPhoto = findViewById<ImageView>(R.id.iv_detail_photo)

        if (dataDestination != null) {
            tvDetailName.text = dataDestination.name
            tvDetailDescription.text = dataDestination.description
            Glide.with(this)
                .load(dataDestination.photo)
                .placeholder(R.drawable.air_terjun_aling_aling)
                .into(ivDetailPhoto)
        }
    }
}