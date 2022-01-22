package uz.mobiler.wallpaperappg2122;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RandomFragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private int mParam1;

    public RandomFragment2() {
    }

    public static RandomFragment2 newInstance(int param1) {
        RandomFragment2 fragment = new RandomFragment2();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random2, container, false);
    }
}