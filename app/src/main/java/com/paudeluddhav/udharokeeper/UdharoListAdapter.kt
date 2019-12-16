package com.paudeluddhav.udharokeeper

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.udharo_item.view.*
import java.util.zip.Inflater

class UdharoListAdapter(items: ArrayList<HashMap<String, String>>): RecyclerView.Adapter<UdharoListAdapter.UdharoListViewHolder>(){

    var items = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UdharoListViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        var cellRow = layoutInflater.inflate(R.layout.udharo_item, parent, false)
        return UdharoListViewHolder(cellRow)
    }

    override fun getItemCount(): Int {
        if(items == null){
            items = ArrayList();
        }
        return items!!.size
    }

    override fun onBindViewHolder(holder: UdharoListViewHolder, position: Int) {
        var business = items?.get(position)
        if(business != null){
            holder.view.udharo_item_date.text = business?.get("dateCreated") as String?
            holder.view.udharo_item_description.text = business?.get("description") as String
            holder.view.udharo_item_price.text = business?.get("expense") as String
        }
    }
    inner class UdharoListViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    }

    data class User(
        var key: String? = "",
        var name: String? = "",
        var email: String? = "",
        var contact: String? = "",
        var address: String? = "",
        var totalCreditAmount: Long? = 0,
        var totalDebitAmount: Long? = 0,
        var totalBusinessAmount: Long? = 0,
        var relation: String? = "Creditor",
        var credits: ArrayList<HashMap<String, Any>>? = null,
        var debits: ArrayList<HashMap<String, Any>>? = null,
        var businessEntity: ArrayList<HashMap<String, Any>>? = null
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readArrayList(ArrayList::class.java.classLoader) as? ArrayList<HashMap<String, Any>>?,
            parcel.readArrayList(ArrayList::class.java.classLoader) as? ArrayList<HashMap<String, Any>>?,
            parcel.readArrayList(ArrayList::class.java.classLoader) as? ArrayList<HashMap<String, Any>>?
        ) {
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(key)
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
            return 0
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
}































