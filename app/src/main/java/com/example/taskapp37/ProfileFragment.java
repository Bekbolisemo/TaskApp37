package com.example.taskapp37;

import android.app.AlertDialog;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taskapp37.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private boolean change = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Prefs prefs = new Prefs(requireContext());
        galileeClick(prefs);
        saveUserName(prefs);


        if (!prefs.getImageUser().equals("")) {
            Glide.with(binding.imageUser).load(prefs.getImageUser()).circleCrop().into(binding.imageUser);
            change = true;
        }

    }


    private void saveUserName(Prefs prefs) {
        binding.userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.saveUserName(s.toString());
            }
        });

        binding.userName.setText(prefs.getUserName());
    }

    private void galileeClick(Prefs prefs) {
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    Glide.with(binding.imageUser).load(uri).circleCrop().into(binding.imageUser);
                    prefs.saveImageUser(uri);
                    change = true;


                });
        binding.imageUser.setOnClickListener(v -> {
            if (!prefs.getImageUser().equals("")) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                Bundle bundle = new Bundle();
                bundle.putString("kay1", prefs.getImageUser());
                navController.navigate(R.id.imageFragment, bundle);

            } else
                Toast.makeText(requireContext(), "Фотография отсутствует", Toast.LENGTH_LONG).show();
        });

        binding.btnCamera.setOnClickListener(v -> {
            if (change) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setNeutralButton("Заменить", (dialog, which) -> mGetContent.launch("image/*"));
                builder.setPositiveButton("Удалить", (dialog, which) -> {
                    binding.imageUser.setImageResource(R.drawable.ic_baseline_account_circle_24);
                    prefs.deleteUserImage();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                change = false;
            } else {
                mGetContent.launch("image/*");

            }
        });
    }


}