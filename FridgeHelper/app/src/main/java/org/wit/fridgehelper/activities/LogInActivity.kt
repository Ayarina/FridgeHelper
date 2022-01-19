package org.wit.fridgehelper.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber.i
import com.google.android.material.snackbar.Snackbar
import org.wit.fridgehelper.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.wit.fridgehelper.databinding.ActivityLogInBinding
import org.wit.fridgehelper.main.MainApp
import org.wit.fridgehelper.models.User


class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLogInBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        app = application as MainApp

        binding.logInLogInbutton.setOnClickListener {
            var email = binding.logInEmail.text.toString()
            var password = binding.logInPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        i("signInWithEmail:success")
                        val user = auth.currentUser
                        app.user = User("SampleUsername", "email")

                        //TODO Hay que pasar información o obtener?
                        val launcherIntent = Intent(this, ProductListActivity::class.java)
                        Snackbar.make(it,getString(R.string.welcome), Snackbar.LENGTH_LONG).show()
                        startActivity(launcherIntent)
                    } else {
                        i("signInWithEmail:failure")
                        Snackbar.make(it, getString(R.string.logIn_failure), Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
        }

        binding.logInRegisterButton.setOnClickListener {
            val launcherIntent = Intent(this, RegisterActivity::class.java)
            startActivity(launcherIntent)
        }

    }

}