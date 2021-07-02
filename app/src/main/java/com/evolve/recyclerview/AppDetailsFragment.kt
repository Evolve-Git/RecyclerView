package com.evolve.recyclerview

import android.annotation.SuppressLint
import android.app.ActionBar
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.evolve.recyclerview.data.Retriever
import com.evolve.recyclerview.data.models.Data
import com.evolve.recyclerview.data.models.DataViewModel
import com.evolve.recyclerview.databinding.FragmentAppDetailsBinding
import com.evolve.recyclerview.utility.retrieveImage
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
            if (requireActivity().isNetworkConnected()) {
                if (!viewModel.appDetailCache.containsKey(viewModel.appId)) {
                    val appDetails = Retriever().getAppDetails(viewModel.appId)
                    if ((appDetails[viewModel.appId] != null) && appDetails[viewModel.appId]!!.success)
                        viewModel.appDetailCache.putAll(appDetails)
                }

                binding.detailsLayout.setBackgroundResource(when (viewModel.owned){
                    1 ->    R.drawable.rvitem_owned
                    2 ->    R.drawable.rvitem_wishlisted
                    else -> R.drawable.rvitem
                })

                if (viewModel.appDetailCache[viewModel.appId] != null) {
                    data = viewModel.appDetailCache[viewModel.appId]!!.data

                    binding.id.text = "AppId: ${data.steam_appid}"

                    binding.title.text = data.name

                    var price = "Price: "
                    price += when{
                        data.is_free ->                 "Free"
                        data.price_overview == null ->  "not specified"
                        else ->                         data.price_overview?.final_formatted
                    }
                    binding.price.text = price

                    loadImage(data.header_image)
                    var screenshotCounter = 0
                    binding.image.setOnClickListener {
                        if (data.screenshots != null) {
                            loadImage(data.screenshots!![screenshotCounter].path_thumbnail)
                            screenshotCounter += 1
                            if (screenshotCounter >= data.screenshots!!.size)
                                screenshotCounter = 0
                        }
                    }

                    toggleDescription()
                    binding.description.setOnClickListener { toggleDescription() }

                    var categories = "Categories: "
                    if (data.categories != null)
                        data.categories!!.forEach { categories += "${it.description}, " }
                    else categories += "not specified  "
                    binding.categories.text = categories.dropLast(2)

                    var genres = "Genres: "
                    if (data.genres != null)
                        data.genres!!.forEach { genres += "${it.description}, " }
                    else genres += "not specified  "
                    binding.genres.text = genres.dropLast(2)

                    if (data.metacritic != null)
                        binding.metacritic.text = "Metacritic score: ${data.metacritic!!.score}"
                    else binding.metacritic.text = "Metacritic score is not available."
                }
                else binding.title.text = "This app does not have any details."
            }
            else binding.title.text = "No network connection to fetch app details."
        }

        return binding.root
    }

    private fun loadImage(img: String){
        retrieveImage(R.drawable.image, binding.root, img, binding.image)
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