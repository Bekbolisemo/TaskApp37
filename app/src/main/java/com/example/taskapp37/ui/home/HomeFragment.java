package com.example.taskapp37.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.taskapp37.App;
import com.example.taskapp37.R;
import com.example.taskapp37.databinding.FragmentHomeBinding;
import com.example.taskapp37.interfaces.OnItemClickListener;
import com.example.taskapp37.modals.News;

import java.util.List;

public class HomeFragment extends Fragment {
    private NewsAdapter adapter;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
        List<News> list = App.getInstance().getDatabase().newsDao().getAll();
        adapter.addItems(list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int p) {
                News news = adapter.getItem(p);
                openFragment(news);
            }

            @Override
            public void onLongClick(int p) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setNeutralButton("Отмена",((dialog, which) -> {}));
                builder.setPositiveButton("Удалить", (dialog, which) -> {
                    News news = adapter.getItem(p);
                    adapter.removeItem(news, p);
                    App.getInstance().getDatabase().newsDao().delete(news);
                });
                @SuppressLint("InflateParams") ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.item_alert_dialog, null);
                builder.setView(constraintLayout);
                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });

        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.fab.setOnClickListener(v -> openFragment(null));
       getParentFragmentManager().setFragmentResultListener("rk_news_add", getViewLifecycleOwner(), (requestKey, result) -> {
           News news = (News) result.getSerializable("news");
           Log.e("Home", "text= " + news.getTitle());
           adapter.addItem(news);
       });
        getParentFragmentManager().setFragmentResultListener("rk_news_update", getViewLifecycleOwner(), (requestKey, result) -> {
            News news = (News) result.getSerializable("news");
            Log.e("Home", "text= " + news.getTitle());
            adapter.updateItem(news);
        });
        return root;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
    }

    private void initList() {
        binding.recyclerView.setAdapter(adapter);

    }

    private void openFragment(News news) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        navController.navigate(R.id.newsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sort){
            getSotrted();
        }
        return super.onOptionsItemSelected(item);

    }

    private void getSotrted() {
        List<News> list = App.getInstance().getDatabase().newsDao().sort();
        adapter.addItems(list);

    }
}