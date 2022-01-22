package uz.mobiler.wallpaperappg2122.ui.popular;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import uz.mobiler.wallpaperappg2122.R;
import uz.mobiler.wallpaperappg2122.adapters.Category2Adapter;
import uz.mobiler.wallpaperappg2122.adapters.CategoryAdapter;
import uz.mobiler.wallpaperappg2122.databinding.FragmentPopularBinding;
import uz.mobiler.wallpaperappg2122.databinding.ItemTabBinding;


public class PopularFragment extends Fragment {

    private FragmentPopularBinding binding;
    private Category2Adapter categoryAdapter;
    private List<String> titleList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPopularBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadCategories();

        categoryAdapter = new Category2Adapter(this, titleList);
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
        titleList.add("Animals");
        titleList.add("Technology");
        titleList.add("Nature");
        titleList.add("Office");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}