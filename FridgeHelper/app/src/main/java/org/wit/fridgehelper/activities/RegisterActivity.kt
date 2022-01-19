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
import org.wit.fridgehelper.databinding.ActivityRegisterBinding
import org.wit.fridgehelper.main.MainApp
import org.wit.fridgehelper.models.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        app = application as MainApp

        binding.registerRegisterButton.setOnClickListener {
            var email = binding.registerEmail.text.toString()
            var password = binding.registerPassword.text.toString()
            var passwordCheck = binding.registerPasswordCheck.text.toString()

            if(password == passwordCheck)
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            i("createUserWithEmail:success")
                            val user = auth.currentUser
                            app.user = User("SampleUsername", "email")

                            //TODO Hay que pasar información o obtener?
                            val launcherIntent = Intent(this, ProductListActivity::class.java)
                            Snackbar.make(it,getString(R.string.welcome), Snackbar.LENGTH_LONG).show()
                            startActivity(launcherIntent)
                        } else {
                            i("createUserWithEmail:failure")
                            Snackbar.make(it,getString(R.string.failedRegistration), Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
            else
                Snackbar.make(it,getString(R.string.passwords_do_not_match), Snackbar.LENGTH_LONG)
                    .show()
        }

    }
}