package com.example.artificialnutritionist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.artificialnutritionist.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var bind : ActivityMainBinding
    private  lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var toggle :ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        toggle = ActionBarDrawerToggle(this,bind.drawerLayout,R.string.open,R.string.close)
        bind.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bind.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miSignout -> {
                    firebaseAuth.signOut()
                    val intent = Intent(this,SignUpActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
        
        val email=firebaseAuth.currentUser?.email
        db.collection("USERS").document(email.toString()).get().addOnSuccessListener { task ->
            val name = task.get("Name").toString()
            bind.tvWelcome.text = "Hey, $name"
        }

        bind.btnSymptom.setOnClickListener{
            val intent = Intent(this,ResultActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}