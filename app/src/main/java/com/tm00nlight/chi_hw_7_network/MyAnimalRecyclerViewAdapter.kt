package com.tm00nlight.chi_hw_7_network

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

import com.tm00nlight.chi_hw_7_network.data.Animal
import com.tm00nlight.chi_hw_7_network.databinding.FragmentAnimalBinding

class MyAnimalRecyclerViewAdapter(
    private val values: List<Animal>
) : RecyclerView.Adapter<MyAnimalRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentAnimalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        holder.altName.text = item.latin_name
        holder.type.text = item.animal_type
        Picasso.get().load(item.image_link).into(holder.img)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentAnimalBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.name
        val altName: TextView = binding.altName
        val type: TextView = binding.type
        val img: ImageView = binding.image

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

}