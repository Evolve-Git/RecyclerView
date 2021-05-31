package com.evolve.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evolve.recyclerview.databinding.RvItemBinding
import com.evolve.recyclerview.models.AppModel

class AllAppsAdapter(private val itemList: List<AppModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

    class AllAppsModelViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = RvItemBinding.bind(itemView)
        private val modelImage = binding.modelImage
        private val modelTitle = binding.modelTitle
        private val modelId = binding.modelId

        fun bind(model: AppModel){
            modelTitle.text = model.name
            modelId.text = model.appid.toString()

            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.image)
                    .error(R.drawable.image)
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load("https://steamcdn-a.akamaihd.net/steam/apps/"+model.appid.toString()+"/header_292x136.jpg")
                    .into(modelImage)
        }
    }
}