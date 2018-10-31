package com.bilgiyazan.malzemeiste.adminpaneli.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import fr.ganfra.materialspinner.MaterialSpinner;

public class EditYedekActivity extends AppCompatActivity {
    MaterialEditText model_name, kod, fiyat, uyumlu;
    MaterialSpinner rate_spinner;
    String Selected_Rate;
    ArrayAdapter<String> adapter;
    Button cancel, save;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_yedek_layout);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Yedek_Parca");

        model_name = (MaterialEditText) findViewById(R.id.model_name);
        kod = (MaterialEditText) findViewById(R.id.kod);
        fiyat = (MaterialEditText) findViewById(R.id.fiyat);
        uyumlu = (MaterialEditText) findViewById(R.id.uyumlu);
        cancel = (Button) findViewById(R.id.cancel_edit);
        save = (Button) findViewById(R.id.save_edit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model_name.getText().length() != 0 && kod.getText().length() != 0 && fiyat.getText().length() != 0) {
                    final String price;
                    if (Selected_Rate.equals("Dolar")) {
                        price = fiyat.getText().toString() + "$";

                    } else {
                        price = fiyat.getText().toString() + "€";

                    }

                    if (model_name.getText().toString().equals(getIntent().getStringExtra("Model")) && kod.getText().toString().equals(getIntent().getStringExtra("Kod"))
                            && price.equals(getIntent().getStringExtra("Fiyat")) && uyumlu.getText().toString().equals(getIntent().getStringExtra("Uyumlu"))) {
                        Intent returnIntent = new Intent();
                        setResult(RESULT_CANCELED, returnIntent);
                        finish();


                    } else {


                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot postSnapshot) {


                                for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                                        Query ObjectKod1 = dataSnapshot1.getRef().orderByChild("Kod").equalTo(getIntent().getStringExtra("Kod"));
                                        ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot tasksSnapshot) {
                                                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {


                                                /*    DatabaseReference modelRef = snapshot.getRef().child("Roland");
                                                    Map<String, String> newData = new HashMap<String, String>();
                                                    newData.put("Model", model_name.getText().toString());
                                                    newData.put("Kod", kod.getText().toString());
                                                    newData.put("Fiyat", price);
                                                    newData.put("Description", kod.getText().toString());
                                                    modelRef.setValue(newData);
*/

                                                    if (!model_name.getText().toString().equals(getIntent().getStringExtra("Model"))) {
                                                        snapshot.getRef().child("Model").setValue(model_name.getText().toString());

                                                    }
                                                    if (!kod.getText().toString().equals(getIntent().getStringExtra("Kod"))) {
                                                        snapshot.getRef().child("Kod").setValue(kod.getText().toString());

                                                    }
                                                    if (!price.equals(getIntent().getStringExtra("Fiyat"))) {
                                                        snapshot.getRef().child("Fiyat").setValue(price);

                                                    }
                                                    if (!uyumlu.getText().toString().equals(getIntent().getStringExtra("Uyumlu"))) {
                                                        if (uyumlu.getText().toString().isEmpty()) {

                                                            snapshot.getRef().child("Uyumlu").setValue("------");

                                                        } else {
                                                            snapshot.getRef().child("Uyumlu").setValue(uyumlu.getText().toString());


                                                        }
                                                    }


                                                    Intent returnIntent = new Intent();
                                                    returnIntent.putExtra("Model",model_name.getText().toString());
                                                    returnIntent.putExtra("Kod",kod.getText().toString());
                                                    returnIntent.putExtra("Fiyat",price);
                                                    if (uyumlu.getText().toString().isEmpty()) {

                                                        returnIntent.putExtra("Uyumlu","------");

                                                    } else {
                                                        returnIntent.putExtra("Uyumlu",uyumlu.getText().toString());


                                                    }
                                                    setResult(RESULT_OK, returnIntent);
                                                    finish();
                                                    Toast.makeText(EditYedekActivity.this, "Ürün başarıyla güncellendi", Toast.LENGTH_SHORT).show();
                                                }
                                            }


                                            @Override
                                            public void onCancelled(DatabaseError firebaseError) {
                                                System.out.println("The read failed: " + firebaseError.getMessage());
                                            }
                                        });

                                    }
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                } else {
                    if (model_name.getText().length() == 0) {
                        model_name.setError(getResources().getString(R.string.required_field));
                    }
                    if (kod.getText().length() == 0) {
                        kod.setError(getResources().getString(R.string.required_field));
                    }
                    if (fiyat.getText().length() == 0) {
                        fiyat.setError(getResources().getString(R.string.required_field));
                    }

                }

            }
        });
        model_name.setText(getIntent().getStringExtra("Model"));
        kod.setText(getIntent().getStringExtra("Kod"));
        fiyat.setText(getIntent().getStringExtra("Fiyat").substring(0, getIntent().getStringExtra("Fiyat").length() - 1));
        uyumlu.setText(getIntent().getStringExtra("Uyumlu"));

        if (getIntent().getStringExtra("Fiyat").substring(getIntent().getStringExtra("Fiyat").length() - 1, getIntent().getStringExtra("Fiyat").length()).equals("€")) {

            String[] ITEMS = {"Euro", "Dolar"};
            Selected_Rate = "Euro";
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        } else if (getIntent().getStringExtra("Fiyat").substring(getIntent().getStringExtra("Fiyat").length() - 1, getIntent().getStringExtra("Fiyat").length()).equals("$")) {
            String[] ITEMS = {"Dolar", "Euro"};
            Selected_Rate = "Dolar";
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rate_spinner = (MaterialSpinner) findViewById(R.id.rate_spinner);
        rate_spinner.setAdapter(adapter);

        rate_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selected_Rate = parent.getSelectedItem().toString() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
