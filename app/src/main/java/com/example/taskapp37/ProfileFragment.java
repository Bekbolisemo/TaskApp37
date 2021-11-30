package com.example.taskapp37;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taskapp37.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private boolean change = false;
    private boolean change2 = true;
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri == null) {
                        change = false;
                    } else {
                        binding.imageUser.setImageURI(uri);
                        change2 = true;
                    }

                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galileeClick();
    }

    private void galileeClick() {
        binding.imageUser.setOnClickListener(v -> {
            if (change && change2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setNeutralButton("Заменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGetContent.launch("image/*");
                    }
                });
                builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.imageUser.setImageResource(R.drawable.ic_baseline_account_circle_24);
                        change2 = false;
                    }
                });
                AlertDialog dialog =builder.create();
                dialog.show();
            } else {
                mGetContent.launch("image/*");
                change = true;

            }
        });
    }
}