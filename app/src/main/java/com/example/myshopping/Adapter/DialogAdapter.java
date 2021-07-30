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
import com.example.myshopping.R;
import com.example.myshopping.Model.Category;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogViewHolder> {

    Context context;
    List<Chat> chatList;

    public DialogAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.contact_item_layout, parent, false);
        return new DialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder holder, int position) {
        Glide.with(context).load(chatList.get(position).getImage()).into(holder.imagechat);
        holder.chatname.setText(chatList.get(position).getHisname());
        holder.lastmsg.setText(chatList.get(position).getLastMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("chatID", chatList.get(position).getChatID());
                i.putExtra("hisID", chatList.get(position).getHisid());
                i.putExtra("name", chatList.get(position).getHisname());
                i.putExtra("hisImage", chatList.get(position).getImage());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public  static class DialogViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imagechat;
        TextView chatname,lastmsg;
        public DialogViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechat = itemView.findViewById(R.id.avatarChat);
            chatname = itemView.findViewById(R.id.chatname);
            lastmsg = itemView.findViewById(R.id.lastmsg);
        }
    }

}

