package work.to.time.gpsservice.utils;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import work.to.time.gpsservice.BuildConfig;
import work.to.time.gpsservice.R;
import work.to.time.gpsservice.net.response.ActiveOrders;

public class RecyclerViewSuitableAdapter extends RecyclerView.Adapter<RecyclerViewSuitableAdapter.MyViewHolder> {

    private List<ActiveOrders.Order> orders;
    private Integer currentRouteId;
    private Boolean verified;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView
                route,
                sender,
                product,
                cost,
                isNew;
        public Button
                detail,
                requestPhone,
                goToCreateWayBill;

        public MyViewHolder(View view) {
            super(view);
            route = (TextView) view.findViewById(R.id.route);
            sender = (TextView) view.findViewById(R.id.sender);
            cost = (TextView) view.findViewById(R.id.cost);
            product = (TextView) view.findViewById(R.id.product);
            isNew = (TextView) view.findViewById(R.id.isNew);
            detail = (Button) view.findViewById(R.id.detail);
            requestPhone = (Button) view.findViewById(R.id.requestPhone);
            goToCreateWayBill = (Button) view.findViewById(R.id.goToCreateWayBill);
        }
    }

    public RecyclerViewSuitableAdapter(List<ActiveOrders.Order> moviesList, Integer currentRouteId, Boolean verified) {
        Collections.sort(moviesList, new Comparator<ActiveOrders.Order>() {
            public int compare(ActiveOrders.Order o1, ActiveOrders.Order o2) {
                Integer x1 = o1.getStatus().length();
                Integer x2 = o2.getStatus().length();
                int sComp = x1.compareTo(x2);
                if (sComp != 0) {
                    return sComp;
                } else return 0;
            }
        });
        this.verified = verified;
        this.orders = moviesList;
        this.currentRouteId = currentRouteId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_suitable_route_element, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ActiveOrders.Order order = orders.get(position);

        if (!order.getStatus().toLowerCase().equals("new")) holder.isNew.setVisibility(View.GONE);

        holder.route.setText(String.format("%s - %s", order.getFromCity(), order.getToCity()));
        holder.cost.setText("Цена: " + order.getStatus());
        holder.product.setText("Груз: " + order.getGoodtypes().toString());
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.BASE_URL + "/orders/view/" + order.getId() + "?suitable=" + currentRouteId));
                view.getContext().startActivity(browserIntent);
            }
        });

        if (order.getCanCreateRequest()) {
            holder.sender.setText("Телефон отправителя: ");
            holder.requestPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!verified) {
                        // FIXME: 016 16.01.18 ролистать в верх
                        Toast.makeText(view.getContext(), "Пройдите верификацию", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.BASE_URL + "/waybills/add-request?orderId=" + order.getId() + "&routeId=" + currentRouteId));
                        view.getContext().startActivity(browserIntent);
                    }
                }
            });
        } else {
            holder.requestPhone.setVisibility(View.GONE);
            holder.sender.setText("Телефон отправителя: " + order.getSenderPhone());
        }

        if (order.getCanCreateRequest()) {
            holder.goToCreateWayBill.setVisibility(View.GONE);
        } else {
            holder.goToCreateWayBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.BASE_URL + "/waybills/create?orderId=" + order.getId() + "&routeId=" + currentRouteId));
                    view.getContext().startActivity(browserIntent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

}