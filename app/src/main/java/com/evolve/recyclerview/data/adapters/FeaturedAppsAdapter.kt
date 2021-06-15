package com.evolve.recyclerview.data.adapters

import android.text.Html
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
import com.evolve.recyclerview.data.models.FeaturedApp

class FeaturedAppsAdapter(val clickListener: (Int) -> Unit): ListAdapter<FeaturedApp,
        FeaturedAppsAdapter.FeaturedAppsModelViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedAppsModelViewHolder {
        return FeaturedAppsModelViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: FeaturedAppsModelViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { clickListener(holder.steamId) }
    }

    class FeaturedAppsModelViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = RvItemBinding.bind(itemView)
        private val modelImage = binding.modelImage
        private val modelTitle = binding.modelTitle
        private val modelId = binding.modelId
        var steamId = 0

        fun bind(model: FeaturedApp) {
            steamId = model.id
            modelTitle.text = model.name

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
            modelId.text = Html.fromHtml("Price: $price", Html.FROM_HTML_MODE_COMPACT)

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(model.header_image)
                .into(modelImage)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FeaturedApp>() {

        override fun areItemsTheSame(oldItem: FeaturedApp, newItem: FeaturedApp): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FeaturedApp, newItem: FeaturedApp): Boolean {
            return oldItem == newItem
        }
    }
}