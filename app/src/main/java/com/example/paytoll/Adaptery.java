package com.example.paytoll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context pContext;
    private List<FetchPaymentsModelClass> pData;

    public Adaptery(Context pContext, List<FetchPaymentsModelClass> pData) {
        this.pContext = pContext;
        this.pData = pData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(pContext);
        v =inflater.inflate(R.layout.fetchpaymentsdata, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.id.setText(pData.get(position).getId());
        holder.status.setText(pData.get(position).getStatus());
        holder.tollamount.setText(pData.get(position).getAmount());
        holder.date.setText(pData.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return pData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView status;
        TextView tollamount;
        TextView date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.paymentid);
            status = itemView.findViewById(R.id.paymentstatus);
            tollamount = itemView.findViewById(R.id.tollchargesAmout);
            date = itemView.findViewById(R.id.transactiondate);
        }
    }
}
