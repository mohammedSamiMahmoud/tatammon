package com.example.tatmon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.Model.Article;
import com.example.tatmon.R;

import java.util.List;

public class DoctorArticlesAdapter extends RecyclerView.Adapter<DoctorArticlesAdapter.ArticleViewHolder> {

    Context mContext;
    List<Article> articles;

    public DoctorArticlesAdapter(Context mContext, List<Article> articles) {
        this.mContext = mContext;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ArticleViewHolder(inflater.inflate(R.layout.doctor_article_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.header.setText(article.getHead());
        holder.name.setText(article.getDoc_name());
        holder.date.setText(article.getDate());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView header, date, name;

        public ArticleViewHolder(@NonNull View view) {
            super(view);
            header = view.findViewById(R.id.header);
            date = view.findViewById(R.id.date);
            name = view.findViewById(R.id.name);
        }
    }
}
