package com.example.tvmazeclient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvmazeclient.data.model.QueryResponse.ResponseItem
import com.example.tvmazeclient.databinding.ItemShowBinding

class QueryAdapter: RecyclerView.Adapter<QueryAdapter.QueryViewHolder>() {
    private var onItemClickListener :((ResponseItem)->Unit)? = null

    /**
     * objeto que facilita la actualización de los elementos del
     * recyclerview (actualiza solo los elementos que cambian)
     */
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ResponseItem>(){
        override fun areItemsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem.show.id == newItem.show.id
        }

        override fun areContentsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
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
                fun bind(item: ResponseItem){

                    binding.txtName.text = item.show.name
                    binding.txtNetworkName.text = item.show.network?.name
                    binding.txtAirDateTime.text = "${item.show.schedule.time} | day"

                    Glide.with(binding.ivShowImage.context)
                        .load(item.show.image?.medium)
                        .into(binding.ivShowImage)

                    binding.root.setOnClickListener {
                        onItemClickListener?.let {
                            it(item)
                        }
                    }
                }
            }

    fun setOnItemClickListener(listener : (ResponseItem)->Unit){
        onItemClickListener = listener
    }
}

