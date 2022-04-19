package com.example.moexfilm.views

import android.os.Bundle
import androidx.activity.viewModels
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ActivityRegisterBinding
import com.example.moexfilm.util.Util


class RegisterActivity : AuthActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passwordConfirm: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initGoogleSignInClient()
        initRegisteredObserver()
        initSignInObserver()
        setListener()
    }

    private fun setListener() {
        binding.tvLogin.setOnClickListener { finish() }
        binding.btnBack.setOnClickListener { finish() }
        binding.btnSigninGoogle.setOnClickListener { signInGoogle() }
        binding.btnRegister.setOnClickListener {
            initEmailPassword()
            if(validateNoEmptyFields()) {
                if (Util.validateEmailFormat(email)){
                    setErrorFormatEmail(true)
                    if(checkPasswordLength()){
                        if(validatePasswordMatch()) { registerWithEmailPassword(email,password)} }
                    }else
                        setErrorFormatEmail(false)
                }
            }
    }

    private fun initEmailPassword(){
        email = binding.tfEmail.text.toString()
        password = binding.tfPassword.text.toString()
        passwordConfirm = binding.tfConfirmPassword.text.toString()
    }

    private fun validateNoEmptyFields():Boolean{
        var validate = true

        binding.textInEmail.error = null
        if(binding.tfEmail.text.toString().isEmpty()){
            validate = false
            binding.textInEmail.error = getString(R.string.fieldEmpty_error)
        }

        binding.textInPassword.error = null
        if(binding.tfPassword.text.toString().isEmpty()){
            validate = false
            binding.textInPassword.error = getString(R.string.fieldEmpty_error)
        }

        binding.textInConfirmPassword.error = null
        if(binding.tfConfirmPassword.text.toString().isEmpty()){
            validate = false
            binding.textInConfirmPassword.error = getString(R.string.fieldEmpty_error)
        }

        return validate
    }

    private fun validatePasswordMatch():Boolean{
        if(password==passwordConfirm){
            binding.textInConfirmPassword.error = null
            return true
        }
        else{
            binding.textInConfirmPassword.error = getString(R.string.passwordMatch_error)
            return false
        }
    }

    private fun checkPasswordLength():Boolean{
        binding.textInPassword.error = null
        if(password.length<6) {
            binding.textInPassword.error = getString(R.string.passwordLength_error)
            return false
        }
        return true
    }

    private fun setErrorFormatEmail(state:Boolean){
        binding.textInEmail.error = null
        if(!state){binding.textInEmail.error = getString(R.string.invalidEmail_error)}
    }
}


