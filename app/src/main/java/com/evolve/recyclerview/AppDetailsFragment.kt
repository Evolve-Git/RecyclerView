package com.evolve.recyclerview

import android.annotation.SuppressLint
import android.app.ActionBar
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evolve.recyclerview.data.Retriever
import com.evolve.recyclerview.data.models.Data
import com.evolve.recyclerview.data.models.DataViewModel
import com.evolve.recyclerview.databinding.FragmentAppDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppDetailsFragment : Fragment() {
    private lateinit var binding: FragmentAppDetailsBinding
    private val viewModel: DataViewModel by activityViewModels()
    private lateinit var data: Data
    private var toggleDescription = false

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_app_details,container,false)
        ActionBar.DISPLAY_SHOW_HOME

        CoroutineScope(Dispatchers.Main).launch {
            if (!viewModel.appDetailCache.containsKey(viewModel.app_id)) {
                val appDetails = Retriever().getAppDetails(viewModel.app_id)
                if ((appDetails[viewModel.app_id] != null) && appDetails[viewModel.app_id]!!.success)
                    viewModel.appDetailCache.putAll(appDetails)
                Log.e("det", appDetails.toString())
                Log.e("id", viewModel.app_id.toString())
            }

            if (viewModel.appDetailCache[viewModel.app_id] != null) {
                data = viewModel.appDetailCache[viewModel.app_id]!!.data
                Log.e("id", viewModel.app_id.toString())

                binding.id.text = "AppId: ${data.steam_appid}"

                binding.title.text = data.name

                var price = "Price: "
                price += if (data.is_free) "Free"
                else if (data.price_overview == null) "TBA"
                else data.price_overview?.final_formatted
                binding.price.text = price

                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.image)
                    .error(R.drawable.image)
                Glide.with(binding.root)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(data.header_image)
                    .into(binding.image)

                toggleDescription()
                binding.description.setOnClickListener { toggleDescription() }

                var categories = "Categories: "
                data.categories.forEach { categories += "${it.description}, " }
                binding.categories.text = categories.dropLast(2)

                var genres = "Genres: "
                data.genres.forEach { genres += "${it.description}, " }
                binding.genres.text = genres.dropLast(2)

                if (data.metacritic != null)
                    binding.metacritic.text = "Metacritic score: ${data.metacritic!!.score}"
                else binding.metacritic.text = "Metacritic score is not available."
            }
            else {
                binding.title.text = "This app does not have any details."
            }
        }

        return binding.root
    }

    private fun toggleDescription(){
        if (toggleDescription)
            binding.description.text = Html.fromHtml(data.about_the_game,
                Html.FROM_HTML_MODE_COMPACT)
        else
            binding.description.text = Html.fromHtml(data.short_description,
                Html.FROM_HTML_MODE_COMPACT)
        toggleDescription = !toggleDescription
    }
}