package com.tm00nlight.chi_hw_7_network

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.tm00nlight.chi_hw_7_network.data.Marvel
import com.tm00nlight.chi_hw_7_network.databinding.FragmentMarvelBinding

class MyMarvelRecyclerViewAdapter(
    private val values: List<Marvel>
) : RecyclerView.Adapter<MyMarvelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMarvelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        holder.altName.text = item.realname
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMarvelBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.name
        val altName: TextView = binding.altName
        val img: ImageView = binding.image

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

}