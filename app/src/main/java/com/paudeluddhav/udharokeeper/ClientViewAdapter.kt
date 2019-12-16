package com.paudeluddhav.udharokeeper

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.client_list_item.view.*

class ClientViewAdapter constructor (items: ArrayList<UdharoListAdapter.User>): RecyclerView.Adapter<ClientViewAdapter.CustomViewHolder>(){
    var items = items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        var cellForRow = layoutInflater.inflate(R.layout.client_list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var item = items[position]
        holder?.view?.client_item_name?.text = item.name
        holder?.view?.client_item_address?.text = item.address
        holder?.view?.client_item_contact?.text = item.contact
    }
    inner class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

        init {
            view.setOnClickListener {

                val intent = Intent(view.context, UdharoActivity::class.java)
                val bundle = Bundle()
                var item = items[adapterPosition]
                intent.putExtra("key", item.key)
                view.context.startActivity(intent)
            }
        }
    }
}

