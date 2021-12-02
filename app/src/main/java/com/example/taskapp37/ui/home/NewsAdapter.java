package com.example.taskapp37.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp37.databinding.ItemNewsBinding;
import com.example.taskapp37.interfaces.OnItemClickListener;
import com.example.taskapp37.modals.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<News> list;
    private OnItemClickListener onItemClickListener;
    private ItemNewsBinding binding;

    public NewsAdapter() {
        list = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position),onItemClickListener);
        if (position % 2 == 0){
            binding.title.setBackgroundColor(Color.GREEN);

        }else {
            binding.title.setBackgroundColor(Color.WHITE);
        }
        holder.bind(list.get(position),onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0,news);
        notifyItemInserted(0);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public News getItem(int p) {
        return list.get(p);
    }

    public void removeItem(News news, int p) {
        this.list.remove(news);
        notifyItemRemoved(p);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder( @NonNull ItemNewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(v -> {
                onItemClickListener.onClick(getAdapterPosition());
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(News news , OnItemClickListener onItemClickListener) {
            binding.title.setText(news.getTitle());
        }
    }
}
