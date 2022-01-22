package uz.mobiler.wallpaperappg2122.adapters;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.gson.Gson;

import java.util.List;


import uz.mobiler.wallpaperappg2122.FavouriteFragment2;
import uz.mobiler.wallpaperappg2122.PopularFragment2;
import uz.mobiler.wallpaperappg2122.RandomFragment2;
import uz.mobiler.wallpaperappg2122.ui.home.BlankFragment;

public class CategoryAdapter extends FragmentStateAdapter {

    private List<String> titleList;

    public CategoryAdapter(Fragment fragment, List<String> titleList) {
        super(fragment);
        this.titleList = titleList;
    }

    @Override
    public Fragment createFragment(int position) {
        return BlankFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }
}
