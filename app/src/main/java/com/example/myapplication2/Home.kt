package com.example.myapplication2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication2.databinding.ActivityHomeBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.Query

class Home : AppCompatActivity() {
    private lateinit var binding :ActivityHomeBinding
    private lateinit var postArrayList:ArrayList<Post>
    private lateinit var homeAdapter: HomeRcyAdapter

    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cars-56ba1-default-rtdb.firebaseio.com/")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        val view=binding.root

        setContentView(view)
        postArrayList=ArrayList<Post>()
        getData()
       // binding.rcyIlan.layoutManager=LinearLayoutManager(this)
       // binding.rcyIlan.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rcyIlan.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        homeAdapter= HomeRcyAdapter(postArrayList)
        binding.rcyIlan.adapter=homeAdapter
        binding.rcyIlan.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                layoutManager.orientation
            )
        )
        homeAdapter.notifyDataSetChanged()



    }
    private fun getData(){
        database.child("Ilanlar").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    println(snapshot.key+"snapshot key")
                    println("sdfıhoşı"+snapshot.exists().toString())
                    for (userSnapshot in snapshot.children){
                        println("VALUEEEEEEEE"+userSnapshot.getValue())

                        val user=userSnapshot.getValue(Post::class.java)
                        println("sıkıntı yokk")
                        postArrayList.add(user!!)
                        println("sıkıntı yokk213213321231")
                    }
                  //  binding.rcyIlan.adapter=HomeRcyAdapter(postArrayList)
                  //  binding.rcyIlan.adapter=HomeRcyAdapter(postArrayList)
                    homeAdapter.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}