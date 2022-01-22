package uz.mobiler.wallpaperappg2122.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uz.mobiler.wallpaperappg2122.R;
import uz.mobiler.wallpaperappg2122.adapters.RecyclerViewAdapter;
import uz.mobiler.wallpaperappg2122.databinding.FragmentFavouritesBinding;
import uz.mobiler.wallpaperappg2122.room.database.AppDatabase;
import uz.mobiler.wallpaperappg2122.room.entity.ImageEntity;

public class FavouritesFragment extends Fragment {

    private FragmentFavouritesBinding binding;
    private RecyclerViewAdapter recyclerViewAdapter;
    private AppDatabase appDatabase;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);
        appDatabase = AppDatabase.getInstance(requireContext());
        List<ImageEntity> imageEntityList = new ArrayList<>(appDatabase.imageDao().getImages());
        recyclerViewAdapter = new RecyclerViewAdapter(imageEntityList, new RecyclerViewAdapter.OnImageClick() {
            @Override
            public void imageClick(ImageEntity imageEntity) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("image2",imageEntity);
                Navigation.findNavController(requireView()).navigate(R.id.imageFragment2, bundle);
            }
        });

        recyclerViewAdapter.notifyDataSetChanged();
        binding.rv.setAdapter(recyclerViewAdapter);

        return root;


    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}