package com.paudeluddhav.udharokeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.add_udharo_item.*
import kotlinx.android.synthetic.main.description_udharo_item.*

class AddUdharoItem: AppCompatActivity(){
    lateinit var listItems: ArrayList<HashMap<String, Any>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_udharo_item)

        var key = intent.getStringExtra("key")

        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        var userId = FirebaseAuth.getInstance().currentUser?.email.toString();
        var userIdCopy = userId.substring(0, userId.indexOf("."))

        var dbReferenceFBase: DatabaseReference = database.child("users").child(userIdCopy).
            child("Clients").child(key!!).child("businessEntity")
        dbReferenceFBase.addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    listItems = arrayListOf()
                    for(p1 in p0.children){
                        var items: HashMap<String, String>
                        var description = p1.child("description").value.toString()
                        var expense = p1.child("expense").value.toString()
                        val dateCreated = p1.child("dateCreated").value.toString()
                        listItems.add(hashMapOf("description" to description, "expense" to expense, "dateCreated" to dateCreated))

                    }
                }

            }
        )

        add_udharo_item_btn.setOnClickListener {
            var description = description_edit_udharo_item.text.toString()
            var price = (add_udharo_item_price.text.toString())?.toLong()
            var dateCreated = System.currentTimeMillis().toString()
            var mappedData = hashMapOf<String, Any>("description" to description, "expense" to price, "dateCreated" to dateCreated)
            listItems.add(mappedData)
            dbReferenceFBase.setValue(listItems)
            Log.d("Test", "Testing done...")
            finish()
        }

    }
}