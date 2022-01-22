package uz.mobiler.wallpaperappg2122.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uz.mobiler.wallpaperappg2122.models.Result;
import uz.mobiler.wallpaperappg2122.room.entity.ImageEntity;

@Dao
public interface ImageDao {

    @Insert
    void insertImage(ImageEntity imageEntity);

    @Query("select * from ImageEntity")
    List<ImageEntity> getImages();

    @Delete
    void deleteImage(ImageEntity imageEntity);
}
