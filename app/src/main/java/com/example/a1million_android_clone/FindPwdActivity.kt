package com.example.a1million_android_clone

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_find_pwd.*
import kotlinx.android.synthetic.main.toolbar_back.*

class FindPwdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pwd)

        toolbar_back_button.setOnClickListener {
            finish()
        }

        find_pwd_mail_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (!isValidMail(s.toString())) {
                        find_pwd_button.isEnabled = false
                        find_pwd_button.setBackgroundColor(Color.GRAY)
                    } else {
                        find_pwd_button.isEnabled = true
                        find_pwd_button.setBackgroundColor(Color.BLACK)
                    }
                }
            }
        })

        find_pwd_button.setOnClickListener {
            // 메일로 변경된 비밀번호 보내주기
        }
    }

    // 이메일 유효성 검사
    private fun isValidMail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}