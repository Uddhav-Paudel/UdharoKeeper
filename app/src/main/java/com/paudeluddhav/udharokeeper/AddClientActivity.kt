package com.paudeluddhav.udharokeeper

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.add_client.*
import kotlinx.android.synthetic.main.address_client.*
import kotlinx.android.synthetic.main.business_relation.*
import kotlinx.android.synthetic.main.contact_client.*
import kotlinx.android.synthetic.main.email.*
import kotlinx.android.synthetic.main.fullname_client.*
import java.lang.Exception


class AddClientActivity: AppCompatActivity() {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_client)

        add_client_user.setOnClickListener {

            if (currentUser != null) {
                writeNewUser(currentUser.email.toString())
            }
            finish()
        }
    }

    data class CreditAmount(
        var Description: String? = "",
        var Price: String?=""

    )

    @IgnoreExtraProperties
    data class User(
        var name: String? = "",
        var email: String? = "",
        var contact: String? = "",
        var address: String? = "",
        var totalCreditAmount: Long? = 0,
        var totalDebitAmount: Long? = 0,
        var totalBusinessAmount: Long? = 0,
        var relation: String? = "Creditor",
        var credits: ArrayList<CreditDetail>? = null,
        var debits: ArrayList<CreditDetail>? = null,
        var businessEntity: ArrayList<CreditDetail>? = null
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString() as String?,
            parcel.readString() as String?,
            parcel.readString() as String?,
            parcel.readString() as String?,
            parcel.readValue(Long::class.java.classLoader) as? Long?,
            parcel.readValue(Long::class.java.classLoader) as? Long?,
            parcel.readValue(Long::class.java.classLoader) as? Long?,
            parcel.readString() as String?,
            parcel.readArrayList(ArrayList::class.java.classLoader) as? ArrayList<CreditDetail>?,
            parcel.readArrayList(ArrayList::class.java.classLoader) as? ArrayList<CreditDetail>?,
            parcel.readArrayList(ArrayList::class.java.classLoader) as? ArrayList<CreditDetail>?
            /*TODO("credits"),
            TODO("debits"),
            TODO("businessEntity")*/
        ) {
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(name)
            dest?.writeString(email)
            dest?.writeString(contact)
            dest?.writeString(address)
            dest?.writeValue(totalCreditAmount)
            dest?.writeValue(totalDebitAmount)
            dest?.writeValue(totalBusinessAmount)
            dest?.writeString(relation)
            dest?.writeList(credits as List<*>?)
            dest?.writeList((debits as List<*>?))
            dest?.writeList(businessEntity as List<*>?)
        }

        override fun describeContents(): Int {
            return 0;
        }

        companion object CREATOR : Parcelable.Creator<User> {
            override fun createFromParcel(parcel: Parcel): User {
                return User(parcel)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class CreditDetail(
        var description: String? = "",
        var expense: Long? = 0,
        var dateCreated: String? = ""
    )
    private fun writeNewUser(userId: String) {

        val name = full_name_client?.text.toString()
        var email = email.text?.toString()
        email = email?.replace(".", "dot")
        val contact = client_contact.text?.toString()
        val address = address_client.text?.toString()
        //var creditMap: Map<String, CreditDetail>? = mapOf("SampleDate" to CreditDetail())
        var timeInMilliseconds = System.currentTimeMillis().toString()
        var bEntity = arrayListOf<CreditDetail>()

        var relation = business_relation.selectedItem.toString()

        val user = User(name, email,contact, address, 0,0, 0,
            relation, bEntity, bEntity, bEntity)
        var userIdCopy = userId;
        userIdCopy = userId.substring(0, userId.indexOf("."))
        if (userIdCopy != null && email != null) {
            try {
                var dbReferenceFBase: DatabaseReference = database.child("users").child(userIdCopy).child("Clients")
                dbReferenceFBase.child(email).setValue(user)
               Log.d("users", "found...")
            }catch (e: Exception){
                Log.d("Test", " Message is : " + e.message)
            }
        }
    }
}