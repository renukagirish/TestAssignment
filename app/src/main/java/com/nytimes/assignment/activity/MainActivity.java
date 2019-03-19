package com.nytimes.assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nytimes.assignment.R;
import com.nytimes.assignment.Constants;
import com.nytimes.assignment.adapter.ArticlesAdapter;
import com.nytimes.assignment.model.Article;
import com.nytimes.assignment.api.ApiManager;
import com.nytimes.assignment.api.Json;
import com.nytimes.assignment.model.Result;
import com.nytimes.assignment.listener.OnArticleItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnArticleItemClickListener {

    @BindView(R.id.rv_article)
    RecyclerView rvArticles;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private List<Result> articlesList = new ArrayList<>();
    private ArticlesAdapter articleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rvArticles.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvArticles.getContext(),
                new LinearLayoutManager(this).getOrientation());
        rvArticles.addItemDecoration(dividerItemDecoration);
        rvArticles.setItemAnimator(new DefaultItemAnimator());

        articleAdapter = new ArticlesAdapter(getApplicationContext(), articlesList, this);
        rvArticles.setAdapter(articleAdapter);

        renderArticles();
    }

    private void renderArticles() {
        new ApiManager().getArticles(Constants.PERIOD, new ApiManager.ApiCallback<Article>() {
            @Override
            public void success(Article response) {
                renderData(response);
            }

            @Override
            public void failure(int responseCode) {
                Toast.makeText(getApplicationContext(), getString(R.string.errorCantConnect), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void renderData(Article response) {
        progressBar.setVisibility(View.GONE);
        rvArticles.setVisibility(View.VISIBLE);

        if (!(response.getResults() == null || response.getResults().isEmpty())) {
            articlesList.clear();
            articlesList.addAll(response.getResults());
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(Result article) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(Constants.KEY_ARTICLE, Json.getGson().toJson(article));
        startActivity(intent);
    }
}