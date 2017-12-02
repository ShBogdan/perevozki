package work.to.time.gpsservice.utils;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import work.to.time.gpsservice.R;
import work.to.time.gpsservice.net.response.RouteModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<RouteModel.Rotes> orders;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView
                recipient,
                sender,
                route,
                product,
                toAddress;

        public MyViewHolder(View view) {
            super(view);
            route = (TextView) view.findViewById(R.id.route);
            sender = (TextView) view.findViewById(R.id.sender);
            recipient = (TextView) view.findViewById(R.id.recipient);
            product = (TextView) view.findViewById(R.id.product);

        }
    }

    public RecyclerViewAdapter(List<RouteModel.Rotes> moviesList) {
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
        RouteModel.Rotes order = orders.get(position);
        holder.route.setText(String.format("%s - %s", order.getFromCity(), order.getToCity()));
        holder.sender.setText("Телефон отправителя:" + order.getFromAddress());
        holder.recipient.setText("Телефон Получателя:" + order.getToAddress());
        holder.product.setText("Груз:" + order.getAtegories().toString());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}