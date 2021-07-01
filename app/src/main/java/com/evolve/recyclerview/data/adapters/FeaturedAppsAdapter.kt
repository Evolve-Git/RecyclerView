package com.evolve.recyclerview.data.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evolve.recyclerview.R
import com.evolve.recyclerview.databinding.RvItemBinding
import com.evolve.recyclerview.data.models.FeaturedApp
import com.evolve.recyclerview.data.models.OwnedAppModel
import com.evolve.recyclerview.data.models.WishlistedAppsModel
import com.evolve.recyclerview.utility.retrieveImage
import com.evolve.recyclerview.utility.setOwnedTag

class FeaturedAppsAdapter(val ownedApps: Map<Int, OwnedAppModel>,
                          val wishApps: Map<Int, WishlistedAppsModel>,
                          val clickListener: (Int) -> Unit): ListAdapter<FeaturedApp,
        FeaturedAppsAdapter.FeaturedAppsModelViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedAppsModelViewHolder {
        return FeaturedAppsModelViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: FeaturedAppsModelViewHolder, position: Int) {
        val owned = when{
            ownedApps.containsKey(getItem(position).id) -> 1
            wishApps.containsKey(getItem(position).id) -> 2
            else -> 0
        }
        holder.bind(getItem(position), owned)
        holder.itemView.setOnClickListener { clickListener(holder.steamId) }
    }

    class FeaturedAppsModelViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = RvItemBinding.bind(itemView)
        var steamId = 0

        fun bind(model: FeaturedApp, owned: Int) {
            steamId = model.id
            binding.modelTitle.text = model.name

            var tempOriginalPrice = model.original_price
            var price = model.final_price.toString()
            if (model.currency == "RUB") {
                tempOriginalPrice = model.original_price / 100
                price = (model.final_price / 100).toString()
            }
            if (model.discounted) {
                price = "<s>$tempOriginalPrice</s> $price"
            }
            price += model.currency
            if (model.final_price == 0) price = "<b>FREE</b>"
            binding.modelId.text = Html.fromHtml("Price: $price", Html.FROM_HTML_MODE_COMPACT)

            binding.setOwnedTag(owned)

            retrieveImage(R.drawable.image, itemView, model.header_image, binding.modelImage)
        }
    }

    //Cant find any uses for this:
    class DiffCallback : DiffUtil.ItemCallback<FeaturedApp>() {

        override fun areItemsTheSame(oldItem: FeaturedApp, newItem: FeaturedApp): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FeaturedApp, newItem: FeaturedApp): Boolean {
            return oldItem == newItem
        }
    }
}