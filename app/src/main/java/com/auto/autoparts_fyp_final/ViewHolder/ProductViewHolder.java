package com.auto.autoparts_fyp_final.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auto.autoparts_fyp_final.Interface.ItemClickListner;
import com.auto.autoparts_fyp_final.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView pname,description,price,category;
    public ImageView imageView;
    public ItemClickListner listner;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.product_image_display);
        pname=(TextView) itemView.findViewById(R.id.product_name_display);
        description=(TextView) itemView.findViewById(R.id.product_description_display);
        price=(TextView) itemView.findViewById(R.id.product_price_display);
        category=(TextView) itemView.findViewById(R.id.category_display);


    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner=listner;
    }

    @Override
    public void onClick(View view) {
        listner.onCick(view , getAdapterPosition(),false);

    }
}
