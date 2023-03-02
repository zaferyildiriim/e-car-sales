package com.example.myapplication2


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_person.*


class PersonFragment : Fragment() {
    private var message = "hello"

    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cars-56ba1-default-rtdb.firebaseio.com/")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        return inflater.inflate(R.layout.fragment_person, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        database.child("users").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val activity: Person? = activity as Person?
                val myDataFromActivity = activity!!.getMyData()
                message=myDataFromActivity.toString()
                if (snapshot.hasChild(myDataFromActivity.toString())) {
                    val getPassword = snapshot.child(myDataFromActivity.toString()).child("password").getValue()
                     val  getName=snapshot.child(myDataFromActivity.toString()).child("fullname").getValue()
                     val getMail=snapshot.child(myDataFromActivity.toString()).child("email").getValue()
                    writePhone.text=myDataFromActivity.toString()
                    writeMail.text=getMail.toString()
                    writeName.text=getName.toString()
                    writePassword.text=getPassword.toString()
                }
            }



            override fun onCancelled(error: DatabaseError) {
                println(error.message)

            }

        })
        exit_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
            activity?.finish()
        }
        bilgi_düzenle.setOnClickListener {
            val action =PersonFragmentDirections.actionPersonFragmentToSetInfo(message.toString())
            Navigation.findNavController(it).navigate(action)

        }

    }

}



private fun Any.actionPersonFragmentToSetInfo(toString: String): String {
    return toString
}

