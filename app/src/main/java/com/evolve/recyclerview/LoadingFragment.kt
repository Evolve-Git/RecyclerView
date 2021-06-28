package com.evolve.recyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
                viewModel.allApps = Retriever().getAppList()
                viewModel.featuredApps = Retriever().getFeaturedAppList()
                viewModel.favApps = LocalDataSource.loadFavAppsData(requireContext())
                view?.findNavController()?.navigate(R.id.action_loadingFragment_to_RVFragment)
            } else {
                useLocalData()
            }
        }
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