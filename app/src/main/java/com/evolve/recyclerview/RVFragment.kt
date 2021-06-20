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
import com.evolve.recyclerview.data.*
import com.evolve.recyclerview.data.adapters.AllAppsAdapter
import com.evolve.recyclerview.data.adapters.FeaturedAppsAdapter
import com.evolve.recyclerview.data.models.AppModel
import com.evolve.recyclerview.data.models.DataViewModel
import com.evolve.recyclerview.data.models.FeaturedItems
import com.evolve.recyclerview.databinding.FragmentRvBinding
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
                when (tab.position) {
                    1 ->    retrieveRepositories(FEATURED_APPS)
                    2 ->    retrieveRepositories(FAV_APPS)
                    else -> retrieveRepositories(ALL_APPS)
                }
                viewModel.tab = tab.position
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })

        setRecyclerViewItemTouchListener()

        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(viewModel.tab))

        retrieveRepositories(viewModel.tab)

        return binding.root
    }

    private fun onClick(id: Int){
        viewModel.app_id = id
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
                        1 ->    viewModel.featuredApps.items.removeAt(position)
                        2 ->    viewModel.favApps.removeAt(position)
                        else -> viewModel.allApps.applist.apps.removeAt(position)
                    }
                    binding.rv.adapter!!.notifyItemRemoved(position)
                }
                else {
                    when (binding.tabLayout.selectedTabPosition){
                        1 ->    {
                            viewModel.favApps.add(AppModel(viewModel.featuredApps.items[position].id,
                                viewModel.featuredApps.items[position].name))
                            viewModel.featuredApps.items.removeAt(position)
                        }
                        2 ->    viewModel.favApps.removeAt(position)
                        else -> {
                            viewModel.favApps.add(viewModel.allApps.applist.apps[position])
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
                FAV_APPS ->         initRVall(viewModel.favApps)
                else ->             initRVall(viewModel.allApps.applist.apps)
            }
        }
    }

    private fun initRVall(data: List<AppModel>) {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = AllAppsAdapter(clickListener = {
                onClick(it) } ).apply { submitList(data) }
        }
    }

    private fun initRVfeatured(data: FeaturedItems) {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = FeaturedAppsAdapter(clickListener = {
                onClick(it) } ).apply { submitList(data.items) }
        }
    }

}