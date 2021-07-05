package com.evolve.recyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.evolve.recyclerview.data.DataStorePrefs
import com.evolve.recyclerview.data.Retriever
import com.evolve.recyclerview.data.models.DataViewModel
import com.evolve.recyclerview.databinding.FragmentLoadingBinding
import com.evolve.recyclerview.utility.EUR
import com.evolve.recyclerview.utility.RUB
import com.evolve.recyclerview.utility.USD
import com.evolve.recyclerview.utility.setAvatar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoadingFragment : Fragment() {
    private lateinit var binding: FragmentLoadingBinding
    private val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_loading,container,false)

        binding.progressBar.max = 70

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStorePrefs = DataStorePrefs(requireActivity().applicationContext)

        CoroutineScope(Dispatchers.IO).launch {
            if (viewModel.network) {
                viewModel.userInfo = Retriever().getUserInfo(viewModel.userId)
                withContext(Dispatchers.Main){
                    binding.textWelcome.text = String.format(resources.getString(R.string.welcome),
                        viewModel.userInfo.personaname)
                    setAvatar(viewModel.userInfo.avatar)}
                addProgress()

                viewModel.ownedApps = Retriever().getOwnedApps(viewModel.userId)
                addProgress()

                var counter = 0
                var result = Retriever().getWishlistedApps(viewModel.userId, counter)
                while (result.isNotEmpty()){
                    viewModel.wislistedApps.putAll(result)
                    counter += 1
                    result = Retriever().getWishlistedApps(viewModel.userId, counter)
                    addProgress(1)
                }

                viewModel.allApps = Retriever().getAppList()
                addProgress()

                viewModel.featuredApps = Retriever().getFeaturedAppList()
                addProgress()

                viewModel.featuredCategories = Retriever().getFeaturedCategoriesList(
                    getCurrency(viewModel.featuredApps.items[0].currency))
                addProgress()

                viewModel.favApps = dataStorePrefs.restoreFavApps()
                addProgress()

                withContext(Dispatchers.Main){
                    switchToRVFragment()}
            } else {
                dataStorePrefs.restoreData(viewModel)
                addProgress(40)

                viewModel.favApps = dataStorePrefs.restoreFavApps()
                addProgress()

                viewModel.ownedApps = mapOf()
                addProgress()

                withContext(Dispatchers.Main) {
                    switchToRVFragment()
                }
            }
        }
    }

    private fun switchToRVFragment(){
        view?.findNavController()?.navigate(R.id.action_loadingFragment_to_RVFragment)
    }

    private fun getCurrency(currency: String): Int{
        return when (currency){
            "EUR" -> EUR
            "RUB" -> RUB
            else  -> USD
        }
    }

    private fun addProgress(step: Int = 10){
        binding.progressBar.progress += step
    }
}