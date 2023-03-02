package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication2.databinding.ActivityHomeBinding
import com.example.myapplication2.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    private var message = "hello"
    private lateinit var binding: ActivityMainBinding
   // private lateinit var binding : ActivityHomeBinding
    private lateinit var postArrayList:ArrayList<Post>
    private lateinit var zafer:ArrayList<String>
    private lateinit var homeAdapter: HomeRcyAdapter
   // var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cars-56ba1-default-rtdb.firebaseio.com/")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainintent=intent
        val id=mainintent.getStringExtra("id")
      //  println("id=="+id)
        message= id.toString()
       val view=binding.root
       getData()
      //  println("message=="+message)

       binding.bottomnavigation.setOnItemSelectedListener {
           when(it.itemId)
           {
               R.id.home->{
//                   replaceFragment(HomeFragment())
                   intent=Intent(applicationContext,MainActivity::class.java)
                   intent.putExtra("id",message)
                   startActivity(intent)

               }
               R.id.profie->{
                   //replaceFragment(PersonFragment())
                   intent=Intent(applicationContext,Person::class.java)
                   intent.putExtra("id",message)
                   startActivity(intent)


               }
               R.id.addCar->{
                  // replaceFragment(AddFragment())
                   intent= Intent(applicationContext,Add::class.java)
                   intent.putExtra("id",message)
                   startActivity(intent)

               }
           }
           true
       }
       setContentView(view)
       postArrayList=ArrayList<Post>()
       zafer=ArrayList<String>()

       binding.rcyIlan.layoutManager= LinearLayoutManager(this)
       homeAdapter= HomeRcyAdapter(postArrayList)
       binding.rcyIlan.adapter=homeAdapter
       val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

       homeAdapter.onItemClick={
           println("sdffsdfsd"+it.resimler?.size)
           val intent=Intent(this,DetailsActivity::class.java)
           intent.putExtra("resimler",it.resimler)
           intent.putExtra("aciklama",it.aciklama)
           intent.putExtra("fiyat",it.fiyat)
           intent.putExtra("id",it.id)
           intent.putExtra("km",it.km)
           intent.putExtra("marka",it.marka)
           intent.putExtra("model",it.model)
           intent.putExtra("paket",it.paket)
           intent.putExtra("renk",it.renk)
           intent.putExtra("yakit",it.yakit)
           intent.putExtra("modelYil",it.modelYil)


           startActivity(intent)

       }
       rcyIlan.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.abc_color_highlight_material))
       binding.rcyIlan.addItemDecoration(
           DividerItemDecoration(
               baseContext,
               layoutManager.orientation
           )
       )
       homeAdapter.notifyDataSetChanged()
       //binding.rcyIlan.addOnItemTouchListener{}

    }
/*
    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val bundle = Bundle()
      fragment.setArguments(bundle)
       // val message = id
        bundle.putString("message", message)
        println("replaceFragment"+message)
        fragmentTransaction.replace(R.id.framelayout,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

 */


    fun getMyData(): String? {
        return message
    }
    private fun getData(){

        database.child("Ilanlar").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    println(snapshot.key+"snapshot key")
                    println("sdfıhoşı"+snapshot.exists().toString())
                    for (userSnapshot in snapshot.children){

                        println("CCCCCCCCCCCCCCCCCC"+zafer.size)
                      //  println("VALUEEEEEEEE"+userSnapshot.getValue())
                        zafer.clear()
                        val id:String=userSnapshot.child("id").getValue().toString()
                        val aciklama:String=userSnapshot.child("aciklama").getValue().toString()
                        val km:String=userSnapshot.child("km").getValue().toString()
                        val renk:String=userSnapshot.child("renk").getValue().toString()
                        val fiyat:String=userSnapshot.child("fiyat").getValue().toString()
                        val yakit:String=userSnapshot.child("yakit").getValue().toString()
                        val marka:String=userSnapshot.child("marka").getValue().toString()
                        val model:String=userSnapshot.child("model").getValue().toString()
                        val paket:String=userSnapshot.child("paket").getValue().toString()
                        val modelYil:String=userSnapshot.child("modelYil").getValue().toString()


                        for(i in userSnapshot.child("resimler").children)
                        {

                            //println("HAHAHAHA"+i.child("ImgLink").getValue().toString())
                            var zz=i.child("ImgLink").getValue().toString()
                             zafer.add(zz)
                        }
                        println("BBBBBBBBBBBBBBBB"+zafer.size)
                        val myuser=Post(id,aciklama,km,renk,fiyat, yakit  ,marka,model,paket, modelYil ,ArrayList(zafer))
                        println("AAAAAAAAAAAAAAAAAAAAAA"+zafer.size)
                        //val user=userSnapshot.getValue(Post::class.java)
                       postArrayList.add(myuser!!)
                        homeAdapter.notifyDataSetChanged()
                       // zafer.clear()


                    }
                    //  binding.rcyIlan.adapter=HomeRcyAdapter(postArrayList)
                    //  binding.rcyIlan.adapter=HomeRcyAdapter(postArrayList)




                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

}