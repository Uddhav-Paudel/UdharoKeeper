package com.paudeluddhav.udharokeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.udharo_activity.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UdharoActivity: AppCompatActivity(){

    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    lateinit var dbReferenceFBase: DatabaseReference
    lateinit var adapter: UdharoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //val user: UdharoListAdapter.User = intent.getParcelableExtra<UdharoListAdapter.User>("key")
        //val user = bundle.getParcelable<AddClientActivity.User>("key")
        val key = intent.getStringExtra("key")
        setContentView(R.layout.udharo_activity)

        udharo_item_list.layoutManager = LinearLayoutManager(this)


        var userId = FirebaseAuth.getInstance().currentUser?.email.toString();
        var userIdCopy = userId.substring(0, userId.indexOf("."))
        dbReferenceFBase = database.child("users").child(userIdCopy).child("Clients").
            child(key!!).child("businessEntity")
        dbReferenceFBase?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var listItems = arrayListOf<HashMap<String, String>>()
                for(p1 in p0.children){

                    var items: HashMap<String, String>
                    var description = p1.child("description").value.toString()
                    var expense = p1.child("expense").value.toString()
                    val dateCreated = p1.child("dateCreated").value.toString()
                    listItems.add(hashMapOf("description" to description, "expense" to expense, "dateCreated" to dateCreated))

                }
                adapter = UdharoListAdapter(listItems)
                udharo_item_list.adapter = adapter
            }

        })

        add_new_udharo_item.setOnClickListener {
            val intent = Intent(this, AddUdharoItem::class.java)

            intent.putExtra("key", key)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish();
        startActivity(getIntent());
    }


}