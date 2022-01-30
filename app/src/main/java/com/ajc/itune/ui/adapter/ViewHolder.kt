package com.ajc.itune.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ajc.itune.local.MusicEntity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*

class ViewHolder(itemView: View, val onClick: OnClick) : RecyclerView.ViewHolder(itemView) {

    fun setData(result: MusicEntity) {
        itemView.apply {
            tvTitle.text = result.artistName
            tvWrapperType.text = result.wrapperType
            tvCountry.text = result.country
            Glide.with(ivImageView).load(result.artistImageUrl).into(ivImageView)
        }
    }
}