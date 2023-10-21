package com.example.challenge3binar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge3binar.databinding.ListMenuLinearBinding
import com.example.challenge3binar.databinding.ListMenuGridBinding

class Adapaters(private val listMenu: ArrayList<DataMenu>, val isGrid:Boolean)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var onItemClick : ((DataMenu) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (isGrid) {
            val binding =
                ListMenuGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GridMenuHolder(binding)
        } else {
            val binding =
                ListMenuLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LinearMenuHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isGrid) {
            val gridHolder = holder as GridMenuHolder
            gridHolder.onBind(listMenu[position])
        } else {
            val linearHolder = holder as LinearMenuHolder
            linearHolder.onBind(listMenu[position])
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listMenu[position])
        }

    }

    override fun getItemCount(): Int {
        return listMenu.size
    }
}

class GridMenuHolder(val binding: ListMenuGridBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: DataMenu) {
        binding.ivGambar.setImageResource(data.img)
        binding.tvNamaMenu.text = data.nameMenu
        binding.tvHargaMenu.text = data.hargaMenu
    }
}

class LinearMenuHolder(val binding: ListMenuLinearBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: DataMenu) {
        binding.ivGambar.setImageResource(data.img)
        binding.tvNamaMenu.text = data.nameMenu
        binding.tvHargaMenu.text = data.hargaMenu
    }
}


