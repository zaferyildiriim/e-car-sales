package com.example.myapplication2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso

class ImageAdapter(private val mContext: Context, private val images: ArrayList<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(mContext)
        imageView.scaleType = ImageView.ScaleType.CENTER
        println("picasso size"+images.size)
        println(  Picasso.get().load(images.get(position).toString()))
        Picasso.get().load(images.get(position).toString()).into(imageView)
        container.addView(imageView, 0)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }
}
