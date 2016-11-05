package goldzweigapps.freelance.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidhuman.rxfirebase.database.RxFirebaseDatabase;
import com.google.api.services.youtube.YouTube;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import goldzweigapps.freelance.Activitis.Tabs;
import goldzweigapps.freelance.Adapters.VideoAdapter;
import goldzweigapps.freelance.Items.VideoItem;
import goldzweigapps.freelance.R;
import goldzweigapps.freelance.Network.YoutubeConnector;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class Videos extends Fragment {
    public RecyclerView rv;
    VideoAdapter adapter;
    List<VideoItem> items;
    LinearLayoutManager llm;
    SwipeRefreshLayout swipe;
    List<String> ids;
    FirebaseDatabase database;
    DatabaseReference classes;
    YoutubeConnector connector;


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        items = new ArrayList<>();
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        ids = new ArrayList<>();


//        database = FirebaseDatabase.getInstance();
//        classes = database.getReference("class");
        llm = new LinearLayoutManager(view.getContext());
        connector = new YoutubeConnector();

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                RxFirebaseDatabase.dataChanges(FirebaseDatabase.getInstance().getReference("class"))
                        .subscribe(new Action1<DataSnapshot>() {
                            @Override
                            public void call(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String[] middle = dataSnapshot.getValue().toString().split(",");
                                    for (String link : middle){
                                        final String id;
                                        if (StringUtils.substringAfter(link, "=").contains("}")){
                                            id = StringUtils.substringAfter(StringUtils.substringBefore(StringUtils.substringAfter(link, "="), "}"), "v=");
                                        } else{
                                            id = StringUtils.substringAfter(StringUtils.substringAfter(link, "="), "v=");
                                        }
                                        ids.add(id);


                                    }

                                    // Do something with data
                                } else {
                                    // Data does not exists
                                    Log.d("tag", "im sorry i didnt find");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // Handle error
                                Log.e("error", "error: ", throwable);
                            }
                        });
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (ids.size() > 2) {
                    while (items.size() != ids.size()) {
                        for (String id : ids) {
                            Log.d("new id", id + "List is " + ids.toString());
                            items.add(connector.search(id));

                        }
                    }

                }


            }
        }.execute();

        adapter = new VideoAdapter(view.getContext(),items);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });

//
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference ref = db.getReference("links");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value[] = dataSnapshot.getValue(String[].class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });





//        for (int i = 0; i <= 150; i++){
//            items.add(item);
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.videos, container, false);
    }

}
