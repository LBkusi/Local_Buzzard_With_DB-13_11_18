package com.lb.richardk.lbfour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateDetailsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");

    public EditText number;
    public EditText email;
    public EditText borough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        number = (EditText) findViewById(R.id.ContactNumbereditText2);
        email = (EditText) findViewById(R.id.EmaileditText3);
        borough = (EditText) findViewById(R.id.ViewBorough);

        Button updateBtn = (Button)findViewById(R.id.Updatebutton);
        updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = number.getText().toString();
                String em = email.getText().toString();
                String bor = borough.getText().toString();

                User user = new User(num, em, bor);

                myRef.push().setValue(user);

                Intent startIntent = new Intent (getApplicationContext(), HomeActivity.class);
                //show how to pass information to another activity
                startActivity(startIntent);

                // Write a message to the database
                // myRef.setValue("Hello, World!");
            }
        });
    }
}
