package uz.mobiler.wallpaperappg2122;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import uz.mobiler.wallpaperappg2122.databinding.BottomSheetBinding;
import uz.mobiler.wallpaperappg2122.databinding.BottomWallpaperBinding;
import uz.mobiler.wallpaperappg2122.databinding.FragmentImageBinding;
import uz.mobiler.wallpaperappg2122.models.Result;
import uz.mobiler.wallpaperappg2122.room.database.AppDatabase;
import uz.mobiler.wallpaperappg2122.room.entity.ImageEntity;

public class ImageFragment extends Fragment {

    private Result result;
    private AppDatabase appDatabase;
    boolean isHave = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainActivity) getActivity()).hide();
        if (getArguments() != null) {
            result = (Result) getArguments().getSerializable("image");
        }
    }

    private FragmentImageBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater, container, false);
        appDatabase = AppDatabase.getInstance(requireContext());
        List<ImageEntity> images = new ArrayList<>(appDatabase.imageDao().getImages());

        Picasso.get().load(result.urls.regular).into(binding.image);

        binding.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        setBlur(binding.blurBack);
        setBlur(binding.blurDownload);
        setBlur(binding.blurInfo);
        setBlur(binding.blurLike);
        setBlur(binding.blurWallpaper);
        setBlur(binding.blurSend);

        for (ImageEntity imageEntity : images) {
            if(imageEntity.getId1().equals(result.id))
                isHave = true;
        }

        if(isHave)
        {
            int ic_vector__35_ = R.drawable.ic_vector__35_;
            Drawable res = getResources().getDrawable(ic_vector__35_);
            binding.myImage.setImageDrawable(res);
        }
        else
        {
            int ic_vector__13_ = R.drawable.ic_vector__13_;
            Drawable res = getResources().getDrawable(ic_vector__13_);
            binding.myImage.setImageDrawable(res);
        }

        binding.likeImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!isHave)
                {
                    isHave = true;
                    int ic_vector__35_ = R.drawable.ic_vector__35_;
                    Drawable res = getResources().getDrawable(ic_vector__35_);
                    binding.myImage.setImageDrawable(res);

                    ImageEntity imageEntity = new ImageEntity(result.id, result.user.first_name,
                            result.likes, result.width + "x" + result.height, result.urls.thumb, result.urls.full,
                            result.urls.regular,
                            isHave);
                    appDatabase.imageDao().insertImage(imageEntity);
                }
                else
                {
                    isHave = false;
                    int ic_vector__13_ = R.drawable.ic_vector__13_;
                    Drawable res = getResources().getDrawable(ic_vector__13_);
                    binding.myImage.setImageDrawable(res);
                    for (ImageEntity imageEntity : images) {
                        if(imageEntity.getId1().equals(result.id))
                        {
                            appDatabase.imageDao().deleteImage(imageEntity);
                        }
                    }
                }
            }
        });

        binding.blurInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
                BottomSheetBinding bottomSheetBinding = BottomSheetBinding.inflate(getLayoutInflater());
                setBlur(bottomSheetBinding.blurView);
                bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

                bottomSheetBinding.website.setText("Website: unsplash.com");
                bottomSheetBinding.author.setText("Author: " + result.user.first_name);
                bottomSheetBinding.download.setText("Likes: " + result.likes);
                bottomSheetBinding.size.setText("Size: " + result.width + "x" + result.height);
                bottomSheetDialog.show();
            }
        });

        binding.blurSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Wallpaper ilovasini ulashish");
                startActivity(Intent.createChooser(shareIntent,getString(R.string.app_name)));
            }
        });

        binding.downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImageNew(result.user.username, result.urls.full);
            }
        });

        binding.blurWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
                BottomWallpaperBinding bottomSheetBinding = BottomWallpaperBinding.inflate(getLayoutInflater());
                setBlur(bottomSheetBinding.blurView);
                bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

                bottomSheetBinding.wallpaperHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setImageBackground(result.urls.regular, true, false);
                    }
                });

                bottomSheetBinding.wallpaperBlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setImageBackground(result.urls.regular, false, true);
                    }
                });

                bottomSheetBinding.wallpaperHomeBlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setImageBackground(result.urls.regular, true, true);
                    }
                });

                bottomSheetDialog.show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((MainActivity) getActivity()).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setBlur(BlurView blur) {
        float radius = 23f;
        View decorView = getActivity().getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        blur.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(requireContext()))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true).setOverlayColor(Color.parseColor("#41FFFFFF"));
    }

    void setImageBackground(String imageUrl, boolean homeScreen, boolean lockScreen) {
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getContext());
                try {
                    if (homeScreen) {
                        myWallpaperManager.setBitmap(bitmap);
                    }
                    if (lockScreen) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(requireContext(), "Wallpaper changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(requireContext(), "Loading image failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Toast.makeText(requireContext(), "Downloading image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadImageNew(String filename, String downloadUrlOfImage){
        try{
            DownloadManager dm = (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename + ".jpg");
            dm.enqueue(request);
            Toast.makeText(requireContext(), "Image download started.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(requireContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
        }
    }
}