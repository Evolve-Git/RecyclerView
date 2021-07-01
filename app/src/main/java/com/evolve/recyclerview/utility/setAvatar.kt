package com.evolve.recyclerview.utility

import androidx.fragment.app.Fragment
import com.evolve.recyclerview.MainActivity

fun Fragment.setAvatar(avatar: String){
    retrieveImage(android.R.drawable.ic_menu_report_image, requireView(), avatar,
        (requireActivity() as MainActivity).activity.icon)
}