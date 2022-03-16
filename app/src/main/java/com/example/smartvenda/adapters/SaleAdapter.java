package com.example.smartvenda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartvenda.R;
import com.example.smartvenda.model.Sale;

import java.util.List;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.MyViewHolder> {

    private List<Sale> saleList;

    public SaleAdapter(List<Sale> saleList) {
        this.saleList = saleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemSale = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_item, parent, false);

        return new MyViewHolder(itemSale);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Sale sale = saleList.get(position);

        holder.buyer.setText(sale.getBuyer());

        String price = "R$ " + sale.getValue();
        holder.value.setText(price);
    }

    @Override
    public int getItemCount() {
        return this.saleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView buyer;
        private TextView value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            buyer = itemView.findViewById(R.id.buyer_text);
            value = itemView.findViewById(R.id.price_text);
        }
    }
}
