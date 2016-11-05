package goldzweigapps.freelance.Items;

import android.util.Log;

import java.util.List;

/**
 * Created by gilgoldzweig on 16/10/2016.
 */

public class VideoItem {
    String url, title, VideoId,id;
    boolean isLiked;

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getUrl() {
        return url;
    }

    public static String getItemAsString(VideoItem item){
        Log.d("tag",String.format("%s,%s,%s,%s",item.getUrl(),item.getTitle(),item.getVideoId(),item.getId()));
        return String.format("%s,%s,%s,%s",item.getUrl(),item.getTitle(),item.getVideoId(),item.getId());
    }
    public static VideoItem createFromString(String item){
        if (item.length() < 4){
            return null;
        }else{
            String[] values = item.split(",");
            System.out.println(values);
            return new VideoItem(values[0], values[1], values[2], values[3]);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VideoItem(String url, String title, String videoId, String id) {

        this.url = url;
        this.title = title;
        VideoId = videoId;
        this.id = id;
    }
}