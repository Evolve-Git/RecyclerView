package com.evolve.recyclerview

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evolve.recyclerview.databinding.RvItemBinding
import com.evolve.recyclerview.models.FeaturedApp

class FeaturedAppsAdapter(private val itemList: List<FeaturedApp>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AllAppsModelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AllAppsModelViewHolder -> holder.bind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class AllAppsModelViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = RvItemBinding.bind(itemView)
        private val modelImage = binding.modelImage
        private val modelTitle = binding.modelTitle
        private val modelId = binding.modelId

        fun bind(model: FeaturedApp) {
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
}