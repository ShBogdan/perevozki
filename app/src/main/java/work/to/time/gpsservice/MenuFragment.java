package work.to.time.gpsservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import work.to.time.gpsservice.net.response.ActiveOrders;
import work.to.time.gpsservice.net.response.RouteModel;
import work.to.time.gpsservice.observer.net.NetSubscriber;
import work.to.time.gpsservice.utils.MyLog;
import work.to.time.gpsservice.utils.RecyclerViewAdapter;
import work.to.time.gpsservice.utils.RecyclerViewMessagesAdapter;
import work.to.time.gpsservice.utils.RecyclerViewSuitableAdapter;
import work.to.time.gpsservice.utils.SharedUtils;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

public class MenuFragment extends Fragment implements NetSubscriber, View.OnClickListener {

    MyApplication app;
    Context mContext;
    String fireBase;
    String token;
    RecyclerViewAdapter adapter;
    RecyclerViewSuitableAdapter suitableAdapter;
    Integer currentRouteId;

    private Integer actualRoutId;

    @Bind(R.id.login_progress)
    View mProgressView;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.active)
    Button active;

    @Bind(R.id.suitable)
    Button suitable;

    @Bind(R.id.verify)
    Button verify;

    @Bind(R.id.status)
    TextView status;

    @Bind(R.id.actualRout)
    TextView actualRout;

    @Bind(R.id.role)
    TextView role;

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
        verify.setOnClickListener(this);
        MyLog.show(SharedUtils.getVerify(getContext()));
        if (allNotNull(SharedUtils.getVerify(getContext()))
                && SharedUtils.getVerify(getContext()).equals("true")) {
            ((ViewGroup) verify.getParent()).removeView(verify);
            status.setText("Статус: Верифицирован");
        }
        role.setText("Роль: Водитель");
        showProgress(true);
        app.getNetManager().activeRoutes(fireBase, token);

        return view;
    }

    @Override
    public void onNetSuccess(int requestId, Object data) {
        if (requestId == NetManager.REQUEST_ACTIVE_ROUTES) {
            RouteModel rotes = (RouteModel) data;
// FIXME: 017 17.01.18
            if (rotes == null || rotes.data == null) {
//                Toast.makeText(getContext(), "Проблемы с сервером", Toast.LENGTH_LONG).show();
                showProgress(false);
                return;
            }

            List<RouteModel.Rotes> orderList = rotes.data;
            for (RouteModel.Rotes r : orderList) {
                if (allNotNull(r.getCurrent())
                        && r.getCurrent()) {
                    MyLog.show(r.toString());
                    currentRouteId = r.getId();

                    actualRout.setText(String.format("Маршрут: %s - %s", r.getFromCity(), r.getToCity()));
                    actualRoutId = r.getId();
                    active.setText(String.format("АКТИВНЫЕ(%s)", r.getOrders().size()));
                    suitable.setText(String.format("ПОДХОДЯЩИЕ(%s)", r.getSuitableCount()));

                    adapter = new RecyclerViewAdapter(r.getOrders());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        }
        if (requestId == NetManager.REQUEST_SUITABLE_ROUTES) {
            ActiveOrders orders = (ActiveOrders) data;
// FIXME: 017 17.01.18
            if (orders == null || orders.data == null) {
//                Toast.makeText(getContext(), "Проблемы с сервером", Toast.LENGTH_LONG).show();
                showProgress(false);
                return;
            }

            List<ActiveOrders.Order> orderList = orders.data;
            suitableAdapter = new RecyclerViewSuitableAdapter(orderList, currentRouteId, orders.verified);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(suitableAdapter);
            suitableAdapter.notifyDataSetChanged();

        }
        showProgress(false);

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
                app.getNetManager().activeRoutes(fireBase, token);
                break;
            case R.id.suitable:
                showProgress(true);
                if (actualRoutId != null)
                    app.getNetManager().suitableRoutes(actualRoutId.toString(), token);
                break;
            case R.id.verify:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.REGISTRATION_URL));
                startActivity(browserIntent);
                break;
        }
    }

    public void onClickActivity() {
        RecyclerViewMessagesAdapter adapter = new RecyclerViewMessagesAdapter(mContext, SharedUtils.getFcmMessage(mContext));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
