package com.example.tatmon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.DownloadImage;
import com.example.tatmon.ChatActivity;
import com.example.tatmon.Model.P_C;
import com.example.tatmon.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context mContext;
    List<P_C> p_cs;

    public UserAdapter(Context mContext, List<P_C> p_cs) {
        this.mContext = mContext;
        this.p_cs = p_cs;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.user_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.name.setText(p_cs.get(position).getDname());
        holder.name.setText(p_cs.get(position).getDspecialization());
        new DownloadImage(holder.d_p).execute(p_cs.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return p_cs.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, spc;
        ImageView d_p;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.userName);
            spc = itemView.findViewById(R.id.spc);
            d_p = itemView.findViewById(R.id.d_p);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra("userId2", p_cs.get(getAdapterPosition()).getId());
            intent.putExtra("name", p_cs.get(getAdapterPosition()).getDname());
            mContext.startActivity(intent);
        }
    }
}
