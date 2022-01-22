package uz.mobiler.wallpaperappg2122.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.mobiler.wallpaperappg2122.adapters.CategoryAdapter;
import uz.mobiler.wallpaperappg2122.databinding.FragmentHomeBinding;
import uz.mobiler.wallpaperappg2122.databinding.ItemTabBinding;
import uz.mobiler.wallpaperappg2122.models.Result;
import uz.mobiler.wallpaperappg2122.models.Root;
import uz.mobiler.wallpaperappg2122.retrofit.ApiClient;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private CategoryAdapter categoryAdapter;
    private List<String> titleList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadCategories();
        categoryAdapter = new CategoryAdapter(this, titleList);
        binding.viewPager.setAdapter(categoryAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                tab.setText(titleList.get(position));
                ItemTabBinding itemTabBinding = ItemTabBinding.inflate(getLayoutInflater());
                itemTabBinding.tv.setText(tab.getText());
                if(position == 0)
                {
                    itemTabBinding.circle.setVisibility(View.VISIBLE);
                    itemTabBinding.tv.setTextColor(Color.WHITE);
                }
                else
                {
                    itemTabBinding.circle.setVisibility(View.INVISIBLE);
                    itemTabBinding.tv.setTextColor(Color.parseColor("#AFADAD"));
                }
                tab.setCustomView(itemTabBinding.getRoot());
            }
        }).attach();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ItemTabBinding itemTabBinding = ItemTabBinding.bind(tab.getCustomView());
                itemTabBinding.tv.setTextColor(Color.WHITE);
                itemTabBinding.circle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ItemTabBinding itemTabBinding = ItemTabBinding.bind(tab.getCustomView());
                itemTabBinding.tv.setTextColor(Color.parseColor("#AFADAD"));
                itemTabBinding.circle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

               return root;
    }

    private void loadCategories() {
        titleList = new ArrayList<>();

        titleList.add("All");
        titleList.add("New");
        titleList.add("Animals");
        titleList.add("Technology");
        titleList.add("Nature");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}