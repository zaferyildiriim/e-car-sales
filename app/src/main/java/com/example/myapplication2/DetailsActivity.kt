package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var imagesAdapter: ImageAdapter
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        /*


           intent.putExtra("fiyat",it.fiyat)
           intent.putExtra("id",it.id)
           intent.putExtra("km",it.km)
           intent.putExtra("marka",it.marka)
           intent.putExtra("model",it.model)
           intent.putExtra("paket",it.paket)
           intent.putExtra("renk",it.renk)
           intent.putExtra("yakit",it.yakit)
         */




        val post=intent.getStringArrayListExtra("resimler")
        val aciklama=intent.getStringExtra("aciklama")
        val fiyat=intent.getStringExtra("fiyat")

        val id=intent.getStringExtra("id")
        val km=intent.getStringExtra("km")
        val marka=intent.getStringExtra("marka")
        val model=intent.getStringExtra("model")
        val paket=intent.getStringExtra("paket")
        val renk=intent.getStringExtra("renk")
        val yakit=intent.getStringExtra("yakit")
        val modelYil=intent.getStringExtra("modelYil")

        ilanAciklama2.text=aciklama
        ilanFiyat2.text=fiyat
        ilanNo2.text=id
        ilanKm2.text=km
        ilanModel2.text=model
        ilanMarka2.text=marka
        ilanPaket2.text=paket
        ilanRenk2.text=renk
        ilanYakıt2.text=yakit
        ilanModelYil2.text=modelYil






        if(post!=null){
            viewPager = findViewById(R.id.viewPager)
            println("QQQQQQQQQQQQ"+post.size)
            val adapter = ImageAdapter(this, post)
            viewPager.adapter = adapter

        }
    }
}


