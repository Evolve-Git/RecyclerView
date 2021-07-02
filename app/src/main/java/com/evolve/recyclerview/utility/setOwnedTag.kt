package com.evolve.recyclerview.utility

import com.evolve.recyclerview.R
import com.evolve.recyclerview.databinding.RvItemBinding

fun RvItemBinding.setOwnedTag(owned: Int){
    when (owned){
        1 -> {ownedIcon.setImageResource(R.drawable.owned)
            ownedIcon.setBackgroundResource(R.color.owned)
            ownedText.setText(R.string.owned)
            ownedText.setBackgroundResource(R.color.owned)
            rvLayout.setBackgroundResource(R.drawable.rvitem_owned)}
        2 -> {ownedIcon.setImageResource(R.drawable.wishlisted)
            ownedIcon.setBackgroundResource(R.color.wishlisted)
            ownedText.setText(R.string.wishlisted)
            ownedText.setBackgroundResource(R.color.wishlisted)
        rvLayout.setBackgroundResource(R.drawable.rvitem_wishlisted)}
        else -> {ownedIcon.setImageDrawable(null)
            ownedText.text = null
            rvLayout.setBackgroundResource(R.drawable.rvitem)}
    }
}