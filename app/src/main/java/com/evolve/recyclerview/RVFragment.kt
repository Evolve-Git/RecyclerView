package com.evolve.recyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evolve.recyclerview.data.LocalDataSource
import com.evolve.recyclerview.data.Retriever
import com.evolve.recyclerview.data.adapters.AllAppsAdapter
import com.evolve.recyclerview.data.adapters.FeaturedAppsAdapter
import com.evolve.recyclerview.data.models.AppList
import com.evolve.recyclerview.data.models.AppListModel
import com.evolve.recyclerview.data.models.AppModel
import com.evolve.recyclerview.data.models.FeaturedItems
import com.evolve.recyclerview.databinding.FragmentRvBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*

class RVFragment : Fragment() {
    private lateinit var binding: FragmentRvBinding
    //private val viewModel: DataViewModel by activityViewModels()
    private lateinit var allApps: AppListModel
    private lateinit var featuredApps: FeaturedItems
    private var favApps = arrayListOf<AppModel>()
    private var activeTab = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_rv,container,false)
        //binding.DataViewModel = viewModel

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab : TabLayout.Tab) {
                when (tab.position) {
                    1 -> {
                        activeTab = 1
                        retrieveRepositories(1)
                    }
                    2 -> {
                        activeTab = 2
                        retrieveRepositories(2)
                    }
                    else -> {
                        activeTab = 0
                        retrieveRepositories()
                    }
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })

        setRecyclerViewItemTouchListener()

        CoroutineScope(Dispatchers.Main).launch {
            if (requireActivity().isNetworkConnected()) {
                allApps = Retriever().getAppList()
                featuredApps = Retriever().getFeaturedAppList()
                retrieveRepositories()
            } else {
                noNetwork()
            }
        }

        return binding.root
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
                    when (activeTab){
                        1 ->    featuredApps.items.removeAt(position)
                        2 ->    favApps.removeAt(position)
                        else -> allApps.applist.apps.removeAt(position)
                    }
                    binding.rv.adapter!!.notifyItemRemoved(position)
                }
                else {
                    when (activeTab){
                        1 ->    {
                            favApps.add(AppModel(featuredApps.items[position].id,
                                featuredApps.items[position].name))
                            featuredApps.items.removeAt(position)
                        }
                        2 ->    favApps.removeAt(position)
                        else -> {
                            favApps.add(allApps.applist.apps[position])
                            allApps.applist.apps.removeAt(position)
                        }
                    }
                    binding.rv.adapter!!.notifyItemRemoved(position)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rv)
    }

    private fun noNetwork(){
        AlertDialog.Builder(requireContext()).setTitle("No Internet Connection")
            .setMessage("Web data is unreachable, load the local data?")
            .setPositiveButton(android.R.string.ok) { _, _ -> addDataSet()}
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    private fun retrieveRepositories(choice: Int = 0) {
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
                1 ->    initRVfeatured(featuredApps)
                2 ->    initRVall(favApps)
                else -> initRVall(allApps.applist.apps)
            }
        }
    }

    private fun addDataSet() {
        val data = LocalDataSource.createDataSet(requireActivity())
        initRVall(data.applist.apps)
    }

    private fun initRVall(data: List<AppModel>) {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = AllAppsAdapter().apply { submitList(data) }
        }
    }

    private fun initRVfeatured(data: FeaturedItems) {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = FeaturedAppsAdapter().apply { submitList(data.items) }
        }
    }

}