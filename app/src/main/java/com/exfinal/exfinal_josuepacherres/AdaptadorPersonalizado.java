package com.exfinal.exfinal_josuepacherres;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class AdaptadorPersonalizado extends RecyclerView.Adapter<AdaptadorPersonalizado.MyViewHolder> {

    private Context context;
    private List<Producto> listaProd = new ArrayList<>();

    public AdaptadorPersonalizado(Context context, List<Producto> listaProd){
        this.context = context;
        this.listaProd = listaProd;
    }

    @NonNull
    @Override
    public AdaptadorPersonalizado.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View vista = inflater.inflate(R.layout.fila,parent,false);
        return new MyViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizado.MyViewHolder holder, int position) {
        holder.filaProd.setText(listaProd.get(position).getNombre());
        holder.filaCantidad.setText(listaProd.get(position).getCantidad());
        holder.filaPrecio.setText(listaProd.get(position).getPrecio());
        holder.fila.setOnLongClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("pid", listaProd.get(position).getId()+"");
            intent.putExtra("pnombre", listaProd.get(position).getNombre()+"");
            intent.putExtra("pprecio", listaProd.get(position).getPrecio()+"");
            intent.putExtra("pcantidad", listaProd.get(position).getCantidad()+"");
            context.startActivity(intent);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return listaProd.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView filaProd, filaCantidad, filaPrecio;
        LinearLayout fila;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            filaProd = itemView.findViewById(R.id.ListaProd);
            filaCantidad = itemView.findViewById(R.id.ListaCantidad);
            filaPrecio = itemView.findViewById(R.id.ListaPrecio);
            fila = itemView.findViewById(R.id.fila);
        }
    }
}
