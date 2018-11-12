package com.lb.richardk.lbfour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mEmail, mPassword, fName, lName, mDob, mNumber;

    public String uid;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fName = (EditText) findViewById(R.id.FirstNameeditText);
        lName = (EditText) findViewById(R.id.LastNameeditText);
        mEmail = (EditText) findViewById(R.id.EmaileditText);
        mDob = (EditText) findViewById(R.id.DateofBirtheditText);
        mNumber = (EditText) findViewById(R.id.ContactNumbereditText);
        mPassword = (EditText) findViewById(R.id.PasswordeditText);

        Button nextbtn = (Button)findViewById(R.id.Nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            Intent startIntent = new Intent (getApplicationContext(), VehicleActivity.class);
            //show how to pass information to another activity
            startActivity(startIntent);

            mAuth = FirebaseAuth.getInstance();

            final String email = mEmail.getText().toString();
            final String password = mPassword.getText().toString();
            final String firstName = fName.getText().toString();
            final String lastName = lName.getText().toString();
            final String number = mNumber.getText().toString();
            final String dob = mDob.getText().toString();

            // mAuth.createUserWithEmailAndPassword(email, password);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        NewUser newUser = new NewUser(firstName, lastName, number, dob, email);

                        mAuth.signInWithEmailAndPassword(email, password);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        uid = user.getUid();

                        myRef.child(uid).setValue(newUser);
                    }
                }
            });
            }
        });
    }
}
