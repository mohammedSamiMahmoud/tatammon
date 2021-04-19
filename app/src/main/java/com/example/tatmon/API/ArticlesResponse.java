package com.example.tatmon.API;

import com.example.tatmon.Model.Article;
import com.example.tatmon.Model.Doctor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticlesResponse {
    @SerializedName("error")
    private boolean err;
    @SerializedName("articles")
    private List<Article> articles;

    public ArticlesResponse(boolean err, List<Article> articles) {
        this.err = err;
        this.articles = articles;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public List<Article> getArticles() {
        return articles;
    }
}

