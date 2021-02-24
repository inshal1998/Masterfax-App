package com.example.masterfax;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class products_Adapter extends RecyclerView.ViewHolder
{

    public TextView title,price,category,description;
    public ImageView img;

    public products_Adapter(@NonNull View itemView) {
        super(itemView);
        title=(TextView) itemView.findViewById(R.id.title);
        price=(TextView) itemView.findViewById(R.id.price);
        category=(TextView) itemView.findViewById(R.id.category);
        img=(ImageView) itemView.findViewById(R.id.img);
        description=(TextView) itemView.findViewById(R.id.description);

    }
}
