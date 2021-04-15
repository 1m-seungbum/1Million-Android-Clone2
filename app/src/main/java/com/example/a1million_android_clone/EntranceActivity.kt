package com.example.a1million_android_clone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_entrance.*

class EntranceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)

        entrance_login.setOnClickListener {
            // 페이지 이동
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        entrance_signup.setOnClickListener {
            // 페이지 이동
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}