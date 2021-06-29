package com.evolve.recyclerview.data.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evolve.recyclerview.R
import com.evolve.recyclerview.databinding.RvItemBinding
import com.evolve.recyclerview.data.models.AppModel
import com.evolve.recyclerview.data.models.OwnedAppsModel
import com.evolve.recyclerview.data.models.WishlistedAppsModel
import com.evolve.recyclerview.utility.retrieveImage

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
        var steamId = 0

        fun bind(model: AppModel){
            steamId = model.appid
            binding.modelTitle.text = model.name
            binding.modelId.text = model.appid.toString()
            when (model.owned){
                1 -> {binding.ownedIcon.setImageResource(R.drawable.owned)
                    binding.ownedText.setText(R.string.owned)}
                2 -> {binding.ownedIcon.setImageResource(R.drawable.wishlisted)
                    binding.ownedText.setText(R.string.wishlisted)}
                else -> {binding.ownedIcon.setImageDrawable(null)
                    binding.ownedText.text = null
                }
            }

            retrieveImage(R.drawable.image, itemView,
                "https://steamcdn-a.akamaihd.net/steam/apps/"+
                    model.appid.toString()+"/header_292x136.jpg", binding.modelImage)
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