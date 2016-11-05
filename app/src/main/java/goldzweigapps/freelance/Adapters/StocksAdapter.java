package goldzweigapps.freelance.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import goldzweigapps.freelance.Fragments.Stocks;
import goldzweigapps.freelance.R;
import goldzweigapps.freelance.Stocks.Stock;
import goldzweigapps.freelance.Stocks.StockFetcher;

/**
 * Created by gilgoldzweig on 17/10/2016.
 */

public class StocksAdapter extends RecyclerView.Adapter<StocksAdapter.StocksHolder> {
List<Stock> stockss;
LayoutInflater inflater;

    public StocksAdapter(Context context, List<Stock> stockss) {
        this.stockss = stockss;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public StocksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StocksHolder(inflater.inflate(R.layout.stockitem, parent, false));
    }

    @Override
    public void onBindViewHolder(StocksHolder holder, int position) {
        Stock stock = stockss.get(position);
        TextView name = holder.name;
        TextView price = holder.price;
        TextView volume = holder.volume;
        name.setText(stock.getSymbol());
        price.setText(String.valueOf(stock.getPrice()));
        volume.setText(String.valueOf(stock.getEps() + "%"));


    }

    @Override
    public int getItemCount() {
        return stockss.size();
    }

     class StocksHolder extends RecyclerView.ViewHolder {
         TextView name, price, volume;
        StocksHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            price = (TextView) v.findViewById(R.id.price);
            volume = (TextView) v.findViewById(R.id.volume);
        }
    }
    public void addItem(Stock stock){
        stockss.add(stock);
        notifyItemInserted(0);
    }
}
