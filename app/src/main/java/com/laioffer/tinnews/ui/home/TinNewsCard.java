package com.laioffer.tinnews.ui.home;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.model.Article;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.squareup.picasso.Picasso;

@Layout(R.layout.tin_news_card)
public class TinNewsCard {

    @View(R.id.news_image)
    private ImageView image;

    @View(R.id.news_title)
    private TextView newsTitle;

    @View(R.id.news_description)
    private TextView newsDescription;

    private final Article article;
    private final OnSwipeListener onSwipeListener;

    public TinNewsCard(Article news, OnSwipeListener OnSwipeListener) {
        this.onSwipeListener = OnSwipeListener;
        this.article = news;
    }

    @Resolve
    private void onResolved() {
        if (article.url == null || article.url.isEmpty()) {
            image.setImageResource(R.drawable.ic_empty_image);
        }else {
        Picasso.get().load(article.urlToImage).into(image);
           // Picasso.get().load("https://i.dlpng.com/static/png/476392_preview.png").into(image);
        }
        newsTitle.setText(article.title);
        newsDescription.setText(article.description);

    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        onSwipeListener.onDislike(article);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
        article.favorite = true;
        onSwipeListener.onLike(article);
    }

    interface OnSwipeListener {
        void onLike(Article news);
        void onDislike(Article news);
    }
}
