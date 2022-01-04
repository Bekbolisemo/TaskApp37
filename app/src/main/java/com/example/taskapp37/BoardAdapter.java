package com.example.taskapp37;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp37.databinding.ItemBoardBinding;
import com.example.taskapp37.interfaces.OnBoardStartClickListener;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private final String[] title = new String[]{"1", "2", "3"};
    private final String[] decs = new String[]{"Description1", "Description2", "Description3"};
    private final int[] lottie = new int[]{R.raw.settings,R.raw.lock,R.raw.car};
    private ItemBoardBinding binding;


    public void setClickListener(OnBoardStartClickListener clickListener) {
        this.clickListener = clickListener;
    }


    private OnBoardStartClickListener clickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return title.length;

    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding.ptnStart.setOnClickListener(v -> clickListener.onStartClick());
        }

        public void bind(int position) {
            binding.textTitle.setText(title[position]);
            binding.lottieView.setAnimation(lottie[position]);
            binding.textDesc.setText(decs[position]);
            if (position == title.length - 1) {
                binding.ptnStart.setVisibility(View.VISIBLE);
            } else {
                binding.ptnStart.setVisibility(View.INVISIBLE);
            }

        }
    }

}
