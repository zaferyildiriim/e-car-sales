package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cars-56ba1-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {

            val phone=editTextPhone.text.toString()

            val password=editTextTextPassword.text.toString()

            if(phone.equals("")||password.equals(""))
            {
                Toast.makeText(this,"Enter phone and password", Toast.LENGTH_LONG).show()
            }
            else{
                database.child("users").addListenerForSingleValueEvent(object :ValueEventListener
                {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(phone))
                        {
                            val  getPassword=snapshot.child(phone).child("password").getValue()

                            if (getPassword != null) {
                                if(getPassword.equals(password))
                                {
                                    Toast.makeText(applicationContext,"Giriş Başarılı",Toast.LENGTH_LONG).show()
                                   // val zafer=snapshot.child(phone).child("email").getValue()
                                   // println(zafer)
                                    val loginToMainintent=Intent(applicationContext,MainActivity::class.java)
                                    loginToMainintent.putExtra("id",phone)
                                    startActivity(loginToMainintent)
                                    finish()
                                }
                                else
                                {


                                    Toast.makeText(applicationContext,"Yanlış Şife Girişi",Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(applicationContext,"Numara Kayıtlı Değil",Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            }
        }


        registerTextButton.setOnClickListener {

            val intent=Intent(applicationContext,Register::class.java)
            startActivity(intent)
        }

    }
}