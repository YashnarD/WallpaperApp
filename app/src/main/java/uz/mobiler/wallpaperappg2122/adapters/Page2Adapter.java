package uz.mobiler.wallpaperappg2122.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uz.mobiler.wallpaperappg2122.databinding.ItemImageBinding;
import uz.mobiler.wallpaperappg2122.databinding.ItemProgressBinding;
import uz.mobiler.wallpaperappg2122.models.models2.Root;

public class Page2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Root> list;
    private OnItemClickListener onItemClickListener;

    public Page2Adapter(List<Root> list, OnItemClickListener onItemClickListener) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    private boolean isLoadingAdded = false;
    private int LOADING = 0;
    private int DATA = 1;

    public void addAll(List<Root> otherList) {
        list.addAll(otherList);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoadingAdded = true;
    }

    public void removeLoading() {
        isLoadingAdded = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LOADING) {
            return new ProgressVh(
                    ItemProgressBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        } else {
            return new UserVh(
                    ItemImageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == DATA) {
            UserVh userVh = (UserVh) holder;
            userVh.onBind(list.get(position));
        } else {
            ProgressVh progressVh = (ProgressVh) holder;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserVh extends RecyclerView.ViewHolder {
        public ItemImageBinding binding;

        public UserVh(@NonNull ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(Root result) {
            Picasso.get().load(result.urls.thumb).into(binding.image);

            binding.image.setOnClickListener(v -> {
                onItemClickListener.onItemClick(result);
            });
        }
    }

    class ProgressVh extends RecyclerView.ViewHolder {
        public ItemProgressBinding binding;

        public ProgressVh(@NonNull ItemProgressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size() - 1 && isLoadingAdded) {
            return LOADING;
        }
        return DATA;
    }

    public interface OnItemClickListener {
        void onItemClick(Root result);
    }
}
