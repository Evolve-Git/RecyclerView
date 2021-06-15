package com.evolve.recyclerview.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evolve.recyclerview.R
import com.evolve.recyclerview.databinding.RvItemBinding
import com.evolve.recyclerview.data.models.AppModel

class AllAppsAdapter(val clickListener: (Int) -> Unit): ListAdapter<AppModel,
        AllAppsAdapter.AllAppsModelViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAppsModelViewHolder {
        return AllAppsModelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item,
            parent, false))
    }

    override fun onBindViewHolder(holder: AllAppsModelViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { clickListener(holder.steamId) }
    }

    class AllAppsModelViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = RvItemBinding.bind(itemView)
        private val modelImage = binding.modelImage
        private val modelTitle = binding.modelTitle
        private val modelId = binding.modelId
        var steamId = 0

        fun bind(model: AppModel){
            steamId = model.appid
            modelTitle.text = model.name
            modelId.text = model.appid.toString()

            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.image)
                    .error(R.drawable.image)
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load("https://steamcdn-a.akamaihd.net/steam/apps/"+
                            model.appid.toString()+"/header_292x136.jpg")
                    .into(modelImage)
        }
    }
}

//Cant find any uses for this:
class DiffCallback : DiffUtil.ItemCallback<AppModel>() {
    override fun areItemsTheSame(oldItem: AppModel, newItem: AppModel): Boolean {
        return oldItem.appid == newItem.appid
    }

    override fun areContentsTheSame(oldItem: AppModel, newItem: AppModel): Boolean {
        return oldItem == newItem
    }
}