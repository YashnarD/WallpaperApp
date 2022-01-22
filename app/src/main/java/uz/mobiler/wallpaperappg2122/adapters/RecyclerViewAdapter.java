package uz.mobiler.wallpaperappg2122.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uz.mobiler.wallpaperappg2122.databinding.ItemImageBinding;
import uz.mobiler.wallpaperappg2122.models.Result;
import uz.mobiler.wallpaperappg2122.room.entity.ImageEntity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Vh>{

    List<ImageEntity> imageEntityList;
    private OnImageClick imageClick;

    public RecyclerViewAdapter(List<ImageEntity> imageEntityList, OnImageClick imageClick) {
        this.imageEntityList = imageEntityList;
        this.imageClick = imageClick;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {

        ImageEntity imageEntities = imageEntityList.get(position);
        holder.onBind(imageEntities);
    }

    @Override
    public int getItemCount() {
        return imageEntityList.size();
    }

    class Vh extends RecyclerView.ViewHolder{
        public ItemImageBinding binding;
        public Vh(@NonNull ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(ImageEntity result) {
            Picasso.get().load(result.getThumb()).into(binding.image);

            binding.image.setOnClickListener(v -> {
                imageClick.imageClick(result);
            });
        }
    }

    public interface OnImageClick{
       public void imageClick(ImageEntity imageEntity);
    }
}
