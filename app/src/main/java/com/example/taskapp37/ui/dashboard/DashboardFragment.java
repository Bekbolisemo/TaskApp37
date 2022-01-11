package com.example.taskapp37.ui.dashboard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.taskapp37.databinding.FragmentDashboardBinding;
import com.example.taskapp37.modals.News;
import com.example.taskapp37.ui.home.NewsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DashboardFragment extends Fragment {


    private FragmentDashboardBinding binding;
    private NewsAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
        getData();
    }

    private void getData() {
        FirebaseFirestore.getInstance()
                .collection("news")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
                if (!isOnline(requireContext())) {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireContext(), "Новость сохранится только после подключения к интернету", Toast.LENGTH_LONG).show();
                    binding.recyclerView.setVisibility(View.INVISIBLE);
                    binding.errorText.setVisibility(View.VISIBLE);
                    binding.lottie.setVisibility(View.VISIBLE);
                    binding.btnRestart.setVisibility(View.VISIBLE);
                }else{
                    binding.progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
        })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot snapshots) {
                        List<News> list = snapshots.toObjects(News.class);
                        adapter.addItems(list);

                    }
                });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.errorText.setVisibility(View.INVISIBLE);
        binding.btnRestart.setVisibility(View.GONE);
        binding.lottie.setVisibility(View.GONE);
        binding.btnRestart.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.errorText.setVisibility(View.INVISIBLE);
            binding.btnRestart.setVisibility(View.GONE);
             binding.lottie.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            getData();
        });
        binding.recyclerView.setAdapter(adapter);

    }
    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }else return false;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}