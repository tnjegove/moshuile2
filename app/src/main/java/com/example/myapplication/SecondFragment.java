package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.example.myapplication.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment implements LoadImageTask.Listener{

    private FragmentSecondBinding binding;
    private SwipeButton swipeButton;
    private ImageView imageView;
    public static final String IMAGE_URL = "http://192.168.178.45/capture?_cb=1626625005526";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeButton = view.findViewById(R.id.swipe_button_fragment2);
        imageView = view.findViewById(R.id.jpegCaptureImage);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Toast.makeText(getActivity().getApplicationContext(), "Door unlocked", Toast.LENGTH_LONG);
                SystemClock.sleep(1000);
                ((MainActivity)getActivity()).setNewCard(false);
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        /*binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
        new LoadImageTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,IMAGE_URL);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onImageLoaded(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        Toast.makeText(getActivity().getApplicationContext(), "Error Loading Image !", Toast.LENGTH_SHORT).show();
    }
}