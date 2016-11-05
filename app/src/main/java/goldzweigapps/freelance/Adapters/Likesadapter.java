package goldzweigapps.freelance.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;

import java.util.List;

import goldzweigapps.freelance.Items.VideoItem;
import goldzweigapps.freelance.R;

/**
 * Created by gilgoldzweig on 17/10/2016.
 */

public class Likesadapter extends RecyclerView.Adapter<Likesadapter.LikesHolder> {
    LayoutInflater inflator;
    List<VideoItem> items;
    public Likesadapter(Context context, List<VideoItem> list){
        items = list;
        inflator = LayoutInflater.from(context);

    }
    @Override
    public Likesadapter.LikesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Likesadapter.LikesHolder(inflator.inflate(R.layout.videoitem, parent, false));
    }

    @Override
    public void onBindViewHolder(Likesadapter.LikesHolder holder, int position) {
        ImageView thumbnail = holder.thumbnail;
        final VideoItem video = items.get(position);
        TextView title = holder.title;
        ImageView like = holder.like;
        like.setVisibility(View.INVISIBLE);
        final Dialog dialog = holder.dialog;
        final YouTubePlayerView player = holder.player;
        title.setText(video.getTitle());
        Glide.with(holder.itemView.getContext()).load(video.getUrl()).into(thumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                player.initialize(new AbstractYouTubeListener() {
                    @Override
                    public void onReady() {
                        player.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                            @Override
                            public void onYouTubePlayerEnterFullScreen() {
                                player.enterFullScreen();
                            }

                            @Override
                            public void onYouTubePlayerExitFullScreen() {

                            }
                        });
                        player.loadVideo(video.getVideoId(),0);
                        super.onReady();
                    }
                }, true);

            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                player.release();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class LikesHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail, like;
        TextView title;
        Dialog dialog;
        YouTubePlayerView player;
        LikesHolder(View v){
            super(v);
            thumbnail = (ImageView) v.findViewById(R.id.profile);
            title = (TextView) v.findViewById(R.id.name);
            like = (ImageView) v.findViewById(R.id.like);
            dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.plaeyerdialog);
            dialog.setCanceledOnTouchOutside(true);
            player = (YouTubePlayerView) dialog.findViewById(R.id.youtube_player_view);


        }
    }

    public void addItem(VideoItem item){
        items.add(item);
        notifyItemInserted(0);
    }
}