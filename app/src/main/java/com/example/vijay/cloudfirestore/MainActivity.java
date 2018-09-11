package com.example.vijay.cloudfirestore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText name_et, mobile_et, fetchmobile;
    TextView fetchname;
    Button save, fetch;
    FirebaseFirestore firestoredb;
    CollectionReference dbdetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestoredb = FirebaseFirestore.getInstance();
        name_et = findViewById(R.id.name_et);
        mobile_et = findViewById(R.id.mobile_et);
        fetchmobile = findViewById(R.id.fetchmobile);

        fetchname = findViewById(R.id.fetchname);

        save = findViewById(R.id.save);
        fetch = findViewById(R.id.fetch);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchUser();
            }
        });

    }


    private void fetchUser() {
        final String Mobile = fetchmobile.getText().toString();

        firestoredb.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                List<DocumentSnapshot> list = documentSnapshots.getDocuments();

                for (DocumentSnapshot document : list) {
                    Users u = document.toObject(Users.class);
                    if (u.getUserMobile().toString().equals(Mobile)) {
                        String User = u.getUserName();
                        Toast.makeText(MainActivity.this, User, Toast.LENGTH_SHORT).show();
                        fetchname.setText(User);
                    }

                    // String Mobile = u.getU;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fetching Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
//Save Detail
    private void saveDetails() {

        String Name = name_et.getText().toString();
        String Mobile = mobile_et.getText().toString();

        if (!TextUtils.isEmpty(Name) && !Mobile.isEmpty()) {

            dbdetails = firestoredb.collection("Users");
            Users users = new Users(Name, Mobile);
            dbdetails.add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(MainActivity.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Saving User Details Failed ", Toast.LENGTH_SHORT).show();

                }
            });

        } else {
            Toast.makeText(this, "Fill the User Details..", Toast.LENGTH_SHORT).show();
        }

    }

}
