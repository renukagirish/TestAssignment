package com.nytimes.assignment.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nytimes.assignment.R;
import com.nytimes.assignment.Constants;
import com.nytimes.assignment.api.Json;
import com.nytimes.assignment.model.MediaMetadata;
import com.nytimes.assignment.model.Result;

import com.nytimes.assignment.databinding.ActivityDetailsBinding;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_article_image)
    ImageView ivImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        String article = getIntent().getStringExtra(Constants.KEY_ARTICLE);
        Result result = Json.getGson().fromJson(article, Result.class);
        Log.e(DetailsActivity.class.getName(), result.getTitle());
        binding.setArticle(result);

        try {
            List<MediaMetadata> media = result.getMedia().get(0).getMediaMetadata();
            if (media != null && media.size() > 0) {
                String url = media.get(media.size() - 1).getUrl();
                Glide.with(getApplicationContext()).load(url).into(ivImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
