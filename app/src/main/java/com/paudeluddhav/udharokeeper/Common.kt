package com.paudeluddhav.udharokeeper

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

fun goToLoginPage(context: Context){
    val loginIntent = Intent(context.applicationContext, LoginActivity::class.java)
    startActivity(context, loginIntent,null)
}