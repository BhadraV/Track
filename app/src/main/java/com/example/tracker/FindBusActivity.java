package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FindBusActivity extends AppCompatActivity {

    AutoCompleteTextView from,to;
    ImageButton find;
    String places[]={"Ernamkulam","Aluva","Angamaly","Chalakudy","Thrissur","Kunnamkulam","Guruvayur","Thodupuzha","Muvattupuzha","Perumbavoor"
            ,"Meenakshipuram","Puthunagaram","Vadakkanchery","Palakad","VyttilaHub","Alappuzha","Changanassery","Chengannur","Alathur","Coimbatore","MalampuzhaDam"
            ,"Kongad","Pathiripala","Ottapalam","Shornur","Mannuthy"};

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bus);

        from=findViewById(R.id.busfrom);
        to=findViewById(R.id.busto);
        find=findViewById(R.id.search);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(FindBusActivity.this,android.R.layout.simple_list_item_1,places);
        from.setAdapter(adapter);
        to.setAdapter(adapter);


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String busfrom=from.getText().toString();
                final String busto=to.getText().toString();
                final String total=busfrom+busto;

                databaseReference= FirebaseDatabase.getInstance().getReference().child("conductors");
                final Query query=databaseReference.orderByChild("from_to").equalTo(total);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Intent intnt = new Intent(FindBusActivity.this, BusDetailsActivity.class);
                            intnt.putExtra("arg",total);
                            startActivity(intnt);
                        }
                    };

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FindBusActivity.this,"No Bus between this route",Toast.LENGTH_LONG).show();
                    };

                });


            }
        });






    }
}
