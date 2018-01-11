package work.to.time.gpsservice.utils;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import work.to.time.gpsservice.BuildConfig;
import work.to.time.gpsservice.R;
import work.to.time.gpsservice.net.response.ActiveOrders;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<ActiveOrders.Order> orders;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView
                recipient,
                sender,
                route,
                product,
                receivedDate;
        public Button detail;

        public MyViewHolder(View view) {
            super(view);
            route = (TextView) view.findViewById(R.id.route);
            sender = (TextView) view.findViewById(R.id.sender);
            recipient = (TextView) view.findViewById(R.id.recipient);
            product = (TextView) view.findViewById(R.id.product);
            receivedDate = (TextView) view.findViewById(R.id.receivedDate);
            detail = (Button) view.findViewById(R.id.detail);
        }

    }

    public RecyclerViewAdapter(List<ActiveOrders.Order> moviesList) {
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
        final ActiveOrders.Order order = orders.get(position);
        holder.route.setText(String.format("%s - %s", order.getFromCity(), order.getToCity()));
        holder.sender.setText("Телефон Отправителя: " + order.getSenderPhone());
        holder.recipient.setText("Телефон Получателя: " + order.getRecipientPhone());
        holder.product.setText("Груз: " + order.getGoodtypes());
        holder.receivedDate.setText("Время прибытия: от " + order.getTimeFrom() + " до " + order.getTimeTo());
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.BASE_URL + "/orders/view?id=" + order.getId()));
                view.getContext().startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}