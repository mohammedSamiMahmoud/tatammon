package com.example.tatmon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.R;

import java.util.List;

public class DoctorWordAdapter extends RecyclerView.Adapter<DoctorWordAdapter.WordViewHolder> {
    private Context mContext;
    private List<String> words;

    public DoctorWordAdapter(Context mContext, List<String> words) {
        this.mContext = mContext;
        this.words = words;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new WordViewHolder(inflater.inflate(R.layout.doctor_word_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.name.setText(words.get(position));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        TextView name, num;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            System.out.println(name.getText());
        }
    }
}
