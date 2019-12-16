package com.paudeluddhav.udharokeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dashboard_activity.*
import org.json.JSONArray
import java.lang.Exception


class DashboardActivity: AppCompatActivity() {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val currentUser = FirebaseAuth.getInstance().currentUser
    private var listView = ArrayList<UdharoListAdapter.User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        progress_bar.setVisibility(View.VISIBLE)
        lvToDoList.layoutManager = LinearLayoutManager(this)


        add_new_user.setOnClickListener {
            val addClientActivity: Intent = Intent(this, AddClientActivity::class.java)
            startActivity(addClientActivity)
        }
        var userId = FirebaseAuth.getInstance().currentUser?.email.toString();
        var userIdCopy = userId.substring(0, userId.indexOf("."))
        var dbReferenceFBase: DatabaseReference = database.child("users").child(userIdCopy).child("Clients")

        dbReferenceFBase?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(p1 in p0.children){
                    var clientName = p1.child("name").value.toString()
                    var clientAddress = p1.child("address").value.toString()
                    var clientEmail = p1.child("email").value.toString()
                    var clientContact = p1.child("contact").value.toString()
                    var clientRelation = p1.child("relation").value.toString()
                    var totalAmount: Long? = 0;
                    var businessEntity: ArrayList<HashMap<String, Any>>? = null
                    businessEntity = p1.child("businessEntity")?.value as ArrayList<HashMap<String, Any>>?
                    totalAmount = p1.child("totalBusinessAmount")?.value as Long?
                    var keyChild = p1.key.toString()
                    Log.d("Key", keyChild)
                    var clientInfo: UdharoListAdapter.User =
                        UdharoListAdapter.User(keyChild, clientName, clientEmail, clientContact, clientAddress,
                            totalAmount, totalAmount,totalAmount, clientRelation, businessEntity, businessEntity,businessEntity )
                    listView.add(clientInfo)
                    Log.d("yes", "No")
                }
                lvToDoList.adapter = ClientViewAdapter(listView)
                progress_bar.setVisibility(View.GONE)

            }

        })
    }



    override fun onRestart() {
        super.onRestart()
        finish();
        startActivity(getIntent());
    }


}