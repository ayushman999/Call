package com.android.internal.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.Model.model;
import com.android.internal.call.MainActivity;
import com.android.internal.call.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    ArrayList<model> contacts=new ArrayList<>();
    contactItemClicked listener;

    public ContactAdapter(ArrayList<model> contacts,contactItemClicked listener) {
        this.listener=listener;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        ContactHolder holder=new ContactHolder(myView);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(contacts.get(holder.getAdapterPosition()).getPhonenum());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactHolder holder, int position) {
        holder.contact.setText(contacts.get(position).getContactName());
        holder.count.setText(contacts.get(position).getPos()+"");
        holder.contact.setOnClickListener(v -> {
            if(holder.layout.getVisibility()==View.VISIBLE)
            {
                holder.layout.setVisibility(View.GONE);
            }
            else
            {
                holder.layout.setVisibility(View.VISIBLE);
            }
        });
        holder.plus.setOnClickListener(v -> {
            listener.plus(contacts.get(position),position);
        });
        holder.minus.setOnClickListener(v->{
            listener.minus(contacts.get(position),position);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView contact;
        TextView count;
        LinearLayout layout;
        Button plus,minus;
        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            count=(TextView) itemView.findViewById(R.id.num);
            contact=(TextView) itemView.findViewById(R.id.textView);
            layout=(LinearLayout) itemView.findViewById(R.id.plus_minus_box);
            plus=(Button) itemView.findViewById(R.id.plus);
            minus=(Button) itemView.findViewById(R.id.minus);
        }
    }
}
