package com.paudeluddhav.udharokeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.email.*
import kotlinx.android.synthetic.main.login_button.*
import kotlinx.android.synthetic.main.password.*
import kotlinx.android.synthetic.main.register_activity.*
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*FirebaseDatabase.getInstance().setPersistenceEnabled(true)*/


        if(isUserSignedIn()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
            val dashBoardIntent = Intent(this, DashboardActivity::class.java)
            startActivity(dashBoardIntent)
        }
        setContentView(R.layout.register_activity)
        register_2_login.setOnClickListener {
            goToLoginPage(this)
        }
        register_user.setOnClickListener {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Successfull!!!", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "Failed!!!", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }


    fun openLoginPage(){
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    fun isUserSignedIn(): Boolean{
        val isUserSignedInFlag: Boolean = FirebaseAuth.getInstance().currentUser != null;
        return isUserSignedInFlag;
    }
}
