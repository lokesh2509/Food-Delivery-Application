package com.example.food_tanya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_tanya.activities.LoginActivity;
import com.example.food_tanya.activities.MainActivity;
import com.example.food_tanya.activities.ReadWriteUserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private EditText editTextRegisterName,editTextRegisterEmail,editTextRegisterDob,editTextRegisterMobile,editTextRegisterPwd,
            editTextRegisterConfirmPwd;
    private ProgressBar progressBar;
    private DatePickerDialog picker;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonGenderSelected;
    private static final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toast.makeText(SignUp.this, "You can register now", Toast.LENGTH_LONG).show();
        progressBar=findViewById(R.id.progressBar);
        editTextRegisterName=findViewById(R.id.text_full_name);
        editTextRegisterEmail=findViewById(R.id.text_email);
        editTextRegisterDob=findViewById(R.id.text_birthday);
        editTextRegisterMobile=findViewById(R.id.text_mobile);
        editTextRegisterPwd=findViewById(R.id.text_password);
        editTextRegisterConfirmPwd=findViewById(R.id.text_confirm_password);
        //Radio Button
        radioGroupRegisterGender=findViewById(R.id.group_gender);
        radioGroupRegisterGender.clearCheck();
        TextView goLogin=findViewById(R.id.backLogin);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoLogin();

            }
        });

        ///date picker
        editTextRegisterDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);
                picker =new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextRegisterDob.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                picker.show();
            }
        });
        Button buttonRegister=findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId= radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonGenderSelected=findViewById(selectedGenderId);

                ///getting values
                String textFullName=editTextRegisterName.getText().toString();
                String textEmail=editTextRegisterEmail.getText().toString();
                String textDoB=editTextRegisterDob.getText().toString();
                String textMobile=editTextRegisterMobile.getText().toString();
                String textPwd=editTextRegisterPwd.getText().toString();
                String textConfirmPwd=editTextRegisterConfirmPwd.getText().toString();
                String textGender;

                //mobile validation
                String mobileRegex="[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern= Pattern.compile(mobileRegex);
                mobileMatcher=mobilePattern.matcher(textMobile);
                if(TextUtils.isEmpty(textFullName))
                {
                    Toast.makeText(SignUp.this,"please enter your name",Toast.LENGTH_LONG).show();
                    editTextRegisterName.setError("Name is required");
                    editTextRegisterName.requestFocus();

                }
                else if(TextUtils.isEmpty(textEmail))
                {
                    Toast.makeText(SignUp.this,"please enter your email",Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("email is required");
                    editTextRegisterEmail.requestFocus();

                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches())
                {
                    Toast.makeText(SignUp.this,"please re-enter your email",Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError(" Valid email is required");
                    editTextRegisterEmail.requestFocus();
                }
                else if(TextUtils.isEmpty(textDoB))
                {
                    Toast.makeText(SignUp.this,"please enter your date of birth",Toast.LENGTH_LONG).show();
                    editTextRegisterDob.setError("Date of Birth  is required");
                    editTextRegisterDob.requestFocus();
                }
                else if(radioGroupRegisterGender.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(SignUp.this,"please select your gender",Toast.LENGTH_LONG).show();
                    radioButtonGenderSelected.setError("gender  is required");
                    radioButtonGenderSelected.requestFocus();
                }
                else if(TextUtils.isEmpty(textMobile))
                {
                    Toast.makeText(SignUp.this,"please enter mobile number",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile number is required");
                    editTextRegisterMobile.requestFocus();
                }
                else if(textMobile.length()!=10)
                {
                    Toast.makeText(SignUp.this,"please re-enter your number",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError(" Valid  number is required");
                    editTextRegisterMobile.requestFocus();

                }else if(!mobileMatcher.find())
                {
                    Toast.makeText(SignUp.this,"Mobile Number is not valid.",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.requestFocus();
                }
                else if(TextUtils.isEmpty(textPwd))
                {
                    Toast.makeText(SignUp.this,"please enter password",Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("Password is required");
                    editTextRegisterPwd.requestFocus();
                }
                else if(textPwd.length()<6)
                {
                    Toast.makeText(SignUp.this," password should not be less then 6 digits",Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("Password is weak");
                    editTextRegisterPwd.requestFocus();
                }
                else if(TextUtils.isEmpty(textConfirmPwd))
                {
                    Toast.makeText(SignUp.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConfirmPwd.setError("Confirm Password is required");
                    editTextRegisterConfirmPwd.requestFocus();
                }
                else if(!textPwd.equals(textConfirmPwd))
                {
                    Toast.makeText(SignUp.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    editTextRegisterConfirmPwd.setError("Password Confirmation is required");
                    editTextRegisterConfirmPwd.requestFocus();
                    //
                    editTextRegisterPwd.clearComposingText();
                    editTextRegisterConfirmPwd.clearComposingText();
                }
                else{
                    textGender=radioButtonGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName,textEmail,textDoB,textGender,textMobile,textPwd);
                }

            }
        });

    }//to register the user

    private void GoLogin() {

        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void registerUser(String textFullName, String textEmail, String textDoB, String textGender, String textMobile, String textPwd) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    //user data save

                    ReadWriteUserDetails writeUserDetails=new ReadWriteUserDetails(textFullName,textDoB,textGender,textMobile);
                    DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(SignUp.this,"User Registered Successfully",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(SignUp.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(SignUp.this,"User Registration failed.Please try again",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }


                        }
                    });

                }
                else{
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e)
                    {
                        editTextRegisterPwd.setError("Your password is too weak. Kindly try again!");
                    }
                    catch(FirebaseAuthInvalidCredentialsException e)
                    {
                        editTextRegisterPwd.setError("Your email is invalid or already in use. Please try again");
                    }
                    catch(FirebaseAuthUserCollisionException e)
                    {

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }
}