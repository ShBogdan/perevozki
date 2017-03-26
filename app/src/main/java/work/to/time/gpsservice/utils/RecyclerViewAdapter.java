package work.to.time.gpsservice.utils;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import work.to.time.gpsservice.R;
import work.to.time.gpsservice.net.response.ArchiveOrders;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<ArchiveOrders.Order> orders;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user, cityTo, cityFtom;

        public MyViewHolder(View view) {
            super(view);
            user = (TextView) view.findViewById(R.id.person_name);
            cityTo = (TextView) view.findViewById(R.id.cityTo);
            cityFtom = (TextView) view.findViewById(R.id.cityFrom);

        }
    }

    public RecyclerViewAdapter(List<ArchiveOrders.Order> moviesList) {
        this.orders = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_element, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArchiveOrders.Order order = orders.get(position);
        holder.user.setText(order.getUser());
        holder.cityTo.setText(order.getToCity());
        holder.cityFtom.setText(order.getFromCity());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}