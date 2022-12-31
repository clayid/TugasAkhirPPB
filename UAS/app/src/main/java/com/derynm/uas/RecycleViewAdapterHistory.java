package com.derynm.uas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapterHistory extends RecyclerView.Adapter<RecycleViewAdapterHistory.ViewHolder>{

    private ArrayList<ModelHistory> dataHistory;

    RecycleViewAdapterHistory(ArrayList<ModelHistory> dataHistory){
        this.dataHistory = dataHistory;
    }
    @Override
    public RecycleViewAdapterHistory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapterHistory.ViewHolder holder, int position) {
        holder.id_pembayaran.setText("ID Pembayaran : "+dataHistory.get(position).getId_pembayaran());
        holder.jumlah_paket.setText("Jumlah Paket : "+dataHistory.get(position).getTotal_paket());
        holder.total_pembayaran.setText("Total Bayar : "+dataHistory.get(position).getTotal_pembayaran());
        holder.waktu.setText(dataHistory.get(position).getWaktu());
        holder.status.setText(dataHistory.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return dataHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_pembayaran,jumlah_paket,status,total_pembayaran,waktu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_pembayaran = (TextView) itemView.findViewById(R.id.id_pembayaran_history);
            jumlah_paket = (TextView) itemView.findViewById(R.id.jumlah_paket_history);
            status = (TextView) itemView.findViewById(R.id.status_history);
            total_pembayaran = (TextView) itemView.findViewById(R.id.total_pembayaran_history);
            waktu = (TextView) itemView.findViewById(R.id.waktu_history);
        }
    }
}
