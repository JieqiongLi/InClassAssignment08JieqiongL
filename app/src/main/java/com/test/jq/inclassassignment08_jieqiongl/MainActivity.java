package com.test.jq.inclassassignment08_jieqiongl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText keyField;
    EditText valueField;
    private Button writeButton;
    private Button readButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyField = (EditText) findViewById(R.id.key_edit);
        valueField = (EditText) findViewById(R.id.value_edit);
        writeButton = (Button) findViewById(R.id.write_button);
        readButton = (Button) findViewById(R.id.read_button);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Messages");

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(keyField.getText().toString()).setValue(valueField.getText().toString());
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef = database.getReference(keyField.getText().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String loadedValue = dataSnapshot.getValue(String.class);
                            valueField.setText(loadedValue);
                        } else {
                            valueField.setText("");
                            Toast.makeText(MainActivity.this, "Cannot find this key", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}





