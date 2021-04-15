package com.example.a1million_android_clone

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar_back.*

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_back_button.setOnClickListener {
            finish()
        }

        //자동 로그인
        if (!MySharedPreferences.getUserMail(this)
                .isNullOrBlank() && !MySharedPreferences.getUserPwd(this).isNullOrBlank()
        ) {
            Toast.makeText(
                this,
                "${MySharedPreferences.getUserMail(this)}님 자동 로그인 되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 비밀번호 찾기
        find_pwd_text.setOnClickListener {
            val intent = Intent(this, FindPwdActivity::class.java)
            startActivity(intent)
        }

        // 이메일 입력
        login_mail_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) { // 입력을 받지 못했을시 helper 표시
                        login_mail_layout.isHelperTextEnabled = true
                        login_mail_layout.helperText = "이메일을 입력해주세요."
                    } else if (!isValidMail(s.toString())) { // 이메일 형식에 맞지 않는 입력일시 helper 표시
                        login_mail_layout.isHelperTextEnabled = true
                        login_mail_layout.helperText = "이메일 형식이 아닙니다."
                    } else { // 제대로된 입력을 받았을시 helper 삭제
                        login_mail_layout.isHelperTextEnabled = false
                    }
                }
            }
        })

        login_button.setOnClickListener { // 비밀번호 이메일이 맞다면 실행
            //이메일 비밀번호 저장

            MySharedPreferences.setUserMail(this, login_mail_input.text.toString())
            MySharedPreferences.setUserPwd(this, login_pwd_input.text.toString())
            Toast.makeText(
                this,
                "${MySharedPreferences.getUserMail(this)}님 로그인 되었습니다.",
                Toast.LENGTH_SHORT
            ).show()

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // 이메일 유효성 검사
    private fun isValidMail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}