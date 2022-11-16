package com.example.artificialnutritionist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.artificialnutritionist.databinding.ResultActivityBinding
import com.google.firebase.auth.FirebaseAuth

class ResultActivity :AppCompatActivity(){
    private lateinit var bind : ResultActivityBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ResultActivityBinding.inflate(layoutInflater)
        setContentView(bind.root)

        firebaseAuth = FirebaseAuth.getInstance()

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

        predictDisease()
    }

    private fun predictDisease(){
        val checkButton = bind.radioGroup.checkedRadioButtonId
        val food = when(checkButton){
            R.id.radioButton -> "Suggested diet : Meat, Carrot, Papaya"
            R.id.radioButton2 -> "Suggested diet : Capsicum, Grains, Almonds"
            R.id.radioButton3 -> "Suggested diet : MilkProducts, Apple, Grapes"
            R.id.radioButton4 -> "Suggested diet : Salmon, Mushroom, Egg"
            R.id.radioButton5 -> "Suggested diet : Tomato, Kiwifruit, Broccoli"
            R.id.radioButton6 -> "Suggested diet : Yoghurt, Liver oil, Beef liver"
            R.id.radioButton7 -> "Suggested diet : Ladysfinger, Cucumber, Greens"
            R.id.radioButton8 -> "You are fine👌"
            else -> "Suggested diet : "
        }
        val disease = when(checkButton){
            R.id.radioButton -> "Night-blindness"
            R.id.radioButton2 -> "Beri-beri"
            R.id.radioButton3 -> "Retarded Growth"
            R.id.radioButton4 -> "Anaemia"
            R.id.radioButton5 -> "Scurvy"
            R.id.radioButton6 -> "Rickets"
            R.id.radioButton7 -> "Excessive bleeding due to injury"
            R.id.radioButton8 -> "You are fine👌"
            else -> "You are fine..."
        }

        val resultDialog = AlertDialog.Builder(this)
            .setTitle("Disease you have")
            .setMessage(disease)
            .setPositiveButton("Ok"){ dialog, _ ->
                dialog.cancel()
            }.create()

        if(disease=="You are fine..." || disease=="You are fine👌"){
            resultDialog.cancel()
        }else {
            resultDialog.show()
        }

        bind.tvFood.text = food
        bind.btnYes.setOnClickListener{
            predictDisease()
        }
        bind.btnNo.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
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