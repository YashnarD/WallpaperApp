package uz.mobiler.wallpaperappg2122.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uz.mobiler.wallpaperappg2122.models.Root;

public interface ApiService {

    @GET("search/photos/?client_id=GK89RqIRFICzTSiADhWc-qkNt051sd_m4Xa1de1xQSA")
    Call<Root> getUnsplashData(@Query("query") String query,
                               @Query("page") int per_page);

    @GET("photos/random/?client_id=zBhoa66Dd-IGCclQlxW23Xwb78dpZYAOLeld4ppfa1k")
    Call<uz.mobiler.wallpaperappg2122.models.models2.Root> getData(@Query("page") int page);
}