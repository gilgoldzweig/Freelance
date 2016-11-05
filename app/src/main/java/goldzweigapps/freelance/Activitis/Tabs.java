package goldzweigapps.freelance.Activitis;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androidhuman.rxfirebase.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import goldzweigapps.freelance.Adapters.StocksAdapter;
import goldzweigapps.freelance.Adapters.VideoAdapter;
import goldzweigapps.freelance.Fragments.Daily;
import goldzweigapps.freelance.Fragments.Favorites;
import goldzweigapps.freelance.Fragments.Stocks;
import goldzweigapps.freelance.Fragments.Videos;
import goldzweigapps.freelance.Interface.ResponseCallBack;
import goldzweigapps.freelance.Items.VideoItem;
import goldzweigapps.freelance.Network.HttpCalls;
import goldzweigapps.freelance.R;
import goldzweigapps.freelance.Stocks.Stock;
import goldzweigapps.freelance.Stocks.StockFetcher;
import goldzweigapps.tabs.Builder.EasyTabsBuilder;
import goldzweigapps.tabs.Colors.EasyTabsColors;
import goldzweigapps.tabs.Items.TabItem;
import rx.functions.Action1;

public class Tabs extends AppCompatActivity {
    TabLayout tabs;
    ViewPager pager;
    public static List<VideoItem> items, items1;
    public static VideoAdapter adapter, adapter1;
    public static StocksAdapter stocksAdapter;
    public static List<Stock> stocks;
    int idi = 0;
    List<String> classes, news;
    public final String KEY = "AIzaSyAizlycGX_xxp_kFuX_QX_urr4TOQFqclE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        EasyTabsBuilder.init(this,tabs,pager).addTabs(
                new TabItem(new Daily(), "Daily"),
                new TabItem(new Videos(), "Videos"),
                new TabItem(new Favorites(), "Favorites"),
                new TabItem(new Stocks(), "Stocks"))
                .setBackgroundColor(EasyTabsColors.White)
                .setTextColors(Color.parseColor("#87cefa"), EasyTabsColors.RoyalBlue)
                .setIndicatorColor(EasyTabsColors.White)
                .addIcons(
                        R.drawable.ic_fiber_new_white_36dp,
                        R.drawable.ic_video_library_white_36dp,
                        R.drawable.ic_favorite_white_36dp,
                        R.drawable.ic_attach_money_white_36dp)
                .setIconFading(true)
                .Build();
        items = new ArrayList<>();
        adapter = new VideoAdapter(this,items);
        items1 = new ArrayList<>();
        adapter1 = new VideoAdapter(this,items1);
        stocks = new ArrayList<>();
        stocksAdapter = new StocksAdapter(this, stocks);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        classes = new ArrayList<>();
        news = new ArrayList<>();


        DatabaseReference ref = database.getReference("class");
        DatabaseReference ref1 = database.getReference("news");
        DatabaseReference ref2 = database.getReference("stocks");



        RxFirebaseDatabase.dataChanges(ref1)
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

                                String a = "https://www.googleapis.com/youtube/v3/videos?id="+ id +"&fields=items(snippet(thumbnails/high,title))&part=snippet&key="+ KEY;
                                HttpCalls.Get(a, new ResponseCallBack() {
                                    @Override
                                    public void Response(final String ResponseBody) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter1.addItem(new VideoItem(StringUtils.substringBetween(ResponseBody,"\"url\": \"","\"," ),StringUtils.substringBetween(ResponseBody,"\"title\": \"","\""),id, String.valueOf(idi)));
                                                idi++;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onUi() {

                                    }
                                });
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

        RxFirebaseDatabase.dataChanges(ref2)
                .subscribe(new Action1<DataSnapshot>() {
                    @Override
                    public void call(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String[] middle = dataSnapshot.getValue().toString().split(",");
                            for (String link : middle){
                                final String id;
                                final String urls;
                                if (StringUtils.substringAfter(link, "=").contains("}")){
                                    urls = StringUtils.substringBefore(StringUtils.substringAfter(link, "="), "}");
                                } else{
                                    classes.add(StringUtils.substringAfter(link, "="));
                                    urls = StringUtils.substringAfter(link, "=");
                                }
                                AsyncTask task = new AsyncTask() {
                                    @Override
                                    protected Object doInBackground(Object[] params) {
                                        final Stock stock = StockFetcher.getStock(urls);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                stocksAdapter.addItem(stock);
                                            }
                                        });

                                        return null;
                                    }
                                }.execute();


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



    }
}
