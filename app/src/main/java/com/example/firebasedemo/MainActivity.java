package com.example.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "inviaMessaggio";

    FirebaseDatabase mDatabase;

    //UI
    EditText mMessaggio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initFirebase();
        initUI();
    }

    private void initUI() {
        mMessaggio = (EditText) findViewById(R.id.et_Messaggio);
    }


    private void initFirebase() {

        mDatabase = FirebaseDatabase.getInstance();
    }

    public void inviaMessaggio(View view) {
        Log.i(TAG, "Click su inviaMessaggio");

        // Scrittura stringa esistente sul database
        DatabaseReference myRef = mDatabase.getReference("msg");
        final String messaggio = mMessaggio.getText().toString();
        myRef.setValue(messaggio);

        //Creazione di un nuovo nodo sul database
        /*
        DatabaseReference myRef = mDatabase.getReference("test");
        myRef.setValue("Prova di scrittura");
        */

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.i(TAG, "Value is: " + value);
                mMessaggio.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i(TAG, "Failed to read value.", error.toException());
            }
        });





    }
}
