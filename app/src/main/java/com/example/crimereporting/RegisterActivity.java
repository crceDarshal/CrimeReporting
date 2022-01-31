package com.example.crimereporting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class RegisterActivity extends AppCompatActivity {
    private Button registerBtn;
    private EditText InputName, InputAddress, InputEmail, InputPassword, InputPhone, InputSecQues1, InputSecQues2;
    private ProgressDialog progressDialog;
    private TextView alreadyUser;
    private CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InputEmail = findViewById(R.id.RegisterEmail);
        InputPassword = findViewById(R.id.RegisterPassword);
        InputName = findViewById(R.id.RegisterName);
        InputAddress = findViewById(R.id.RegisterAddress);
        InputPhone = findViewById(R.id.RegisterPhone);
        InputSecQues1 = findViewById(R.id.secQues1);
        InputSecQues2 = findViewById(R.id.secQues2);
        countryCodePicker = findViewById(R.id.country_code_picker);
        registerBtn = findViewById(R.id.create_button);
        alreadyUser = findViewById(R.id.alreadyUser);
        progressDialog = new ProgressDialog(this);


        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = InputName.getText().toString().trim();
                String address = InputAddress.getText().toString().trim();
                String email = InputEmail.getText().toString();
                String pass = InputPassword.getText().toString();
                String ques1 = InputSecQues1.getText().toString().toLowerCase();
                String ques2 = InputSecQues2.getText().toString().toLowerCase();
                String passwordVal = "^" +
                        //"(?=.*[0-9])" +         //at least 1 digit
                        //"(?=.*[a-z])" +         //at least 1 lower case letter
                        //"(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,}" +               //at least 4 characters
                        "$";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                String phone = InputPhone.getText().toString().trim();
                //Remove first zero if entered!
//                if (phone.charAt(0) == '0') {
//                    phone = phone.substring(1);
//                }else{
//
//                }
                //Complete phone number
                final String phoneNum = "+" + countryCodePicker.getFullNumber() + phone;

                if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(email) && TextUtils.isEmpty(name) && TextUtils.isEmpty(address) && TextUtils.isEmpty(pass) && TextUtils.isEmpty(ques1) && TextUtils.isEmpty(ques2)){
                    Toast.makeText(RegisterActivity.this, "Please fill every details...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(phone)){
                    InputPhone.setError("Phone Required");
                }
                else if(phone.length()!=10){
                    InputPhone.setError("Phone Number must be of 10 digits");
                }
                else if(TextUtils.isEmpty(name)){
                    InputName.setError("Name Required");
                }
                else if(TextUtils.isEmpty(address)){
                    InputAddress.setError("Address Required");
                }
                else if(TextUtils.isEmpty(email)){
                    InputEmail.setError("Email Required");
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this, "Password Required", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(ques1)){
                    InputSecQues1.setError("Answer Required");
                }
                else if(TextUtils.isEmpty(ques2)){
                    InputSecQues2.setError("Answer Required");
                }
                else if (!pass.matches(passwordVal)) {
                    Toast.makeText(RegisterActivity.this, "Password is too weak, must contain a special character", Toast.LENGTH_SHORT).show();
                }
                else if (!email.matches(emailPattern)) {
                    InputEmail.setError("Please enter a valid Email Id");
                }
                else{
                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("address", address);
                    intent.putExtra("email", email);
                    intent.putExtra("password", pass);
                    intent.putExtra("phone", phoneNum);
                    intent.putExtra("ques1", ques1);
                    intent.putExtra("ques2", ques2);
                    startActivity(intent);

                }
            }
        });
    }
}