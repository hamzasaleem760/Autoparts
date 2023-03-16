package com.auto.autoparts_fyp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.auto.autoparts_fyp_final.Model.VendorOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewOrderActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }//on create


    @Override
    protected void onStart()
    {
        super.onStart();



        FirebaseRecyclerOptions<VendorOrders> options =
                new FirebaseRecyclerOptions.Builder<VendorOrders>()
                        .setQuery(ordersRef, VendorOrders.class)
                        .build();

        FirebaseRecyclerAdapter<VendorOrders,VendorsOrdersViewHolder> adapter
                =new FirebaseRecyclerAdapter<VendorOrders, VendorsOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VendorsOrdersViewHolder holder, final int position, @NonNull final VendorOrders model)
            {
                holder.userName.setText("Name: " + model.getName());
                holder.userPhoneNumber.setText("Phone: " + model.getPhone());
                holder.userTotalPrice.setText("Total Amount =  $" + model.getTotalAmount());
                holder.userDateTime.setText("Order at: " + model.getDate() + "  " + model.getTime());
                holder.userShippingAddress.setText("Shipping Address: " + model.getAddress() + ", " + model.getCity());


                holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        String uID = getRef(position).getKey();

                        Intent intent = new Intent(ViewOrderActivity.this, ViewOrderDetailsActivity.class);
                        intent.putExtra("uid", uID);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public VendorsOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                return new VendorsOrdersViewHolder(view);
            }
        };


        ordersList.setAdapter(adapter);
        adapter.startListening();

    }//on start



    public static class VendorsOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        public Button ShowOrdersBtn;


        public VendorsOrdersViewHolder(View itemView)
        {
            super(itemView);


            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            ShowOrdersBtn = itemView.findViewById(R.id.show_all_products_btn);
        }
    }




    private void RemoverOrder(String uID)
    {
        ordersRef.child(uID).removeValue();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ViewOrderActivity.this, VendorMainActivity.class);
        startActivity(intent);
    }
}
