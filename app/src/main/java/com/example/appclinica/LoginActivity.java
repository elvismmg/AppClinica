package com.example.appclinica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appclinica.ui.home.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.login_layout, loginFragment).commit();


        //final Context context = this;

//        Button buttonLogin = findViewById(R.id.btnLogin);
//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, MainActivity.class);
//                context.startActivity(intent);
//            }
//        });
//
//        TextView lnkForgotPassword = findViewById(R.id.lnkForgotPassword);
//        lnkForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                PasswordFragment passwordFragment = new PasswordFragment();
////                getSupportFragmentManager().beginTransaction().add(passwordFragment, "tagPasswordFragment").commit();
//                //getSupportFragmentManager().beginTransaction().replace(R.id.login_layout, new PasswordFragment());
//            }
//        });
//
//        TextView lnkRegister = findViewById(R.id.lnkRegistar);
//        lnkRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //getSupportFragmentManager().beginTransaction().replace(R.id.login_layout, new RegistryUserFragment());
//            }
//        });
    }

}