package com.example.taskapp37;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taskapp37.databinding.FragmentNewsBinding;
import com.example.taskapp37.modals.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private News news;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.progressBar.setVisibility(View.GONE);
        news = (News) requireArguments().getSerializable("news");


        if (news != null)
            binding.editText.setText(news.getTitle());

        binding.btnSave.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            save();
        });
    }

    private void save() {
        String text = binding.editText.getText().toString().trim();
        Bundle bundle = new Bundle();
        if (news == null) {
            news = new News(text, System.currentTimeMillis());
            bundle.putSerializable("news", news);
            getParentFragmentManager().setFragmentResult("rk_news_add", bundle);
            App.getInstance().getDatabase().newsDao().insert(news);
            addToFireStore();
        } else {
            news.setTitle(text);
            bundle.putSerializable("news", news);
            getParentFragmentManager().setFragmentResult("rk_news_update", bundle);
            App.getInstance().getDatabase().newsDao().update(news);

        }


    }

    private void addToFireStore() {
        FirebaseFirestore.getInstance()
                .collection("news")
                .add(news).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show();
                }
                close();
            }
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}