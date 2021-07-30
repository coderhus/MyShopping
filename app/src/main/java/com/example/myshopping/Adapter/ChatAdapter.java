package com.example.myshopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myshopping.Model.Messages;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;



public class ChatAdapter extends RecyclerView.Adapter {

    Context context;
    List<Messages> messList;

    int ITEM_SEND=1;
    int ITEM_RECIEVE=2;

    SupportCode sp = new SupportCode();
    String myID = sp.getUID();

    public ChatAdapter(Context context, List<Messages> messList) {
        this.context = context;
        this.messList=messList;
    }

    @NonNull
    @Override

    public RecyclerView.ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.recieverchatlayout,parent,false);
            return new RecieverViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages messages = messList.get(position);
        String time = sp.getTimeAgo(Long.parseLong(messages.getDate()));

        if(holder.getClass()==SenderViewHolder.class)
        {
            SenderViewHolder viewHolder=(SenderViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(time);
        }
        else
        {
            RecieverViewHolder viewHolder=(RecieverViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return messList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Messages messages= messList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSender()))

        {
            return  ITEM_SEND;
        }
        else
        {
            return ITEM_RECIEVE;
        }
    }










    class SenderViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewmessaage;
        TextView timeofmessage;


        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewmessaage;
        TextView timeofmessage;


        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }




}