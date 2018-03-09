package com.example.rxexamplesearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rxexamplesearch.POJO.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karthi on 9/3/2018.
 */

class UsersAdapterFilterable extends RecyclerView.Adapter<UsersAdapterFilterable.MyViewHolder> {
    private List<Result> usersList = new ArrayList<>();
    private Context context;

    public UsersAdapterFilterable(Context context, List<Result> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Result contact = usersList.get(position);
        holder.name.setText(contact.getName().getFirst() + " " + contact.getName().getLast());
        holder.email.setText(contact.getEmail());

        Glide.with(context)
                .load(contact.getPicture().getMedium())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return usersList.size();    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, email;
        public ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvw_Name);
            email = itemView.findViewById(R.id.tvw_email);
            thumbnail = itemView.findViewById(R.id.ico_profile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                }
            });
        }
    }
}
