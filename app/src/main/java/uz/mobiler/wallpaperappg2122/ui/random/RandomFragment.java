package uz.mobiler.wallpaperappg2122.ui.random;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.mobiler.wallpaperappg2122.R;
import uz.mobiler.wallpaperappg2122.adapters.PageAdapter;
import uz.mobiler.wallpaperappg2122.databinding.FragmentRandomBinding;
import uz.mobiler.wallpaperappg2122.models.Result;
import uz.mobiler.wallpaperappg2122.models.Root;
import uz.mobiler.wallpaperappg2122.retrofit.ApiClient;
import uz.mobiler.wallpaperappg2122.utils.PaginationScrollListener;

public class RandomFragment extends Fragment {

    private FragmentRandomBinding binding;
    private PageAdapter imageAdapter;
    private static String TAG = "Cannot invoke method length() on null object";

    private int currentPage = 5;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private List<Result> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRandomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);

        list = new ArrayList<>();
        imageAdapter = new PageAdapter(list, new PageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Result result) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("image", (Serializable) result);
                Navigation.findNavController(requireView()).navigate(R.id.imageFragment, bundle);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        binding.rv.setLayoutManager(gridLayoutManager);
        binding.rv.setAdapter(imageAdapter);

        getUsers(currentPage);

        binding.rv.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getNextUsers(currentPage);
            }

            @Override
            protected Boolean isLastPage() {
                return isLastPage;
            }

            @Override
            protected Boolean isLoading() {
                return isLoading;
            }
        });

        return root;
    }

    private void getNextUsers(int page) {
        new ApiClient().apiService(requireContext()).getUnsplashData("All", page).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    imageAdapter.removeLoading();
                    isLoading = false;
                    imageAdapter.addAll(response.body().results);
                    if (currentPage != TOTAL_PAGES) {
                        imageAdapter.addLoading();
                    } else {
                        isLastPage = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

    private void getUsers(int page) {
            new ApiClient().apiService(requireContext()).getUnsplashData("All",page).enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    if (response.isSuccessful()) {
                        list.addAll(response.body().results);
                        imageAdapter.notifyDataSetChanged();
                        if (currentPage <= TOTAL_PAGES) {
                            imageAdapter.addLoading();
                        } else {
                            isLastPage = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {

                }

            });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.random, menu);

        MenuItem item = menu.findItem(R.id.action_random);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public void onDestroyView() {
        currentPage = 1;
        isLoading = false;
        isLastPage = false;
        super.onDestroyView();
        binding = null;
    }
}