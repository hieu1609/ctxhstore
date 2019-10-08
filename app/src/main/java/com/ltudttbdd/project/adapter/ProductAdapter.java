package com.ltudttbdd.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.activity.ProductDetailActivity;
import com.ltudttbdd.project.model.Product;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> arrayProduct;

    public ProductAdapter(Context context, ArrayList<Product> arrayProduct) {
        this.context = context;
        this.arrayProduct = arrayProduct;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_new_product,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = arrayProduct.get(position);
        holder.txtProductName.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtProductPrice.setText("Giá: " + decimalFormat.format(product.getPrice()) + " VNĐ");
//        Locale locale = new Locale("vi","VN");
//        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
//        txtTotalPrice.setText(fmt.format(total));
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.errorimg)
                .into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return arrayProduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgProduct;
        public TextView txtProductName, txtProductPrice;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imageviewsanpham);
            txtProductPrice = itemView.findViewById(R.id.textviewgiasanpham);
            txtProductName = itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("thongtinsanpham", arrayProduct.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToastShort(context, arrayProduct.get(getPosition()).getProductName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
