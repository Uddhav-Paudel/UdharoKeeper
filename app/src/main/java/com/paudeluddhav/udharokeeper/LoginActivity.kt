package com.paudeluddhav.udharokeeper

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.login_button.*
import java.util.concurrent.TimeUnit

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        login_button.setOnClickListener{
            val userA = FirebaseAuth.getInstance().currentUser;
            FirebaseAuth.getInstance().signInWithEmailAndPassword(login_username.text.toString(), login_pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information oks

                        val user = FirebaseAuth.getInstance().currentUser;
                        Toast.makeText(this, "Authentication succeed!!!",Toast.LENGTH_LONG).show()
                        val dashboardActivity = Intent(this, DashboardActivity::class.java)
                        startActivity(dashboardActivity);
                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(this, "Authentication failed!!!",Toast.LENGTH_LONG).show()
                    }

                    // ...
                }

        }
    }

    fun authenticateByPhone(): String{
        return "a";
    }
}