package com.lb.richardk.lbfour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehicleViewActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private RecyclerView carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_view);

        Button addvehiclebutton = (Button) findViewById(R.id.addVehiclebutton);
        addvehiclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), AddVehicleActivity.class);
                startActivity(startIntent);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();

        myRef = FirebaseDatabase.getInstance().getReference().child("Car").child(uid);
        myRef.keepSynced(true);

        carList = (RecyclerView) findViewById(R.id.UserCarRecycleView);
        carList.setHasFixedSize(true);
        carList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<UserCar,CarViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<UserCar, CarViewHolder>
                (UserCar.class,R.layout.car_row,CarViewHolder.class,myRef) {

            @Override
            protected void populateViewHolder(CarViewHolder viewHolder, UserCar model, int position)
            {
                viewHolder.setVehicleReg(model.getVehicleReg());
                viewHolder.setModel(model.getModel());
            }
        };

        carList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public CarViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setVehicleReg(String reg) {
            TextView carReg = (TextView) mView.findViewById(R.id.carReg);
            carReg.setText(reg);
        }

        public void setModel(String mod) {
            TextView message = (TextView) mView.findViewById(R.id.model);
            message.setText(mod);
        }
    }
}