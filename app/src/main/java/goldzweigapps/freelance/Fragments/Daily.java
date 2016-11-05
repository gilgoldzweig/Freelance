package goldzweigapps.freelance.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import goldzweigapps.freelance.Activitis.Tabs;
import goldzweigapps.freelance.Adapters.VideoAdapter;
import goldzweigapps.freelance.Items.VideoItem;
import goldzweigapps.freelance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Daily extends Fragment {
    public RecyclerView rv;
    VideoAdapter adapter;
    List<VideoItem> items;
    LinearLayoutManager llm;
    SwipeRefreshLayout swipe;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        items = new ArrayList<>();
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        adapter = Tabs.adapter1;
        llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
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
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

}
