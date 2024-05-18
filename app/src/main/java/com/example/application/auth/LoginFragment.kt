package com.example.application.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.application.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class LoginFragment : Fragment() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var regBtn: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.email_et)
        password = view.findViewById(R.id.password_et)
        loginBtn = view.findViewById(R.id.login_btn)
        regBtn = view.findViewById(R.id.reg_btn)

        loginBtn.setOnClickListener {
            if (email.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Log.d("RRR", "Пусто!")
                Snackbar.make(view, "Поля не заполнены!", Snackbar.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            findNavController().navigate(R.id.action_loginFragment_to_navigation_notifications)
                        } else {
                            Snackbar.make(view, "Ошибка входа в аккаунт!", Snackbar.LENGTH_LONG).show();
                        }
                    }
            }
        }

        regBtn.setOnClickListener {
            if (email.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Log.d("RRR", "Пусто!")
                Snackbar.make(view, "Поля не заполнены", Snackbar.LENGTH_LONG).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                Snackbar.make(view, "Неверный адрес электронной почты", Snackbar.LENGTH_LONG).show();
            } else if (password.text.toString().length < 6) {
                Snackbar.make(view, "Пароль должен быть не менее 6 символов", Snackbar.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            Snackbar.make(view, "Вы успешно зарегистрированы!", Snackbar.LENGTH_LONG).show();
                            findNavController().navigate(R.id.action_loginFragment_to_navigation_notifications)
                        } else {
                            Snackbar.make(view, "Ошибка при регистрации!", Snackbar.LENGTH_LONG).show();
                        }
                    }
            }
        }
    }
}