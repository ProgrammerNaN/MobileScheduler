package com.example.teleg.programm.serverSide.News;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teleg.programm.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.NewsViewHolder> {
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_item_news_item, viewGroup, false);
        NewsViewHolder nvh = new NewsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        newsViewHolder.title.setText(news.get(i).getTitle());
        newsViewHolder.pubDate.setText(news.get(i).getPubDate());
        newsViewHolder.content.setText(news.get(i).getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    List<ItemNews> news;

    public RVAdapter(List<ItemNews> news) {
        this.news = news;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        CardView newsCardView;
        TextView title;
        TextView pubDate;
        TextView content;

        NewsViewHolder(View itemView) {
            super(itemView);

            newsCardView = (CardView)itemView.findViewById(R.id.newsCardView);
            title = (TextView)itemView.findViewById(R.id.myImageViewText);
            pubDate = (TextView)itemView.findViewById(R.id.txtPubDate);
            content = (TextView)itemView.findViewById(R.id.txtContent);
        }

    }
}
