package com.evolve.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.evolve.recyclerview.databinding.ActivityMainBinding
import com.evolve.recyclerview.data.models.DataViewModel
import com.google.gson.Gson
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var activity: ActivityMainBinding
    private lateinit var viewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = DataBindingUtil.setContentView(this,
            R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onStop() {
        val jsonAllApps: String = Gson().toJson(viewModel.allApps)
        File("${filesDir}/allapps.json").writeText(jsonAllApps)

        val jsonFeaturedApps: String = Gson().toJson(viewModel.featuredApps)
        File("${filesDir}/featuredapps.json").writeText(jsonFeaturedApps)

        val jsonFavApps: String = Gson().toJson(viewModel.favApps)
        File("${filesDir}/favapps.json").writeText(jsonFavApps)

        super.onStop()
    }
}