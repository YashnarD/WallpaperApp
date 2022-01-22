package uz.mobiler.wallpaperappg2122;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.mobiler.wallpaperappg2122.adapters.PageAdapter;
import uz.mobiler.wallpaperappg2122.databinding.FragmentBlankBinding;
import uz.mobiler.wallpaperappg2122.databinding.FragmentPopular2Binding;
import uz.mobiler.wallpaperappg2122.models.Result;
import uz.mobiler.wallpaperappg2122.models.Root;
import uz.mobiler.wallpaperappg2122.retrofit.ApiClient;
import uz.mobiler.wallpaperappg2122.utils.PaginationScrollListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PopularFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularFragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private int mParam1;

    public PopularFragment2() {

    }

    public static PopularFragment2 newInstance(int param1) {
        PopularFragment2 fragment = new PopularFragment2();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    private PageAdapter imageAdapter;

    private int currentPage = 100;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 102;
    private List<Result> list;
    private FragmentPopular2Binding binding;

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
        binding = FragmentPopular2Binding.inflate(inflater, container, false);

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
            getUsers("Animals", currentPage);
        }

        if (mParam1 == 2) {
            getUsers("Technology", currentPage);
        }

        if (mParam1 == 3) {
            getUsers("Nature", currentPage);
        }

        if (mParam1 == 4) {
            getUsers("Office", currentPage);
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
                    getNextUsers("Animals", currentPage);
                }

                if (mParam1 == 2) {
                    getNextUsers("Technology", currentPage);
                }

                if (mParam1 == 3) {
                    getNextUsers("Nature", currentPage);
                }

                if (mParam1 == 4) {
                    getNextUsers("Office", currentPage);
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
        currentPage = 100;
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