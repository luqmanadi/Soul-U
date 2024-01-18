package com.intech.soul_u.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.intech.soul_u.MainActivity
import com.intech.soul_u.R
import com.intech.soul_u.databinding.ActivityLoginBinding
import com.intech.soul_u.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private var isPasswordVisible = false

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private var binding : ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // configure google sign in
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // initialize Firebase Auth
        auth = Firebase.auth

        setUpAction()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun setUpAction(){

        binding?.apply {

            emailEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (s.toString().isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                        emailEditText.error = "Invalid email format"
                    } else {
                        emailEditText.error = null
                    }
                }
                override fun afterTextChanged(p0: Editable?) {}
            })

            passwordEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (s.toString().length < 6){
                        passwordEditText.error = "Password should be at least 6 characters"
                    } else{
                        passwordEditText.error = null
                    }
                }
                override fun afterTextChanged(p0: Editable?) {}

            })

            btnLogin.setOnClickListener {
                val editTextEmail = emailEditText.text.toString().trim()
                val editTextPassword =passwordEditText.text.toString().trim()
                if (TextUtils.isEmpty(editTextEmail)  || TextUtils.isEmpty(editTextPassword)){
                    Toast.makeText(this@LoginActivity, "Input is still empty", Toast.LENGTH_SHORT).show()
                }else{
                    signInWithEmail(editTextEmail,editTextPassword)
                }
            }

            signInGoogle.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                resultLauncher.launch(signInIntent)

            }

            tvSignUp.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            togglePasswordButton.setOnClickListener {
                togglePasswordVisibility()
            }

        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        binding?.apply {
            val inputType = if (isPasswordVisible)
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            val selection = passwordEditText.selectionEnd ?: 0

            passwordEditText.inputType = inputType

            passwordEditText.setSelection(selection)

            val toggleIcon = if (isPasswordVisible)
                R.drawable.view_alt_fll
            else
                R.drawable.view_alt_light

            togglePasswordButton.setImageResource(toggleIcon)
        }
    }

    private fun signInWithEmail(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Email or Password invalid",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful,authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException){
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?){
        if (currentUser != null){
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}