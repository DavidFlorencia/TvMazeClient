package com.example.tvmazeclient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvmazeclient.data.model.CastResponse.Person
import com.example.tvmazeclient.databinding.ItemPersonBinding

class PersonAdapter: RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    /**
     * objeto que facilita la actualización de los elementos del
     * recyclerview (actualiza solo los elementos que cambian)
     */
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Person>(){
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.data.name == newItem.data.name
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val show = differ.currentList[position]
        holder.bind(show)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class PersonViewHolder(private val binding: ItemPersonBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(item: Person){
                    binding.tvName.text = item.data.name
                    Glide.with(binding.ivImage.context)
                        .load(item.data.image.medium)
                        .into(binding.ivImage)
                }
            }
}

