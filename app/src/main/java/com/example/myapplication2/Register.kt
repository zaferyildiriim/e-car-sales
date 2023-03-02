package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cars-56ba1-default-rtdb.firebaseio.com/")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val intent=intent
        intent.getStringExtra("zafer")

//        loginButton.setOnClickListener {
//            finish()
//        }
        val id=1
       // val mycar=Car(id,"vw","passat",2020,)

        register_save_button.setOnClickListener {

            val phone=registerPhone.text.toString()
            val password=registerPassword.text.toString()
            val password2=registerAgainPassword.text.toString()
            val mail=registerEmail.text.toString()
            val name=registerName.text.toString()

            if(phone.equals("")||password.equals("")||password2.equals("")||mail.equals("")||name.equals(""))
            {
                Toast.makeText(this,"Check all data ", Toast.LENGTH_LONG).show()
            }
            else if (!password.equals(password2))
            {
                Toast.makeText(this,"Passwords dont match",Toast.LENGTH_SHORT).show()
            }
            else{
                database.child("users").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(phone))
                        {
                            Toast.makeText(applicationContext,"Telefon Numarası Zaten Kayıtlı",Toast.LENGTH_LONG).show()
                        }
                        else
                        {
                            database.child("users").child(phone).child("fullname").setValue(name)
                            database.child("ozan").child("lhkşgj").setValue("123")
                            database.child("users").child(phone).child("password").setValue(password)
                            database.child("users").child(phone).child("email").setValue(mail)
                            //database.child("cars").child(id.toString()).setValue(mycar)
                            Toast.makeText(applicationContext,"Kayıt Başarılı  ", Toast.LENGTH_LONG).show()

                            finish()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }


                })













            }

        }
    }
}