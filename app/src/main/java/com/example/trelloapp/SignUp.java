package com.example.trelloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    //image slider
    SliderLayout sliderLayout;
    HashMap<String, String> Hash_file_maps;

    //signup activity
    private Button CreateAccountButton;
    private EditText InputName, InputPhone, InputPassword;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        CreateAccountButton = findViewById(R.id.signup_btn);
        InputPhone = findViewById(R.id.signup_phone_input);
        InputPassword = findViewById(R.id.signup_password_input);
        InputName = findViewById(R.id.signup_name_input);
        loadingBar = new ProgressDialog(this);


        Hash_file_maps = new HashMap<String, String>();

        sliderLayout = (SliderLayout) findViewById(R.id.slider);

        Hash_file_maps.put("Boards", "https://blog.trello.com/hs-fs/hubfs/Education%20Page%20/trello-for-teachers@2x.png?width=2400&name=trello-for-teachers@2x.png");
        Hash_file_maps.put("Cards", "https://blog.trello.com/hubfs/Card-aging-final.png");
        Hash_file_maps.put("Activities", "https://clipartart.com/images/trello-clipart-6.png");
        Hash_file_maps.put("Collabration Of projects", "https://library.kissclipart.com/20180920/gzw/kissclipart-computer-icon-clipart-trello-computer-3fa25c01b684081d.png");

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

        for (String name : Hash_file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(SignUp.this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener) this);

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone =InputPhone.getText().toString();
        String password =InputPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(SignUp.this, "Please enter your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(SignUp.this, "Please enter your Number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(SignUp.this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(name, phone, password);
        }
    }

    private void ValidateEmail(final String name,final String phone,final String password) {

        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

           @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(SignUp.this, Login.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(SignUp.this, "Network Error: Please try again after sometime..", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }

                else {
                    Toast.makeText(SignUp.this, "This" + phone + "already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignUp.this, "Please try again using another Email ID", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUp.this, Mainacticityone.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}




