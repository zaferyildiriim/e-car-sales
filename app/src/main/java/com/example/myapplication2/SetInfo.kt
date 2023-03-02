package com.example.myapplication2

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_set__info.*
var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cars-56ba1-default-rtdb.firebaseio.com/")

class SetInfo : Fragment() {
    private var name = "hello"
    private var mail = "hello"
    private var pass=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set__info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       arguments?.let {
           val id=SetInfoArgs.fromBundle(it).userid
           println("sooooo"+id)

          database.child("users").addListenerForSingleValueEvent(object :ValueEventListener{
              override fun onDataChange(snapshot: DataSnapshot) {
                  val  getName=snapshot.child(id.toString()).child("fullname").getValue().toString()
                  val  getPassword=snapshot.child(id.toString()).child("password").getValue().toString()
                  val  getMail=snapshot.child(id.toString()).child("email").getValue().toString()
                  editTextTextPersonName.hint=getName
                  editTextTextMail.hint=getMail
                  name=getName
                  mail=getMail
                  pass=getPassword

              }
              override fun onCancelled(error: DatabaseError) {

              }
          })


           adDegistirbtn.setOnClickListener {
               val newname=editTextTextPersonName.text.toString()
               if(newname.equals(""))
               {
                   Toast.makeText(requireContext(),"Ad Soyad Boş bırakılamaz",Toast.LENGTH_SHORT).show()
                   editTextTextPersonName.text.clear()


               }
               else{
                   database.child("users").child(id).child("fullname").setValue(newname)
                   Toast.makeText(requireContext(),"Ad Soyad Değitirildi",Toast.LENGTH_SHORT).show()
                   editTextTextPersonName.text.clear()
                   editTextTextPersonName.hint=newname

               }


           }
           emailKaydet.setOnClickListener {

               val newmail=editTextTextMail.text.toString()
               if(newmail.equals(""))
               {
                   Toast.makeText(requireContext(),"Mail Boş bırakılamaz",Toast.LENGTH_SHORT).show()
                   editTextTextMail.text.clear()

               }
               else
               {
                   database.child("users").child(id).child("email").setValue(newmail)
                   Toast.makeText(requireContext(),"Mail Değitirildi",Toast.LENGTH_SHORT).show()

                   editTextTextMail.hint=newmail
                   editTextTextMail.text.clear()

               }
           }
           passwordKaydet2.setOnClickListener {
               val newPassword=editPassword.text.toString()
               val newPassword2=editPassword2.text.toString()
               if(newPassword.equals("")||newPassword2.equals(""))
               {
                   Toast.makeText(requireContext(),"Şifre Boş bırakılamaz",Toast.LENGTH_SHORT).show()

               }
               else if(!newPassword.equals(newPassword2))
               {
                   Toast.makeText(requireContext(),"Şifreler Aynı Olmalıdır",Toast.LENGTH_SHORT).show()
                   editPassword.text.clear()
                   editPassword2.text.clear()

               }
               else if(newPassword.equals(pass)||newPassword2.equals(pass))
               {
                   Toast.makeText(requireContext(),"Yeni Şifre Eskisiyle Aynı Olamaz",Toast.LENGTH_SHORT).show()
                   editPassword.text.clear()
                   editPassword2.text.clear()

               }
               else
               {
                   database.child("users").child(id).child("password").setValue(newPassword)
                   Toast.makeText(requireContext(),"Şifre Değitirildi",Toast.LENGTH_SHORT).show()
                   editPassword.text.clear()
                   editPassword2.text.clear()



               }


           }
           change_ok.setOnClickListener {

               val action=SetInfoDirections.actionSetInfoToPersonFragment()
               Navigation.findNavController(it).navigate(action)
           }


       }

    }


}