package com.example.artificialnutritionist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.artificialnutritionist.databinding.SigninActivityBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity(){
    private lateinit var bind : SigninActivityBinding
    private lateinit var firebaseAuth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = SigninActivityBinding.inflate(layoutInflater)
        setContentView(bind.root)

        firebaseAuth = FirebaseAuth.getInstance()

        bind.textView.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        bind.button.setOnClickListener{
            val email = bind.emailEt.text.toString()
            val password = bind.passET.text.toString()
            //val welname = intent.getStringExtra("name")
            //Toast.makeText(this,welname,Toast.LENGTH_SHORT).show()
            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this,MainActivity::class.java)
                        intent.putExtra("email",email)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}