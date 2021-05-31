package com.evolve.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.evolve.recyclerview.databinding.ActivityMainBinding
import com.evolve.recyclerview.models.AppListModel
import com.evolve.recyclerview.models.FeaturedItems
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var activity: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = DataBindingUtil.setContentView(this, R.layout.activity_main)

        activity.tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab : TabLayout.Tab) {
                if (tab.position == 1) retrieveRepositories(1)
                else retrieveRepositories()
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })

        if (isNetworkConnected()) {
            retrieveRepositories()
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Web data is unreachable, load the local data?")
                .setPositiveButton(android.R.string.ok) { _, _ -> addDataSet()}
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun retrieveRepositories(choice: Int = 0) {
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            when (choice){
                1 ->    initRVfeatured(Retriever().getFeaturedAppList())
                else -> initRVall(Retriever().getAppList())
            }
        }
    }

    private fun addDataSet() {
        val data = LocalDataSource.createDataSet(applicationContext)
        initRVall(data)
    }

    private fun initRVall(data: AppListModel) {
        activity.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = AllAppsAdapter(data.applist.apps)
        }
    }

    private fun initRVfeatured(data: FeaturedItems) {
        activity.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = FeaturedAppsAdapter(data.items)
        }
    }
}