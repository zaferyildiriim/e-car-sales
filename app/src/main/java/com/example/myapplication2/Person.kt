package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Person : AppCompatActivity() {
    private var message = "hello"
    val fragmentManager=supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        val mainintent=intent
        val id=mainintent.getStringExtra("id")

        message=id.toString()
        println("skdjfg"+message)
        replaceFragment(PersonFragment())

    }
    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        // val message = id
        bundle.putString("message", message)
        println("replaceFragment"+message)

    }


    fun getMyData(): String? {
        return message
    }
//    override fun onBackPressed() {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("id",message)
//        startActivity(intent)
//        finish()
//    }

}