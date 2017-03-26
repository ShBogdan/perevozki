package work.to.time.gpsservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import work.to.time.gpsservice.core.MyApplication;
import work.to.time.gpsservice.net.NetManager;
import work.to.time.gpsservice.net.response.ArchiveOrders;
import work.to.time.gpsservice.observer.net.NetSubscriber;
import work.to.time.gpsservice.utils.MyLog;
import work.to.time.gpsservice.utils.RecyclerViewAdapter;
import work.to.time.gpsservice.utils.SharedUtils;

public class MenuFragment extends Fragment implements NetSubscriber, View.OnClickListener {

    MyApplication app;
    Context mContext;
    String fireBase;
    String token;
    RecyclerViewAdapter adapter;

    @Bind(R.id.login_progress)
    View mProgressView;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.active)
    Button active;

    @Bind(R.id.suitable)
    Button suitable;

    @Bind(R.id.archive)
    Button archive;

    @Bind(R.id.massage)
    TextView massage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        app = (MyApplication) getActivity().getApplication();
        ButterKnife.bind(this, view);
        mContext = getContext();

        app.getNetManager().subscribe(this);
        fireBase = SharedUtils.getAccessDeviceId(mContext);
        token = SharedUtils.getAccessToken(mContext);

        active.setOnClickListener(this);
        suitable.setOnClickListener(this);
        archive.setOnClickListener(this);

        return view;
    }

    @Override
    public void onNetSuccess(int requestId, Object data) {
        if (requestId == NetManager.REQUEST_ACTIVE_ORDERS) {
//            MyLog.show((String) data);
//            massage.setText((String) data);
//            showProgress(false);

        }
        if (requestId == NetManager.REQUEST_SUITABLE_ORDERS) {
        }
        if (requestId == NetManager.REQUEST_ARCHIVE_ORDERS) {
            ArchiveOrders orders = (ArchiveOrders) data;

            List<ArchiveOrders.Order> orderList = orders.data;

            adapter = new RecyclerViewAdapter(orderList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            showProgress(false);
        }
    }

    @Override
    public void onNetError(int requestId, String error) {
        showProgress(false);
        Toast.makeText(mContext, "Ошибка подключения", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetConnectionError(int requestId) {
        showProgress(false);
        Toast.makeText(mContext, "Ошибка подключения", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.active:
                showProgress(true);
                app.getNetManager().activeOrders(fireBase, token);
                break;
            case R.id.suitable:
                showProgress(true);
                app.getNetManager().suitableOrders(fireBase, token);
                break;
            case R.id.archive:
                showProgress(true);
                app.getNetManager().archiveOrders(fireBase, token);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;

            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
