package com.example.chattingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AdapterMassage extends RecyclerView.Adapter<AdapterMassage.viewHolder> {
    private Context context;
    private ArrayList<MesaageModel> list;
    private AESDecrypt aesDecrypt = new AESDecrypt();

    public AdapterMassage(Context context, ArrayList<MesaageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final MesaageModel model = list.get(position);
        final String massage= model.getMessageText();
        final String user= model.getMessageUser();
        final String time= model.getMessageTime();

        try {
        holder.nama.setText(user);
        holder.pesan.setText(aesDecrypt.AESDEcryptt(massage));
        holder.waktu.setText(time);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView nama, pesan, waktu;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.message_user);
            pesan = itemView.findViewById(R.id.message_text);
            waktu = itemView.findViewById(R.id.message_time);
        }
    }
}
