package com.example.firsthome

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.user_item.view.*

class UsersActivity : AppCompatActivity() {
    var db: FirebaseFirestore?=null
    var adapter:FirestoreRecyclerAdapter<User,UserViewHolder>?=null
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        db = Firebase.firestore
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        getAllUser()


    }
    fun getAllUser(){

        val query = db!!.collection("users")
        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query,User::class.java).build()

        adapter = object :FirestoreRecyclerAdapter<User,UserViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                val view = LayoutInflater.from(this@UsersActivity).inflate(R.layout.user_item,parent,false)
                progressDialog.dismiss()
                return UserViewHolder(view)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                holder.name.text = model.name
                holder.phone.text = model.phone
                holder.address.text = model.address


            }
        }

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

    }


    class UserViewHolder(view:View):RecyclerView.ViewHolder(view){
        var name = view.name
        var phone = view.phone
        var address = view.address
    }
    fun deleteUser(user: User){
        var alertDialog= AlertDialog.Builder(this@UsersActivity)
        alertDialog.setTitle("Delete")
        alertDialog.setMessage("Are you sure?")
        alertDialog.setCancelable(false)
        alertDialog.setIcon(R.drawable.ic_baseline_delete_24)
        alertDialog.setPositiveButton("Yes") { dialogInterface, i ->
            db!!.collection("users").whereEqualTo("id",user.id)
                .get()
                .addOnSuccessListener { querySnapshot ->
                }
        }

        alertDialog.setNegativeButton("No") { dialogInterface, i ->
            Toast.makeText(applicationContext, "No", Toast.LENGTH_SHORT).show()
        }

        alertDialog.setNeutralButton("Cancel") { dialogInterface, i ->
            Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_SHORT).show()
        }

        alertDialog.create().show()
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()

    }
}