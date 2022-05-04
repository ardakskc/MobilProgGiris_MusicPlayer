package com.example.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;


public class DisplaySignUpActivity extends AppCompatActivity {

    private Button sig;
    private EditText sendto;
    private EditText name;
    private EditText surname;
    private EditText tel;
    private EditText usname;
    private EditText passtext;
    private EditText birth;
    private EditText passtext2;
    private SharedPreferences sharedp;
    private SharedPreferences.Editor editor;


    protected boolean isEmpty(EditText edt) { //Bos input var mı kontrolu
        if (edt.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sign_up);

        sendto =(EditText)findViewById(R.id.mail);
        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        tel = (EditText)findViewById(R.id.tel);
        usname = (EditText)findViewById(R.id.t_username);
        passtext = (EditText)findViewById(R.id.s_password);
        passtext2 = (EditText)findViewById(R.id.s_password2);
        birth = (EditText)findViewById(R.id.birthdate);
        sig =(Button)findViewById(R.id.b_sig);

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uygun input formu kontrolleri
                if(!passtext.getText().toString().equals(passtext2.getText().toString())){
                    Toast.makeText(DisplaySignUpActivity.this, "Passwords not match.Try Again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(isEmpty(sendto) || isEmpty(name) || isEmpty(surname) || isEmpty(usname) || isEmpty(passtext) || isEmpty(passtext2) || isEmpty(tel)){
                    Toast.makeText(DisplaySignUpActivity.this, "Please fill the empty field.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!(sendto.getText().toString().contains("@") && sendto.getText().toString().contains("."))){
                    Toast.makeText(DisplaySignUpActivity.this, "Mail adress is wrong.Try Again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String emailsend = sendto.getText().toString();
                    String emailsubject = name.getText().toString() + " " + surname.getText().toString();
                    String emailmessage = "Ad Soyad:  " + name.getText().toString() + " " + surname.getText().toString() + " \nTelefon: " + tel.getText().toString() + " \nKullanıcı Adı ve Şifre: " + usname.getText().toString()
                            + " " + passtext.getText().toString() + " \nDoğum Tarihi: " + birth.getText().toString();

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailsend});
                    email.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
                    email.putExtra(Intent.EXTRA_TEXT, emailmessage);

                    email.setType("message/rfc822");

                    try {
                        startActivity(Intent.createChooser(email, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(DisplaySignUpActivity.this, "Error occur to sending mail..", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(DisplaySignUpActivity.this, "Sign Up Suceed.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}