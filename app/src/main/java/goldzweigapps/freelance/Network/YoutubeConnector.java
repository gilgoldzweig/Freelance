package goldzweigapps.freelance.Network;

import android.content.Context;
import android.util.Log;


import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import goldzweigapps.freelance.Items.VideoItem;
import goldzweigapps.freelance.R;

public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Videos.List query;

    // Your developer key goes here
    public static final String KEY = "AIzaSyAizlycGX_xxp_kFuX_QX_urr4TOQFqclE";
//    String url = "https://www.googleapis.com/youtube/v3/videos?id="+videoID+"&key=" + YOUR KEY HERE + "&fields=items(id,snippet(channelId,title,categoryId),statistics)&part=snippet,statistics";

    public YoutubeConnector() {
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName("Share my share").build();

        try{
            query = youtube.videos().list("snippet");
            query.setKey(KEY);
            query.setFields("items(id/videoId,snippet(thumbnails/high,title,channelTitle))");
        }catch(IOException e){
            Log.d("YC", "Could not initialize: "+e);
        }
    }


    public VideoItem search(String keywords){
        Log.d("im Searching for",keywords);
        query.setId(keywords);

        try{
            VideoListResponse response = query.execute();
            List<Video> results = response.getItems();

           VideoItem item = null;
            int i = 0 ;
            for(Video result : results){
                item = new VideoItem(result.getSnippet().getThumbnails().getHigh().getUrl(),result.getSnippet().getTitle(),keywords,String.valueOf(i));
                i++;
                Log.d("I found: ",item.getTitle());

            }
            return item;
        }catch(IOException e){
            Log.d("YC", "Could not search: "+e);
            return null;
        }
    }
}