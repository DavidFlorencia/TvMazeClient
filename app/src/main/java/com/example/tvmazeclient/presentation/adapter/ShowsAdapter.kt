package com.example.tvmazeclient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvmazeclient.data.model.ScheduleResponse.Show
import com.example.tvmazeclient.databinding.ItemShowBinding

class ShowsAdapter: RecyclerView.Adapter<ShowsAdapter.ShowsViewHolder>() {
    private var onItemClickListener :((Show)->Unit)? = null

    /**
     * objeto que facilita la actualización de los elementos del
     * recyclerview (actualiza solo los elementos que cambian)
     */
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Show>(){
        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        val binding = ItemShowBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return ShowsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        val show = differ.currentList[position]
        holder.bind(show)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ShowsViewHolder(private val binding: ItemShowBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(show: Show){
                    binding.txtName.text = show.showInfo.name
                    binding.txtNetworkName.text = show.showInfo.network?.name
                    binding.txtAirDateTime.text = "${show.airdate} | ${show.airtime}"

                    Glide.with(binding.ivShowImage.context)
                        .load(show.showInfo.image.medium)
                        .into(binding.ivShowImage)

                    binding.root.setOnClickListener {
                        onItemClickListener?.let {
                            it(show)
                        }
                    }
                }
            }

    fun setOnItemClickListener(listener : (Show)->Unit){
        onItemClickListener = listener
    }
}

