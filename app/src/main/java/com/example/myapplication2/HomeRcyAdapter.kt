package com.example.myapplication2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.databinding.RcyrowBinding
import com.squareup.picasso.Picasso

class HomeRcyAdapter(private val postList: ArrayList<Post>): RecyclerView.Adapter<HomeRcyAdapter.PostHolder>() {

    var onItemClick:((Post)->Unit)?=null

    class PostHolder(val binding : RcyrowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RcyrowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post=postList[position]
        if(!postList.isEmpty())
        {

            Picasso.get().load(postList.get(position).resimler!!.get(0)).into(holder.binding.rcyimageview)

        }
        holder.binding.rcyemailtext.text=postList.get(position).marka+" "+postList.get(position).model
        holder.binding.rcycommnettext.text=postList.get(position).fiyat+"TL"
        //holder.itemView.setBackgroundColor(0x00000000)
     //   Picasso.get().load(postList.get(position).Resimler!!.get(0)).into(holder.binding.rcyimageview)
      //  holder.binding.rcyimageview=postList.get(position).Resimler.get(0).
      //  Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.rcyimageview)
        //println(postList.get(position).resimler!!.size)
        println("SİZEEEEEE"+  postList.get(position).resimler!!.size)


        holder.itemView.setOnClickListener{
            onItemClick?.invoke(post)

        }



      //  Picasso.get().load(postList.get(position).resimler.get(1).

    }

    override fun getItemCount(): Int {
        return postList.size
    }

}