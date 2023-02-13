package com.example.tvmazeclient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvmazeclient.data.db.LocalShow
import com.example.tvmazeclient.data.model.QueryResponse.ResponseItem
import com.example.tvmazeclient.databinding.ItemShowBinding

class LocalAdapter: RecyclerView.Adapter<LocalAdapter.QueryViewHolder>() {
    private var onItemClickListener :((LocalShow)->Unit)? = null

    /**
     * objeto que facilita la actualización de los elementos del
     * recyclerview (actualiza solo los elementos que cambian)
     */
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<LocalShow>(){
        override fun areItemsTheSame(oldItem: LocalShow, newItem: LocalShow): Boolean {
            return oldItem.showId == newItem.showId
        }

        override fun areContentsTheSame(oldItem: LocalShow, newItem: LocalShow): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder {
        val binding = ItemShowBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return QueryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {
        val show = differ.currentList[position]
        holder.bind(show)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class QueryViewHolder(private val binding: ItemShowBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(item: LocalShow){

                    binding.txtName.text = item.name
                    binding.txtNetworkName.text = item.network

                    binding.txtAirDateTime.text = "${item.time} | ${item.days}"

                    item.image?.let {
                        Glide.with(binding.ivShowImage.context)
                            .load(it)
                            .into(binding.ivShowImage)
                    }

                    binding.root.setOnClickListener {
                        onItemClickListener?.let {
                            it(item)
                        }
                    }
                }
            }

    fun setOnItemClickListener(listener : (LocalShow)->Unit){
        onItemClickListener = listener
    }
}

