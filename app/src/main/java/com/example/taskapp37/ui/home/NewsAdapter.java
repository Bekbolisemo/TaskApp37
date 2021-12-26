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
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<News> list;
    private OnItemClickListener onItemClickListener;

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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0,news);
        notifyItemInserted(0);
    }
    public void updateItem(News news) {
        int index = list.indexOf(news);
        list.set(index,news);
        notifyItemChanged(index);
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

    public void addItems(List<News> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;
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
