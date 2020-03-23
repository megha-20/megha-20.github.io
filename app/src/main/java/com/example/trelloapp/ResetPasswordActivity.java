package com.example.trelloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trelloapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check="";
    private TextView pageTitle, titleQuestions;
    private EditText phoneNumber,ques1,ques2;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check = getIntent().getStringExtra("check");

        pageTitle=findViewById(R.id.page_title);
        titleQuestions=findViewById(R.id.title_question);
        phoneNumber=findViewById(R.id.find_phone_number);
        ques1=findViewById(R.id.questions_1);
        ques2=findViewById(R.id.questions_2);
        verifyButton=findViewById(R.id.Verify_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);


        if(check.equals("settings")){
            pageTitle.setText("Set Questions");

            titleQuestions.setText("Please set answer for questions?");
            verifyButton.setText("Set");
            displayPreviousAnswers();
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setAnswers();

                }
            });
        }
        else if(check.equals("login")){
            phoneNumber.setVisibility(View.VISIBLE);
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    verifyUser();
                }


            });
        }

    }

    private void setAnswers(){
        String ans1=ques1.getText().toString().toLowerCase();
        String ans2=ques2.getText().toString().toLowerCase();

        if(ques1.equals("") && ques2.equals("")){
            Toast.makeText(ResetPasswordActivity.this, "Please answer both questions", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(Prevalent.onlineUser.getPhone());


            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", ans1);
            userdataMap.put("answer2", ans2);


            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPasswordActivity.this, "Answers security questions successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this,Home.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void displayPreviousAnswers(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(Prevalent.onlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String ans1 = dataSnapshot.child("answer1").getValue().toString();
                    String ans2 = dataSnapshot.child("answer2").getValue().toString();

                    ques1.setText(ans1);
                    ques2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void verifyUser() {

        final String phone = phoneNumber.getText().toString();

        String ans1=ques1.getText().toString().toLowerCase();
        String ans2=ques2.getText().toString().toLowerCase();

        if(!phone.equals("")&& !ans1.equals("") && !ans2.equals("")){
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
//                        String mphone = dataSnapshot.child("phone").getValue().toString();

                        if (dataSnapshot.hasChild("Security Questions")) {

                            String ans1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();


                            if (!ans1.equals(ans1)) {
                                Toast.makeText(ResetPasswordActivity.this, "your first answer is wrong", Toast.LENGTH_SHORT).show();
                            } else if (!ans2.equals(ans2)) {
                                Toast.makeText(ResetPasswordActivity.this, "your second answer is wrong", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("New Password");

                                final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("Write password here....");
                                builder.setView(newPassword);

                                builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (!newPassword.getText().toString().equals("")) {
                                            ref.child("password").setValue(newPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(ResetPasswordActivity.this, "Password is Reset Successfully", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(ResetPasswordActivity.this, Login.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });

                                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                            }
                        }


                        else {
                            Toast.makeText(ResetPasswordActivity.this, "You have not set the security questions yet", Toast.LENGTH_SHORT).show();
                        }


                    }

                    else

                    {
                        Toast.makeText(ResetPasswordActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    }
                }




                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        else {
            Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show();
        }



    }




}
