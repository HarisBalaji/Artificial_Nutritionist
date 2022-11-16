package com.example.artificialnutritionist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.artificialnutritionist.databinding.SignupActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity(){
    private lateinit var bind : SignupActivityBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = SignupActivityBinding.inflate(layoutInflater)
        setContentView(bind.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        bind.textView.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        bind.button.setOnClickListener{
            val email = bind.emailEt.text.toString()
            val password = bind.passET.text.toString()
            val confirmPass = bind.confirmPassEt.text.toString()
            val etName = bind.nameET.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()){
                if(password.equals(confirmPass)) {

                    val user = hashMapOf("Name" to etName)
                    val users = db.collection("USERS")

                    val query = users.whereEqualTo("email", email).get().addOnSuccessListener {
                   if(it.isEmpty) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    users.document(email).set(user)
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("email", email)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                    }else
                    {
                        Toast.makeText(this,"User Already Registered", Toast.LENGTH_LONG).show()
                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                   }
                }else{
                    Toast.makeText(this,"Password doesn't match",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Empty fields are not allowed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}