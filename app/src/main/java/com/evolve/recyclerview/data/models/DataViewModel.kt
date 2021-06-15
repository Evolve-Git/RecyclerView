package com.evolve.recyclerview.data.models

import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {
    var app_id = 0
    var appDetailCache = mutableMapOf<Int, AppDetailModel>()
}