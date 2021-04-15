package com.example.a1million_android_clone


import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {

    private val MY_ACCOUNT: String = "account"

    //이메일 저장
    fun setUserMail(context: Context, input: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.commit()
    }

    //이메일 get
    fun getUserMail(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }

    //비밀번호 저장
    fun setUserPwd(context: Context, input: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PASS", input)
        editor.commit()
    }

    //비밀번호 get
    fun getUserPwd(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PASS", "").toString()
    }

    //로그아웃 할시 저장해둔 이메일 비밀번호 clear
    fun clearUser(context: Context) { // 로그아웃하는거라 생각
        val prefs: SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

}