package com.derynm.uas;

    import android.annotation.SuppressLint;
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


import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private ArrayList<ModelWisata> dataWisata;
    Context context;
    OnNoteListener mOnNoteListener;


    RecycleViewAdapter(ArrayList<ModelWisata> dataWisata, Context context, OnNoteListener onNoteListener){
        this.dataWisata = dataWisata;
        this.context = context;
        this.mOnNoteListener = onNoteListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paket,parent,false);
        return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nama_wisata.setText(dataWisata.get(position).getNama());
        holder.harga_wisata.setText(dataWisata.get(position).getHarga());

        Glide.with(context)
                .load(dataWisata.get(position).getGambar())
                .thumbnail(0.5f)
                .into(holder.gambar_wisata);

        holder.nama_wisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.nama_wisata.getContext(),DetailWisata.class);
                intent.putExtra("Gambar",dataWisata.get(position).getGambar());
                intent.putExtra("Nama",dataWisata.get(position).getNama());
                intent.putExtra("Deskripsi",dataWisata.get(position).getDeskripsi());
                holder.nama_wisata.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataWisata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nama_wisata,harga_wisata;
        ImageView gambar_wisata;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            nama_wisata = (TextView) itemView.findViewById(R.id.nama_paket);
            harga_wisata =  (TextView) itemView.findViewById(R.id.harga_paket);
            gambar_wisata =  (ImageView) itemView.findViewById(R.id.gambar_paket);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
