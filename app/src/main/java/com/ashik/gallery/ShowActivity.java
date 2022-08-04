package com.ashik.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity implements  ImageAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private ArrayList<Upload> list;

    private ImageAdapter adapter;
    private FirebaseStorage mStorage;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private ValueEventListener mDBListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new ImageAdapter(this,list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(ShowActivity.this);
        mStorage=FirebaseStorage.getInstance();
        mDBListner=root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    upload.setKey(dataSnapshot.getKey());
                    list.add(upload);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowActivity.this, "Failed To Load!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem=list.get(position);
        final String selectedKey=selectedItem.getKey();

        StorageReference imageref=mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                root.child(selectedKey).removeValue();
                Toast.makeText(ShowActivity.this, "Image Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        root.removeEventListener(mDBListner);
    }
}