package com.nytimes.assignment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nytimes.assignment.R;
import com.nytimes.assignment.model.Result;
import com.nytimes.assignment.listener.OnArticleItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Result> articleList;
    private final Context context;
    private final OnArticleItemClickListener clickListener;

    public ArticlesAdapter(Context context, List<Result> articleList, OnArticleItemClickListener clickListener) {
        this.articleList = articleList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_article, viewGroup, false);
        return new RecentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecentViewHolder itemViewHolder = (RecentViewHolder) holder;
        final Result article = articleList.get(holder.getAdapterPosition());
        itemViewHolder.txtArticleName.setText(article.getTitle());
        itemViewHolder.txtArticleBy.setText(article.getByline());
        itemViewHolder.txtDate.setText(article.getPublishedDate());
        try {
            Glide.with(context).load(article.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(itemViewHolder.ivArticleImage);
        } catch (Exception e) {
            Log.e(ArticlesAdapter.class.getName(), e.getMessage());
        }

        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class RecentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_article_name)
        TextView txtArticleName;

        @BindView(R.id.txt_article_by)
        TextView txtArticleBy;

        @BindView(R.id.txt_date)
        TextView txtDate;

        @BindView(R.id.iv_article)
        ImageView ivArticleImage;


        RecentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}