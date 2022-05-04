package com.example.signup;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private Button log;
    private Button sig;
    private EditText username;
    private EditText password;
    private TextView txt1;
    private TextView txt2;
    private int counter=3;
    public String[] perm = {WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE};
    //public ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log= (Button)findViewById(R.id.b_login);
        sig= (Button)findViewById(R.id.b_signup);
        username = (EditText)findViewById(R.id.usertext);
        password = (EditText)findViewById(R.id.passtext);
        txt1 = (TextView) findViewById(R.id.textView);
        txt2 = (TextView) findViewById(R.id.textView2);
        txt1.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.VISIBLE);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { login(view); }
        });
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup(view);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!checkPermision(getApplicationContext())){//Uyg izinleri icin android11 ve üstü
            new AlertDialog.Builder(this)
                    .setTitle("Give Permission")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestPermission(getApplicationContext());
                        }
                    })
                    .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setIcon(R.drawable.ic_baseline_warning_24).show();

        }
    }

    public void signup(View view) {
        Intent intent = new Intent(this,DisplaySignUpActivity.class);
        try {
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(),
                    "Log in succeed.",Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this,Music_List.class);
            try {
                startActivity(intent2);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Wrong",Toast.LENGTH_SHORT).show();
            counter--;

            if(counter==0){//3 kez yanlış giriş sonrası tuş bloke
                log.setEnabled(false);
                Toast.makeText(getApplicationContext(),
                        "Log in button deactivated cause of 3 wrong attempt.",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),
                        "Need to Sign Up.",Toast.LENGTH_SHORT).show();
                signup(view);

            }
        }
    }
    public boolean checkPermision(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }else{
            int read = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
            int write = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
            return read == PackageManager.PERMISSION_GRANTED && write==PackageManager.PERMISSION_GRANTED;
        }
    }
    public void requestPermission(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                Uri u= Uri.fromParts("package",getPackageName(),null);
                startActivityForResult(intent,1);

            }catch (Exception e){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent,1);
            }
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,perm,1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    boolean read=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean write=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if (read && write) {
                        Toast.makeText(getApplicationContext(),
                                "Permission granted.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Permission denied.",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),
                            "User denied the permission.",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}