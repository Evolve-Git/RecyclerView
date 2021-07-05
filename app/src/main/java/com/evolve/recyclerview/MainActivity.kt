package com.evolve.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.evolve.recyclerview.data.DataStorePrefs
import com.evolve.recyclerview.databinding.ActivityMainBinding
import com.evolve.recyclerview.data.models.DataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var activity: ActivityMainBinding
    private lateinit var viewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(activity.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onStop() {
        if (viewModel.network) {
            val dataStorePrefs = DataStorePrefs(applicationContext)
            CoroutineScope(Dispatchers.IO).launch {
                dataStorePrefs.saveData(viewModel)
            }
        }

        super.onStop()
    }
}