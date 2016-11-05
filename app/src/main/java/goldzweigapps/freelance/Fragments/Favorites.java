package goldzweigapps.freelance.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import goldzweigapps.freelance.Adapters.Likesadapter;
import goldzweigapps.freelance.Items.VideoItem;
import goldzweigapps.freelance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favorites extends Fragment {
    public static RecyclerView rv;
    List<VideoItem> items;
    LinearLayoutManager llm;
    SharedPreferences sp;
    public static Likesadapter adapter;
    SwipeRefreshLayout swipe;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        items = new ArrayList<>();
        adapter = new Likesadapter(view.getContext(), items);
        llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        sp = view.getContext().getSharedPreferences("fav", 0);
        for (int id = 0; id <= 100; id++){
            if (sp.getString(String.valueOf(id), null) != null){
                String it = sp.getString(String.valueOf(id), null);
                if (VideoItem.createFromString(it) != null){
                    adapter.addItem(VideoItem.createFromString(it));
                }
            }
        }
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorites, container, false);
    }

}
