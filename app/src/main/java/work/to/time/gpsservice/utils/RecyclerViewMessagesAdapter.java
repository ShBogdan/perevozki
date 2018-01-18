package work.to.time.gpsservice.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import work.to.time.gpsservice.R;

public class RecyclerViewMessagesAdapter extends RecyclerView.Adapter<RecyclerViewMessagesAdapter.MyViewHolder> {

    private List<String> messages = new LinkedList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public LinearLayout button;

        public MyViewHolder(View view) {
            super(view);
            message = (TextView) view.findViewById(R.id.message);
            button = (LinearLayout) view.findViewById(R.id.llButton);
        }
    }

    public RecyclerViewMessagesAdapter(Context context, Set<String> moviesList) {
        this.messages.addAll(moviesList);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_messages, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.message.setText(messages.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messages.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, messages.size());
                SharedUtils.updateFcmMessage(context, messages);
                context.sendBroadcast(new Intent("MESSAGE_UPDATED"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}