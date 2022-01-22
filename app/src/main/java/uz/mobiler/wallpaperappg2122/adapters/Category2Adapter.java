package uz.mobiler.wallpaperappg2122.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import uz.mobiler.wallpaperappg2122.PopularFragment2;
import uz.mobiler.wallpaperappg2122.ui.home.BlankFragment;

public class Category2Adapter extends FragmentStateAdapter {

    private List<String> titleList;

    public Category2Adapter(Fragment fragment, List<String> titleList) {
        super(fragment);
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return PopularFragment2.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }
}
