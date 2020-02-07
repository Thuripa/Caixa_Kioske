package com.example.caixakioske.Adaptadores;

import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/*
*   DAO CLASS, BUILD IN ENGLISH TO GIT HUB VISUALIZATION
*
* */

public class FirebaseDAO {

    private DatabaseReference reference;

    public FirebaseDAO() {

    }

    public void create(String child, String key, Object object) {

        reference = FirebaseDatabase.getInstance().getReference().child(child);

        if(object != null) {
            reference.child(key).setValue(object);
        }
    }

    // Create Whit Aux
    public void create(String child, String aux, String key, Object object) {

        reference = FirebaseDatabase.getInstance().getReference().child(child).child(aux);

        if(object != null) {
            reference.child(key).setValue(object);
        }

    }

    public Query read(String child) {

        // SELECT * FROM child
        reference = FirebaseDatabase.getInstance().getReference();
        return reference.child(child);
    }

    public Query read(String child, String attribute, String value) {
        // SELECT * FROM child WHERE attribute EQUALS TO value
        reference = FirebaseDatabase.getInstance().getReference();
        return reference.child(child).orderByChild(attribute).equalTo(value);
    }

    // Get Generic Reference (Table)
    public DatabaseReference getReference() {
        DatabaseReference reff;
        reff = FirebaseDatabase.getInstance().getReference();
        return reff;
    }
    // Get a child Table Reference
    public DatabaseReference getReference(String child) {
        DatabaseReference reff;
        reff = FirebaseDatabase.getInstance().getReference().child(child);
        return reff;
    }
    // Get a Sub Table or a Object From a Table Reference
    public DatabaseReference getReference(String child, String aux) {
        DatabaseReference reff;
        reff = FirebaseDatabase.getInstance().getReference().child(child).child(aux);
        return reff;
    }

}
