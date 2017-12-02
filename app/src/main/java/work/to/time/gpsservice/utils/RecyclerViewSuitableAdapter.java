package work.to.time.gpsservice.utils;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import work.to.time.gpsservice.R;
import work.to.time.gpsservice.net.response.RouteModel;
import work.to.time.gpsservice.net.response.RouteSuitableModel;

public class RecyclerViewSuitableAdapter extends RecyclerView.Adapter<RecyclerViewSuitableAdapter.MyViewHolder> {

    private List<RouteSuitableModel.RotesSuitable> orders;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView
                route,
                sender,
                product,
                cost;

        public MyViewHolder(View view) {
            super(view);
            route = (TextView) view.findViewById(R.id.route);
            sender = (TextView) view.findViewById(R.id.sender);
            cost = (TextView) view.findViewById(R.id.cost);
            product = (TextView) view.findViewById(R.id.product);

        }
    }

    public RecyclerViewSuitableAdapter(List<RouteSuitableModel.RotesSuitable> moviesList) {
        this.orders = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_suitable_route_element, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RouteSuitableModel.RotesSuitable order = orders.get(position);
        holder.route.setText(String.format("%s - %s", order.getFromCity(), order.getToCity()));
        holder.sender.setText("Телефон отправителя:" + order.getFromAddress());
        holder.cost.setText("Цена:" + order.getCost());
        holder.product.setText("Груз:" + "поле отсутствует");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}