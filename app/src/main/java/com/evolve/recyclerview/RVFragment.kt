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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evolve.recyclerview.data.adapters.AllAppsAdapter
import com.evolve.recyclerview.data.adapters.FeaturedAppsAdapter
import com.evolve.recyclerview.data.models.AppModel
import com.evolve.recyclerview.data.models.DataViewModel
import com.evolve.recyclerview.data.models.FeaturedItems
import com.evolve.recyclerview.databinding.FragmentRvBinding
import com.evolve.recyclerview.utility.*
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*

class RVFragment : Fragment() {
    private lateinit var binding: FragmentRvBinding
    private val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_rv,container,false)

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab : TabLayout.Tab) {
                retrieveRepositories(tab.position)
                viewModel.tab = tab.position
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(viewModel.tab))

        setRecyclerViewItemTouchListener()

        retrieveRepositories(viewModel.tab)

        if (viewModel.network)
            CoroutineScope(Dispatchers.Main).launch {
                setAvatar(viewModel.userInfo.avatar)
            }

        return binding.root
    }

    private fun onClick(id: Int, owned: Int){
        viewModel.appId = id
        viewModel.owned = owned
        view?.findNavController()?.navigate(R.id.action_RVFragment_to_appDetailsFragment)
    }

    private fun setRecyclerViewItemTouchListener() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (swipeDir == ItemTouchHelper.LEFT) {
                    when (binding.tabLayout.selectedTabPosition){
                        0 ->    viewModel.featuredApps.items.removeAt(position)
                        1 ->    viewModel.featuredCategories.specials.items.removeAt(position)
                        2 ->    viewModel.featuredCategories.new_releases.items.removeAt(position)
                        3 ->    viewModel.featuredCategories.coming_soon.items.removeAt(position)
                        4 ->    viewModel.featuredCategories.top_sellers.items.removeAt(position)
                        5 ->    viewModel.favApps.removeAt(position)
                        else -> viewModel.allApps.applist.apps.removeAt(position)
                    }
                    binding.rv.adapter!!.notifyItemRemoved(position)
                }
                else {
                    when (binding.tabLayout.selectedTabPosition){
                        0 ->    removeFromList(viewModel.featuredApps, position)
                        1 ->    removeFromList(viewModel.featuredCategories.specials, position)
                        2 ->    removeFromList(viewModel.featuredCategories.new_releases, position)
                        3 ->    removeFromList(viewModel.featuredCategories.coming_soon, position)
                        4 ->    removeFromList(viewModel.featuredCategories.top_sellers, position)
                        5 ->    viewModel.favApps.removeAt(position)
                        else -> {
                            addToFavList(viewModel.allApps.applist.apps[position])
                            viewModel.allApps.applist.apps.removeAt(position)
                        }
                    }
                    binding.rv.adapter!!.notifyItemRemoved(position)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rv)
    }

    private fun removeFromList(list: FeaturedItems, position: Int){
        addToFavList(AppModel(list.items[position].id, list.items[position].name))
        list.items.removeAt(position)
    }

    private fun addToFavList(app: AppModel){
        if (!viewModel.favApps.contains(app))
            viewModel.favApps.add(app)
    }

    private fun retrieveRepositories(choice: Int) {
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(requireActivity()).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            when (choice) {
                FEATURED_APPS ->    initRVfeatured(viewModel.featuredApps)
                SPECIALS ->         initRVfeatured(viewModel.featuredCategories.specials)
                NEW_RELEASES ->     initRVfeatured(viewModel.featuredCategories.new_releases)
                COMING_SOON ->      initRVfeatured(viewModel.featuredCategories.coming_soon)
                TOP_SELLERS ->      initRVfeatured(viewModel.featuredCategories.top_sellers)
                FAV_APPS ->         initRVall(viewModel.favApps)
                else ->             initRVall(viewModel.allApps.applist.apps)
            }
        }
    }

    private fun initRVall(data: List<AppModel>) {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = AllAppsAdapter(
                viewModel.ownedApps, viewModel.wislistedApps,
                clickListener = { onClick(it[0], it[1]) } ).apply { submitList(data) }
        }
    }

    private fun initRVfeatured(data: FeaturedItems) {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = FeaturedAppsAdapter(
                viewModel.ownedApps, viewModel.wislistedApps,
                clickListener = { onClick(it[0], it[1]) } ).apply { submitList(data.items) }
        }
    }

}