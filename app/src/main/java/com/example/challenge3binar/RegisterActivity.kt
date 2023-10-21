package com.example.challenge3binar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.challenge3binar.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    // create Firebase authentication object
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvRegToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        binding.btnRegist.setOnClickListener {
            val email = binding.etEmailReg.text.toString()
            val name = binding.etUsername.text.toString()
            val password = binding.etPasswordReg.text.toString()
            val confirmPass = binding.etConfirmpasswordReg.text.toString()

            //Validasi Nama
            if(name.isEmpty()) {
                binding.etUsername.error = "Nama Harus Diisi"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }

            //Validasi email
            if (email.isEmpty()) {
                binding.etEmailReg.error = "Email Harus Diisi"
                binding.etEmailReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmailReg.error = "Email Tidak Valid"
                binding.etEmailReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()) {
                binding.etPasswordReg.error = "Password Harus Diisi"
                binding.etPasswordReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi panjang password
            if (password.length < 6) {
                binding.etPasswordReg.error = "Password Minimal 6 Karakter"
                binding.etPasswordReg.requestFocus()
                return@setOnClickListener
            }

            //Validasi password sama dengan confirm password
            if (password != confirmPass) {
                Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            RegisterFirebase(email, password)
        }
    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}