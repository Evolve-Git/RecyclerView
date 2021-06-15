package com.evolve.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.evolve.recyclerview.databinding.ActivityMainBinding
import com.evolve.recyclerview.data.models.*

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
}