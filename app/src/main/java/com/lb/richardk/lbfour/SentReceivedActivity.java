package com.lb.richardk.lbfour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SentReceivedActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private RecyclerView alertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_received);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();

        myRef=FirebaseDatabase.getInstance().getReference().child("message").child(uid);
        myRef.keepSynced(true);

        alertList=(RecyclerView)findViewById(R.id.AlertView);
        alertList.setHasFixedSize(true);
        alertList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Alert,ReceivedActivity.AlertViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Alert, ReceivedActivity.AlertViewHolder>
                (Alert.class,R.layout.alert_row,ReceivedActivity.AlertViewHolder.class,myRef) {

            @Override
            protected void populateViewHolder(ReceivedActivity.AlertViewHolder viewHolder, Alert model, int position)
            {
                viewHolder.setReg(model.getReg());
                viewHolder.setSubject(model.getSubject());
            }
        };

        alertList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class AlertViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public AlertViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
        }

        public void setReg(String reg)
        {
            TextView carReg=(TextView)mView.findViewById(R.id.carReg);
            carReg.setText(reg);
        }
        public void setSubject(String mess)
        {
            TextView message=(TextView)mView.findViewById(R.id.message);
            message.setText(mess);
        }
    }
}
