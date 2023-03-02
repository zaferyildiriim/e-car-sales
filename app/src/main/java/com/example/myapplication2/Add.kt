package com.example.myapplication2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityAddBinding
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add.*
import java.util.*


class Add : AppCompatActivity()

{
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private var pressedTime: Long = 0
    private lateinit var binding:ActivityAddBinding
    private lateinit var storage: FirebaseStorage
    var list_marka = ArrayList<String>()
    var list_model = ArrayList<String>()
    var list_paket = ArrayList<String>()
    val PICK_IMAGE:Int=1
    var upLoadCount:Int=0
    var aracRenk:String=""
    var aracYakıt:String=""
    var aracMarka:String=""
    var aracModel:String=""
    var aracPaket:String=""
    private var message = "hello"
    val ImageList = ArrayList<Uri>()
    lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        val addIntent=intent
        val id=addIntent.getStringExtra("id")
        message=id.toString()
        storage= Firebase.storage
        val aa = ArrayAdapter(this@Add,android.R.layout.simple_spinner_item,list_marka)
        retrievemarka()
        getColor()
        getYakıt()
    }
    /*

        database.child("cars").child("markalar").child("VW").child("jetta").child("paket").push().setValue("cakal kasa")
        database.child("cars").child("markalar").child("VW").child("passat").child("paket").push().setValue("aşiret kasa")
        database.child("cars").child("markalar").child("VW").child("passat").child("paket").push().setValue("jilet kasa")
        database.child("cars").child("markalar").child("VW").child("jetta").child("paket").push().setValue("memur kasa")
        database.child("cars").child("markalar").child("VW").child("jetta").child("paket").push().setValue("memur kasa")
        database.child("cars").child("markalar").child("reno").child("clıo").child("paket").push().setValue("dert paket")

        database.child("cars").child("markalar").child("Ford").child("Fiesta").child("paket").push().setValue("1.4 TDCİ")
        database.child("cars").child("markalar").child("Ford").child("Fiesta").child("paket").push().setValue("1.6")
        database.child("cars").child("markalar").child("Ford").child("Connect").child("paket").push().setValue("110 luk ")
        database.child("cars").child("markalar").child("Ford").child("Connect").child("paket").push().setValue("90 lık ")
        database.child("cars").child("markalar").child("Seat").child("Leon").child("paket").push().setValue("Jilettt")
        database.child("cars").child("markalar").child("Seat").child("Leon").child("paket").push().setValue("1.9 Dizel")

        database.child("cars").child("markalar").child("Tofaşk").child("Şahin").child("paket").push().setValue("Gayış")
        database.child("cars").child("markalar").child("Tofaşk").child("Şahin").child("paket").push().setValue("Stok")

        database.child("cars").child("markalar").child("BMW").child("E92").child("paket").push().setValue("Ciğer Power")
        database.child("cars").child("markalar").child("BMW").child("3 20d").child("paket").push().setValue("Stok")
        database.child("cars").child("markalar").child("Reno").child("Clio").child("paket").push().setValue("Dert Paket")
        database.child("cars").child("markalar").child("Reno").child("Clio").child("paket").push().setValue("1.5 dci")
        database.child("cars").child("markalar").child("BMW").child("3 20d").child("paket").push().setValue("Stok")

 */

    fun clickYayınla(view: View) {
        if(contolIlan()) {
            if(!controlIlanFoto())
            {
                val uuid=UUID.randomUUID()
                val ilanId="$uuid"
                val myPost=Post(message,editAcıklamaText.text.toString(),editKm.text.toString(),aracRenk,editFiyat.text.toString(),aracYakıt,aracMarka,aracModel,aracPaket,editModelYılı.text.toString(),null)
                database.child("Ilanlar").child(ilanId).setValue(myPost)
                uploadimg(ilanId)
                val loadingDialog=LoadingDialog(this)
                loadingDialog.startLoading()
                val hundler=Handler()
                hundler.postDelayed(object :Runnable{
                    override fun run() {
                        loadingDialog.isDismiss()
                    }
                },3000)
            }
            else{
                Toast.makeText(this,"Lütfen Fotoğraf Ekleyiniz",Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,"Aracın Bilgilerini Eksiksiz Giriniz",Toast.LENGTH_LONG).show()
        }

    }
    fun clickFotoEkle(view: View) {
        if(contolIlan())
        {
            intent=Intent(Intent.ACTION_GET_CONTENT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            startActivityForResult(intent,PICK_IMAGE)

        }
        else{
            Toast.makeText(this,"Fotoğraf Yüklemeden Önce Aracın Bilgilerini Eksiksiz Giriniz",Toast.LENGTH_LONG).show()

        }

    }
    fun getColor() {

        var Colors=resources.getStringArray(R.array.Colors)
        var selectedRenk:String
        if(spinnerRenk!=null)
        {
            val adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,Colors)
            spinnerRenk.adapter=adapter

            spinnerRenk.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    // Toast.makeText(applicationContext,"Seçilen Durak"+duraklar[p2],Toast.LENGTH_LONG).show()
                   selectedRenk=Colors[p2]
                    aracRenk=selectedRenk
                    println("Renk"+selectedRenk)

                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(applicationContext,"Lüften Renk Seçiniz", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    fun getYakıt() {
        var Yakıt=resources.getStringArray(R.array.yakıtTürü)
        if(spinnerYakıt!=null)
        {
            val adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,Yakıt)
            spinnerYakıt.adapter=adapter

            spinnerYakıt.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    // Toast.makeText(applicationContext,"Seçilen Durak"+duraklar[p2],Toast.LENGTH_LONG).show()
                    val selectedYakıt=Yakıt[p2]
                    aracYakıt=selectedYakıt
                    println("yakıt"+selectedYakıt)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    val selectedYakıt=Yakıt[0]
                    println("yakıtnothing"+selectedYakıt)
                }
            }
        }

    }
    fun retrievemarka() {


        database.child("cars").child("markalar").addValueEventListener(object :ValueEventListener
        {


            override fun onDataChange(snapshot: DataSnapshot) {
                for (areaSnapshot in snapshot.getChildren()) {

                    val areaName = areaSnapshot.key
                    list_marka.add(areaName.toString())
                    println(areaName)

                }
                val aa = ArrayAdapter(this@Add,android.R.layout.simple_spinner_item,list_marka)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerMarka.adapter=aa
                spinnerMarka.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val zafer=aa.getItem(p2)
                        println(zafer+"secilen marka")
                        aracMarka=zafer.toString()
                        retrievemodel(zafer.toString())
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun retrievemodel(marka:String){
        database.child("cars").child("markalar").child(marka).addValueEventListener(object :ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (areaSnapshot in snapshot.getChildren()) {

                    val areaName = areaSnapshot.key
                    list_model.add(areaName.toString())
                    println(areaName)


                }
                val aa = ArrayAdapter(this@Add,android.R.layout.simple_spinner_item,list_model)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerModel.adapter=aa

                spinnerModel.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val zafer=aa.getItem(p2)
                        aracModel=zafer.toString()
                        println(zafer+"secilen model")
                        retrievepaket(marka,zafer.toString())
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }



            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        list_model.clear()
    }
    fun retrievepaket(marka:String,model :String){
        database.child("cars").child("markalar").child(marka).child(model).child("paket").addValueEventListener(object :ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (areaSnapshot in snapshot.getChildren()) {

                    val areaName = areaSnapshot.getValue()
                    list_paket.add(areaName.toString())
                    println(areaName)


                }
                val aa = ArrayAdapter(this@Add,android.R.layout.simple_spinner_item,list_paket)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerPaket.adapter=aa

                spinnerPaket.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                        val zafer=aa.getItem(p2)
                        aracPaket=zafer.toString()

                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        list_paket.clear()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE) {
            if(resultCode== RESULT_OK)
            {
                if (data != null) {
                    if(data.clipData!=null) {
                        val  countClipData= data.clipData!!.itemCount
                        var currentImageSelect=0
                        while (currentImageSelect<countClipData) {
                            ImageUri= data.clipData!!.getItemAt(currentImageSelect).uri
                            ImageList.add(ImageUri)
                            currentImageSelect += 1

                        }


                    }
                }
            }else
            {
                Toast.makeText(this,"Select Multiple Image ",Toast.LENGTH_LONG)
            }

        }
    }

    fun uploadimg(ilanid:String) {

        while (upLoadCount<ImageList.size)
        {
            val specialName:Uri=ImageList.get(upLoadCount)
            val reference=storage.reference
            val imagereference=reference.child("images").child("Ilanlar").child("Image"+specialName.lastPathSegment)

            imagereference.putFile(specialName).addOnSuccessListener{
                imagereference.downloadUrl.addOnSuccessListener {

                }.addOnSuccessListener {
                    val url=it.toString()
                    StoreLink(url,ilanid)
                }

            }
            upLoadCount += 1
        }


    }
    private fun StoreLink(url: String,ilanid: String) {

        val postmap= hashMapOf<String,Any>()
        postmap.put("ImgLink",url)

        database.child("Ilanlar").child(ilanid).child("resimler").push().setValue(postmap)

    }
    fun contolIlan(): Boolean {
        return (editModelYılı.text.isNotEmpty()&&editFiyat.text.isNotEmpty()&&editKm.text.isNotEmpty()&&!aracRenk.equals("")&&!aracYakıt.equals(""))
    }
    fun controlIlanFoto():Boolean {
        return ImageList.isEmpty()

    }
    override fun onBackPressed() {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id",message)
        startActivity(intent)
    }
    fun goHome(view: View) {
                 val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id",message)
                startActivity(intent)

    }








}




