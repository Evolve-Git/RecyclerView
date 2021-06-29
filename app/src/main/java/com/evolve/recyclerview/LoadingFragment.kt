package com.evolve.recyclerview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.evolve.recyclerview.data.LocalDataSource
import com.evolve.recyclerview.data.Retriever
import com.evolve.recyclerview.data.models.DataViewModel
import com.evolve.recyclerview.databinding.FragmentLoadingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingFragment : Fragment() {
    private lateinit var binding: FragmentLoadingBinding
    private val viewModel: DataViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            if (viewModel.network) {
                viewModel.userInfo = Retriever().getUserInfo(viewModel.userId)
                binding.textWelcome.text = String.format(resources.getString(R.string.welcome),
                    viewModel.userInfo.response.players[0].personaname)
                progress(10)

                viewModel.ownedApps = Retriever().getOwnedApps(viewModel.userId)
                progress(10)

                var counter = 0
                var result = Retriever().getWishlistedApps(viewModel.userId, counter)
                while (result.isNotEmpty()){
                    Log.e("wishlist", result.toString())
                    viewModel.wislistedApps.putAll(result)
                    counter += 1
                    result = Retriever().getWishlistedApps(viewModel.userId, counter)
                    progress(1)
                }

                viewModel.allApps = Retriever().getAppList()
                progress(10)

                for (app in viewModel.allApps.applist.apps){
                    if (viewModel.ownedApps.response.games.any { it.appid == app.appid })
                        app.owned = 1
                    else if (viewModel.wislistedApps.containsKey(app.appid)) app.owned = 2
                }
                progress(10)

                viewModel.featuredApps = Retriever().getFeaturedAppList()
                progress(10)

                for (app in viewModel.featuredApps.items){
                    if (viewModel.ownedApps.response.games.any { it.appid == app.id })
                        app.owned = 1
                    else if (viewModel.wislistedApps.containsKey(app.id)) app.owned = 2
                }
                progress(10)

                viewModel.favApps = LocalDataSource.loadFavAppsData(requireContext())
                progress(10)

                view?.findNavController()?.navigate(R.id.action_loadingFragment_to_RVFragment)
            } else {
                useLocalData()
            }
        }
    }

    private fun progress(step: Int){
        binding.progressBar.progress += step
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_loading,container,false)

        return binding.root
    }

    private fun useLocalData(){
        viewModel.allApps = LocalDataSource.loadAllAppsData(requireContext())
        viewModel.featuredApps = LocalDataSource.loadFeaturedAppsData(requireContext())
        viewModel.favApps = LocalDataSource.loadFavAppsData(requireContext())
        view?.findNavController()?.navigate(R.id.action_loadingFragment_to_RVFragment)
    }
}