package com.example.a1million_android_clone

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar_back.*

class SignUpActivity : AppCompatActivity(), ProfileFragmentDialog.OnProfileFragmentInteractionListener,
    GenderFragmentDialog.OnGenderFragmentInteractionListener {

    var checkedName: Boolean = false
    var checkedMail: Boolean = false
    var checkedPwd: Boolean = false
    var checkedPwdConfirm: Boolean = false
    var checkedBirthdate: Boolean = false
    var checkedPhoneNumber: Boolean = false
    var checkedAddress: Boolean = false
    var checkedGender: Boolean = false
    var checkedTermsOfService: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val profileDialog = ProfileFragmentDialog()
        val genderDialog = GenderFragmentDialog()

        toolbar_back_button.setOnClickListener {
            finish()
        }

        // 성별 선택 dialog fragment
        gender_input.setOnClickListener {
            genderDialog.show(supportFragmentManager, "gender_dialog")
        }

        // 프로필 선택 dialog fragment
        profile_image.setOnClickListener {
            profileDialog.show(supportFragmentManager, "profile_dialog")
        }

        required_text_first.setOnClickListener {
            // 이용약간 화면 띄우기
            val intent = Intent(this, TermsServiceActivity::class.java)
            startActivity(intent)
        }

        required_text_third.setOnClickListener {
            // 개인정보수집이용 화면 띄우기
            val intent = Intent(this, TermsPersonActivity::class.java)
            startActivity(intent)
        }

        person_all.setOnClickListener { onCheckChanged(person_all) }
        person_option.setOnClickListener { onCheckChanged(person_option) }
        person_required.setOnClickListener { onCheckChanged(person_required) }

        // 가입하기 버튼 dialog message 띄우기
        join_button.setOnClickListener {
            if (!checkedName) {
                AlertDialog.Builder(this).setTitle("이름을 입력해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, name_input.scrollY)
                        name_input.requestFocus()
                        name_layout.isHelperTextEnabled = true
                        name_layout.helperText = "이름을 입력해주세요."
                        checkedName = false
                    }
                    .show()
            } else if (!checkedMail) {
                AlertDialog.Builder(this).setTitle("이메일을 입력해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, mail_input.scrollY)
                        mail_input.requestFocus()
                        mail_layout.isHelperTextEnabled = true
                        mail_layout.helperText = "이메일을 입력해주세요."
                        checkedMail = false
                    }
                    .show()
            } else if (!checkedPwd) {
                AlertDialog.Builder(this).setTitle("비밀번호를 입력해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, pwd_input.scrollY)
                        pwd_input.requestFocus()
                        pwd_layout.isHelperTextEnabled = true
                        pwd_layout.helperText = "비밀번호를 입력해주세요."
                        checkedPwd = false
                    }
                    .show()
            } else if (!checkedPwdConfirm) {
                AlertDialog.Builder(this).setTitle("비밀번호 확인을 입력해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, pwd_confirm_input.scrollY)
                        pwd_confirm_input.requestFocus()
                        pwd_confirm_layout.isHelperTextEnabled = true
                        pwd_confirm_layout.helperText = "비밀번호 확인을 입력해주세요."
                        checkedPwdConfirm = false
                    }
                    .show()
            } else if (!checkedBirthdate) {
                AlertDialog.Builder(this).setTitle("생년월일을 입력해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, birthdate_input.scrollY)
                        birthdate_input.requestFocus()
                        birthdate_layout.isHelperTextEnabled = true
                        birthdate_layout.helperText = "생년월일을 입력해주세요."
                        checkedBirthdate = false
                    }
                    .show()
            } else if (!checkedPhoneNumber) {
                AlertDialog.Builder(this).setTitle("연락처를 입력해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, phone_number_input.scrollY)
                        phone_number_input.requestFocus()
                        phone_number_layout.isHelperTextEnabled = true
                        phone_number_layout.helperText = "연락처를 입력해주세요."
                        checkedPhoneNumber = false
                    }
                    .show()
            } else if (!checkedAddress) {
                AlertDialog.Builder(this).setTitle("주소를 입력해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, address_input.scrollY)
                        address_input.requestFocus()
                        address_layout.isHelperTextEnabled = true
                        address_layout.helperText = "주소를 입력해주세요."
                        checkedAddress = false
                    }
                    .show()
            } else if (!checkedGender) {
                AlertDialog.Builder(this).setTitle("성별을 선택해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, gender_input.scrollY)
                        gender_layout.isHelperTextEnabled = true
                        gender_layout.helperText = "성별을 선택해주세요."
                        checkedGender = false
                    }
                    .show()
            } else if (!checkedTermsOfService) {
                AlertDialog.Builder(this).setTitle("필수 이용약관 및 개인정보수집이용에 동의해주세요.")
                    .setNeutralButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        scrollView.smoothScrollTo(0, person_required.scrollY)
                        checkedTermsOfService = false
                    }
                    .show()
            } else { // 가입

            }
        }

        // 이름 입력
        name_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) { // 입력 받지 못했을시 helper 보여줌
                        name_layout.isHelperTextEnabled = true
                        name_layout.helperText = "이름을 입력해주세요."
                        checkedName = false
                    } else { // 입력 받았을시 helper 삭제
                        name_layout.isHelperTextEnabled = false
                        checkedName = true
                    }
                }
            }
        })

        // 이메일 입력
        mail_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) { // 입력을 받지 못했을시 helper 표시
                        mail_layout.isHelperTextEnabled = true
                        mail_layout.helperText = "이메일을 입력해주세요."
                        checkedMail = false
                    } else if (!isValidMail(s.toString())) { // 이메일 형식에 맞지 않는 입력일시 helper 표시
                        mail_layout.isHelperTextEnabled = true
                        mail_layout.helperText = "이메일 형식이 아닙니다."
                        checkedMail = false
                    } else { // 제대로된 입력을 받았을시 helper 삭제
                        mail_layout.isHelperTextEnabled = false
                        checkedMail = true
                    }
                }
            }
        })

        // 비밀번호 입력
        pwd_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) { // 입력을 받지 못했을시 helper 표시
                        pwd_layout.isHelperTextEnabled = true
                        pwd_layout.helperText = "비밀번호를 입력해주세요."
                        checkedPwd = false
                    } else if (!isValidPwd(s.toString())) { // 이메일 형식에 맞지 않는 입력일시 helper 표시
                        pwd_layout.isHelperTextEnabled = true
                        pwd_layout.helperText = "비밀번호 형식이 아닙니다."
                        checkedPwd = false
                    } else { // 제대로된 입력을 받았을시 helper 삭제
                        pwd_layout.isHelperTextEnabled = false
                        checkedPwd = true
                    }
                }
            }
        })

        // 비밀번호 확인 입력
        pwd_confirm_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) { // 새비밀번호 입력을 안받았을시 helepr 표시
                        pwd_confirm_layout.isHelperTextEnabled = true
                        pwd_confirm_layout.helperText = "새 비밀번호 확인을 입력해주세요."
                        checkedPwdConfirm = false
                    } else if (pwd_input.length() == 0) { // 비밀번호 입력을 안받았을시 helper 표시
                        pwd_confirm_layout.isHelperTextEnabled = true
                        pwd_confirm_layout.helperText = "비밀번호를 입력해주세요."
                        checkedPwdConfirm = false
                    } else if (!isValidPwdConfirm(s.toString())) { // 비밀번호와 새 비밀번호가 같지 않을시 helper 표시
                        pwd_confirm_layout.isHelperTextEnabled = true
                        pwd_confirm_layout.helperText = "새비밀번호와 일치하지 않습니다."
                        checkedPwdConfirm = false
                    } else { // 비밀번호랑 같은 입력을 받았을시 helper 삭제
                        pwd_confirm_layout.isHelperTextEnabled = false
                        checkedPwdConfirm = true
                    }
                }
            }
        })

        // 생년월일 입력
        birthdate_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (!isValidBirthdate(s.toString())) {
                        birthdate_layout.isHelperTextEnabled = true
                        birthdate_layout.helperText = "생년월일을 입력해주세요."
                        checkedBirthdate = false
                    } else {
                        birthdate_layout.isHelperTextEnabled = false
                        checkedBirthdate = true
                    }
                }
            }
        })

        // 연락처 입력
        phone_number_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) {
                        phone_number_layout.isHelperTextEnabled = true
                        phone_number_layout.helperText = "연락처를 입력해주세요."
                        checkedPhoneNumber = false
                    } else {
                        phone_number_layout.isHelperTextEnabled = false
                        checkedPhoneNumber = true
                    }
                }
            }
        })

        // 주소 입력
        address_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) {
                        address_layout.isHelperTextEnabled = true
                        address_layout.helperText = "주소를 입력해주세요."
                        checkedAddress = false
                    } else {
                        address_layout.isHelperTextEnabled = false
                        checkedAddress = true
                    }
                }
            }
        })

        // 국적 바꾸기
        nation_text.setText(nation_ccp.selectedCountryName)
        nation_ccp.setOnCountryChangeListener {
            nation_text.setText(nation_ccp.selectedCountryName)
        }

        // 전화번호 바꾸기
        phone_number_layout.prefixText = "+" + ccp.selectedCountryCode
        ccp.setOnCountryChangeListener {
            phone_number_layout.prefixText = "+" + ccp.selectedCountryCode
        }

    }

    // 생년월일 유효성 검사
    private fun isValidBirthdate(birthdate: String): Boolean {

        val birthdateReg = Regex("^([1-2][0-9]{3}[0-1][0-9][0-3][0-9])$")
        return birthdate.matches(birthdateReg)

        /*
        val currentDateTime = Calendar.getInstance().time
        val currentDateYear = SimpleDateFormat("yyyy", Locale.KOREA).format(currentDateTime)
        val validBirthdate = SimpleDateFormat("yyyyMMdd", Locale.KOREA)


        if (birthdate.length == 4) {
            if (birthdate.toInt() > currentDateYear.toInt()) {
                AlertDialog.Builder(this).setTitle("잘못 입력하였습니다.") //제목
                    .setNeutralButton("확인", { dialogInterface: DialogInterface, i: Int ->
                        birthdate_input.setText(birthdate.dropLast(1))
                    })
                    .show()

                // dialog 메세지 띄우기 및 하나 삭제
            } else {
                Log.d("asdf","3333")
                // 뒤에 - 붙이
                birthdate_input.setText(birthdate + "-")
            }
        }
         */
    }

    // 새비밀번호와 비밀번호가 같은지 검사
    private fun isValidPwdConfirm(pwdConfirm: String): Boolean {
        return pwdConfirm == pwd_input.text.toString()
    }

    // 비밀번호 유효성 검사 숫자, 문자가 있는지 8글자 이상인지
    private fun isValidPwd(pwd: String): Boolean {

        val regexLetter = "\\w+[a-zA-Z]\\w+" // 문자 정규식
        val regexDigit = "\\w+[0-9]\\w+" // 숫자 정규식

        return regexLetter.toRegex().matches(pwd) && regexDigit.toRegex().matches(pwd) && pwd.length >= 8
    }

    // 이메일 유효성 검사
    private fun isValidMail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    // 이용약관 선택, 필수 all 처리 이벤트
    private fun onCheckChanged(compoundButton: CompoundButton) {
        when (compoundButton.id) {
            //all 선택시
            R.id.person_all -> {
                // all ture 일시 모두 tre
                if (person_all.isChecked) {
                    person_all.isChecked = true
                    person_option.isChecked = true
                    person_required.isChecked = true
                    checkedTermsOfService = true
                } else { // all 해제시 모두 false
                    person_all.isChecked = false
                    person_option.isChecked = false
                    person_required.isChecked = false
                    checkedTermsOfService = false
                }
            }
            else -> { // 선택과 옵션 두개 모두 선택했을시 all true
                person_all.isChecked = (person_option.isChecked && person_required.isChecked)
                checkedTermsOfService = person_required.isChecked
            }

        }
    }

    // 프로필 사진 추가
    override fun onProfileFragmentInteraction(msg: Uri) {
        Glide.with(this).load(msg).apply(RequestOptions().circleCrop()).into(profile_image)
    }

    // 성별 선택 추가
    override fun onGenderFragmentInteraction(msg: String) {
        gender_input.setText(msg)
        gender_layout.isHelperTextEnabled = false
        checkedGender = true
    }
}