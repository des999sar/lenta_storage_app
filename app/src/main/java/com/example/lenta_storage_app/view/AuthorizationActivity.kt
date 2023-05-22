package com.example.lenta_storage_app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.lenta_storage_app.R
import com.example.lenta_storage_app.api.ApplicationDbContext
import com.google.android.material.navigation.NavigationBarView

class AuthorizationActivity : AppCompatActivity() {

    private var db = ApplicationDbContext()
    private lateinit var buttonLogin : Button
    private lateinit var editUsername : EditText
    private lateinit var editPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorize)

        var loginView = findViewById<NavigationBarView>(R.id.login_view) as LinearLayout
        buttonLogin = loginView.findViewById(R.id.button_login)
        editUsername = loginView.findViewById(R.id.edit_username)
        editPassword = loginView.findViewById(R.id.edit_password)

        buttonLogin.setOnClickListener { _ ->
            login()
        }

        supportActionBar?.title = "Авторизация"
    }

    private fun login() {
        if (editUsername.text.isEmpty()) {
            Toast.makeText(this, "Заполните имя пользователя", Toast.LENGTH_SHORT).show()
            return
        }
        if (editPassword.text.isEmpty()) {
            Toast.makeText(this, "Укажите пароль пользователя", Toast.LENGTH_SHORT).show()
            return
        }

        val user = db.getUser(editUsername.text.toString())
        if (user == null) {
            Toast.makeText(this, "Пользователя с заданным именем не существует", Toast.LENGTH_SHORT).show()
            return
        }
        if (user.password != editPassword.text.toString()) {
            Toast.makeText(this, "Указан неверный пароль", Toast.LENGTH_SHORT).show()
            return
        }
        if (user.roleId == 1) {
            startActivity(Intent(this, DirectorActivity::class.java));
        }
        else if (user.roleId == 2) {
            startActivity(Intent(this, ShifSuperVisorActivity::class.java));
        }
        else
        {
            Toast.makeText(this, "У Вас нет прав доступа к системе", Toast.LENGTH_SHORT).show()
            return
        }

        finish()
    }
}