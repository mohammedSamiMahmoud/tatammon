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
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Doctor.ArticleActivity;
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
        new DownloadImage(holder.image).execute(article.getImage());
        holder.dName.setText(article.getDocName());
        holder.header.setText(article.getHeader());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView header, dName;
        ImageView image;

        public ArticleViewHolder(@NonNull View view) {
            super(view);
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.imageView3);
            header = itemView.findViewById(R.id.header);
            dName = itemView.findViewById(R.id.dName);
        }

        @Override
        public void onClick(View v) {
            Article a = articles.get(getAdapterPosition());
            SharedPrefManager.getInstance(mContext)
                    .setArticle(a);
            Intent intent = new Intent(mContext, ArticleActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
}
