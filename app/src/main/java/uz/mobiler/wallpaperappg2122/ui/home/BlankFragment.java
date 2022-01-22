package uz.mobiler.wallpaperappg2122.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.mobiler.wallpaperappg2122.R;
import uz.mobiler.wallpaperappg2122.adapters.PageAdapter;
import uz.mobiler.wallpaperappg2122.databinding.FragmentBlankBinding;
import uz.mobiler.wallpaperappg2122.models.Result;
import uz.mobiler.wallpaperappg2122.models.Root;
import uz.mobiler.wallpaperappg2122.retrofit.ApiClient;
import uz.mobiler.wallpaperappg2122.utils.PaginationScrollListener;


public class BlankFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private int mParam1;


    public BlankFragment() {
        // Required empty public constructor
    }


    public static BlankFragment newInstance(int category) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, category);

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

    private FragmentBlankBinding binding;

    private PageAdapter imageAdapter;

    private int currentPage = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private List<Result> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlankBinding.inflate(inflater, container, false);

        list = new ArrayList<>();
        imageAdapter = new PageAdapter(list, new PageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Result result) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("image", result);
                Navigation.findNavController(requireView()).navigate(R.id.imageFragment, bundle);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        binding.rv.setLayoutManager(gridLayoutManager);
        binding.rv.setAdapter(imageAdapter);

        if (mParam1 == 0) {
            getUsers("All", currentPage);
        }

        if (mParam1 == 1) {
            getUsers("New", currentPage);
        }

        if (mParam1 == 2) {
            getUsers("Animals", currentPage);
        }

        if (mParam1 == 3) {
            getUsers("Technology", currentPage);
        }

        if (mParam1 == 4) {
            getUsers("Nature", currentPage);
        }

        binding.rv.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                if (mParam1 == 0) {
                    getNextUsers("All", currentPage);
                }

                if (mParam1 == 1) {
                    getNextUsers("New", currentPage);
                }

                if (mParam1 == 2) {
                    getNextUsers("Animals", currentPage);
                }

                if (mParam1 == 3) {
                    getNextUsers("Technology", currentPage);
                }

                if (mParam1 == 4) {
                    getNextUsers("Nature", currentPage);
                }
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

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        currentPage = 1;
        isLoading = false;
        isLastPage = false;
        super.onDestroyView();
        binding = null;
    }

    void getUsers(String title, int page) {
        new ApiClient().apiService(requireContext()).getUnsplashData(title, page).enqueue(new Callback<Root>() {
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

    void getNextUsers(String title, int page) {
        new ApiClient().apiService(requireContext()).getUnsplashData(title, page).enqueue(new Callback<Root>() {
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

}