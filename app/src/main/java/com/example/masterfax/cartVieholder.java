package com.example.masterfax;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartVieholder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView product_name,product_quantity,product_price;

ItemClickListner itemClickListner;

    public cartVieholder(@NonNull View itemView) {
        super(itemView);
        product_name=itemView.findViewById(R.id.product_name);
        product_quantity=itemView.findViewById(R.id.product_quantity);
        product_price=itemView.findViewById(R.id.product_price);
    }

    @Override
    public void onClick(View v)
    {
        itemClickListner.onclick(v,getAdapterPosition(),false);
    }

    public void ItemClickListner(ItemClickListner itemClickListner)
        {
            this.itemClickListner=itemClickListner;
        }
}
