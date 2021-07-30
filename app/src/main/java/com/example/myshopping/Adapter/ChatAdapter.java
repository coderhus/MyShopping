package com.example.myshopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myshopping.Activity.DetailsActivity;
import com.example.myshopping.Activity.MessageActivity;
import com.example.myshopping.Model.Chat;
import com.example.myshopping.Model.MessageModel;
import com.example.myshopping.R;
import com.example.myshopping.Model.Category;
import com.example.myshopping.SupportCode.SupportCode;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    Context context;
    List<MessageModel> messList;
    SupportCode sp = new SupportCode();
    String myID = sp.getUID();
    public ChatAdapter(Context context, List<MessageModel> messList) {
        this.context = context;
        this.messList=messList;
    }

    @NonNull
    @Override

    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =null;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.right_item_layout, parent, false);
                break;
            default:
                view = LayoutInflater.from(context).inflate(R.layout.left_item_layout, parent, false);
                break;
        }

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        MessageModel a = messList.get(position);
        String time = sp.getTimeAgo(Long.parseLong(a.getDate()));
        switch (getItemViewType(position)){
            case 1:
               // Glide.with(context).load(a.get).into(holder.leftava);
                holder.left_mes_data.setText(a.getMessage());
                holder.Lefttime.setText(time);
                break;
            default:
                // Glide.with(context).load(a.get).into(holder.rightAva);

                holder.right_mes_data.setText(a.getMessage());
                holder.righttime.setText(time);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messList.size();
    }
    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = messList.get(position);
        if (myID.equals(messageModel.getSender())) {
                return 0;
        } else {
                return 1;
        }
    }
    public  static class ChatViewHolder extends RecyclerView.ViewHolder{

        CircleImageView leftava,rightAva;
        TextView left_mes_data,Lefttime,right_mes_data,righttime;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            leftava = itemView.findViewById(R.id.Leftava);
            rightAva = itemView.findViewById(R.id.rightAva);
            left_mes_data = itemView.findViewById(R.id.left_mes_data);
            Lefttime = itemView.findViewById(R.id.Lefttime);
            right_mes_data = itemView.findViewById(R.id.right_mes_data);
            righttime = itemView.findViewById(R.id.righttime);
        }
    }

}

