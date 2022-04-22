package com.example.moexfilm.views

import android.content.Intent
import android.os.Bundle
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ActivityLoginBinding
import com.example.moexfilm.util.StringUtil

class LoginActivity : AuthActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initGoogleSignInClient()
        initSignInObserver()
        setListener()
    }

    private fun initEmailPassword() {
        email = binding.tfEmail.text.toString()
        password = binding.tfPassword.text.toString()
    }

    private fun setListener() {
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnSignin.setOnClickListener {
            initEmailPassword()
            setErrorFormatEmail(false)
            if (validateNoEmptyFields() && StringUtil.validateEmailFormat(email)){ setErrorFormatEmail(true);signinWithEmailPassword(email, password)}
        }
        binding.btnSigninGoogle.setOnClickListener {signInGoogle()}
    }

    private fun validateNoEmptyFields():Boolean{
        var validate = true
        if(binding.tfEmail.text.toString().isEmpty()){
            validate = false
            binding.textInEmail.error = getString(R.string.fieldEmpty_error)
        }

        if(binding.tfPassword.text.toString().isEmpty()){
            validate = false
            binding.textInPassword.error = getString(R.string.fieldEmpty_error)
        }
        return validate
    }

    private fun setErrorFormatEmail(state:Boolean){
        binding.textInEmail.error = null
        if(!state){binding.textInEmail.error = getString(R.string.invalidEmail_error)}
    }
}