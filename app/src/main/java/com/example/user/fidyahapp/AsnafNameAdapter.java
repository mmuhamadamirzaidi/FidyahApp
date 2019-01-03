package com.example.user.fidyahapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.fidyahapp.Model.AsnafDetails;

import java.util.ArrayList;

/*Well adapters in Android are basically a bridge between the
UI components and the data source that fill data into the UI Component
For example, Lists (UI Component) get populated by using a list adapter, from a data source array.*/
public class AsnafNameAdapter extends RecyclerView.Adapter<AsnafNameAdapter.MyViewHolder> {
    Context context;
    ArrayList<AsnafDetails> asnafDetails;

    public AsnafNameAdapter(Context context, ArrayList<AsnafDetails> asnafDetails) {
        this.context = context;
        this.asnafDetails = asnafDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.asnaf_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.asnafName.setText("Asnaf name: " + asnafDetails.get(i).getAsnafName());
        myViewHolder.asnaflat.setText("Asnaf Latitude: " + String.valueOf(asnafDetails.get(i).getLatitude()));
        myViewHolder.asnafLong.setText("Asnaf Longitude: " + String.valueOf(asnafDetails.get(i).getLongitude()));

        if (StaticData.isAdmin) {
            myViewHolder.itemList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AsnafPageDetails.start(context, asnafDetails.get(i).getAsnafName(), asnafDetails.get(i).getLatitude(), asnafDetails.get(i).getLongitude(), asnafDetails.get(i).getKeyValue());
                }
            });
        } else {
            myViewHolder.itemList.setOnClickListener(null);
        }
    }


    @Override
    public int getItemCount() {
        return asnafDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView asnafName, asnaflat, asnafLong;
        ConstraintLayout itemList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            asnafName = itemView.findViewById(R.id.txtasnafname);
            asnaflat = itemView.findViewById(R.id.txtasnafname2);
            asnafLong = itemView.findViewById(R.id.txtasnafname3);
            itemList = itemView.findViewById(R.id.item_list);
        }
    }
}
