package com.gaurav.gschat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    lateinit var  btnSignUp: Button
    lateinit var  editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        btnSignUp = findViewById(R.id.button_sign_up)
        editName = findViewById(R.id.name)
        editEmail = findViewById(R.id.signup_username)
        editPassword = findViewById(R.id.signup_password)

        btnSignUp.setOnClickListener {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            signUp(name, email, password)
        }
    }

    private fun signUp(name : String, email : String, password : String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, jump to login page
                        addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUpActivity, "Sign up failed",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name : String, email : String, uid : String) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference()
        mDatabaseRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}