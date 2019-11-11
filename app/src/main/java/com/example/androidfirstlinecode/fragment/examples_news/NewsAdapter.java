package com.example.androidfirstlinecode.fragment.examples_news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidfirstlinecode.R;

import java.util.List;

/**
 * @author JinXin
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNewsList;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitleText = itemView.findViewById(R.id.news_title);
        }
    }

    public NewsAdapter(List<News> mNewsList) {
        this.mNewsList = mNewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                News news = mNewsList.get(holder.getAdapterPosition());
                if (NewsTitleFragment.isTwoPane) {
                    // 如果是双页模式，则刷新NewsContentFragment中的内容
                    NewContentFragment.refresh(news.getTitle(), news.getContent());
                } else {
                    // 如果是单页模式，则直接启动NewsContentActivity
                    NewsContentActivity.actionStart(viewGroup.getContext(),news.getTitle(), news.getContent());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        News news = mNewsList.get(i);
        viewHolder.newsTitleText.setText(news.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
