package com.example.firsthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    var db: FirebaseFirestore?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Firebase.firestore



        btn_save.setOnClickListener {
            var name =etName.text.toString()
            var phone = etNumber.text.toString()
            var address = etAddress.text.toString()
            var id = UUID.randomUUID().toString()
            addData(id,name,phone,address)
            etName.text.clear()
            etNumber.text.clear()
            etAddress.text.clear()
        }

        btn_get.setOnClickListener {
            var i = Intent(this,UsersActivity::class.java)
            startActivity(i)
        }


    }
    private fun addData(id : String,name:String,phone:String,address:String){
        var user = hashMapOf("id" to id,"name" to name,"phone" to phone,"address" to address)
        db!!.collection("users").add(user).addOnSuccessListener {documentReference ->
            Log.e("TAG","add successfully")

        }.addOnFailureListener { exception ->
            Log.e("TAG","add Failed $exception")
        }
    }



}
