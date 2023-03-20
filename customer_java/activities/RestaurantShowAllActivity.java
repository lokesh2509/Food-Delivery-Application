package com.example.food_tanya.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_tanya.R;
import com.example.food_tanya.adapters.RestaurantShowAllAdapter;
import com.example.food_tanya.adapters.ShowAllAdapter;
import com.example.food_tanya.models.RestaurantShowAllModel;
import com.example.food_tanya.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RestaurantShowAllAdapter restaurantShowAllAdapter;
    List<RestaurantShowAllModel> restaurantShowAllModelList;

    Toolbar toolbar;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_show_all);

        toolbar = findViewById(R.id.restaurant_show_all_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        String type = getIntent().getStringExtra("type");

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.restaurant_show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        restaurantShowAllModelList = new ArrayList<>();
        restaurantShowAllAdapter = new RestaurantShowAllAdapter(this,restaurantShowAllModelList);
        recyclerView.setAdapter(restaurantShowAllAdapter);



        if (type == null || type.isEmpty()){
            firestore.collection("Restaurant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot documentSnapshot :task.getResult().getDocuments()){
                            RestaurantShowAllModel restaurantShowAllModel = documentSnapshot.toObject(RestaurantShowAllModel.class);
                            restaurantShowAllModelList.add(restaurantShowAllModel);
                            restaurantShowAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        if (type != null && type.equalsIgnoreCase("veg")){
            firestore.collection("Restaurant_ShowAll").whereEqualTo("type","veg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot documentSnapshot :task.getResult().getDocuments()){
                            RestaurantShowAllModel restaurantShowAllModel = documentSnapshot.toObject(RestaurantShowAllModel.class);
                            restaurantShowAllModelList.add(restaurantShowAllModel);
                            restaurantShowAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        if (type != null && type.equalsIgnoreCase("Non-veg")){
            firestore.collection("Restaurant_ShowAll").whereEqualTo("type","Non-veg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot documentSnapshot :task.getResult().getDocuments()){
                            RestaurantShowAllModel restaurantShowAllModel = documentSnapshot.toObject(RestaurantShowAllModel.class);
                            restaurantShowAllModelList.add(restaurantShowAllModel);
                            restaurantShowAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }
}