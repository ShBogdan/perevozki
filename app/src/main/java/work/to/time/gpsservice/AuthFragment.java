package work.to.time.gpsservice;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import work.to.time.gpsservice.core.MyApplication;
import work.to.time.gpsservice.net.NetManager;
import work.to.time.gpsservice.net.response.AuthModel;
import work.to.time.gpsservice.observer.net.NetSubscriber;
import work.to.time.gpsservice.service.GPSTracker;
import work.to.time.gpsservice.utils.Constants;
import work.to.time.gpsservice.utils.MyLog;
import work.to.time.gpsservice.utils.PermissionsUtils;
import work.to.time.gpsservice.utils.SharedUtils;

public class AuthFragment extends Fragment implements NetSubscriber {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    MyApplication app;
    Context mContext;
    FragmentActivity a;


    @Bind(R.id.login_progress)
    View mProgressView;
    @Bind(R.id.email)
    EditText mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.login_form)
    View mLoginFormView;
    @Bind(R.id.sign_in_button)
    Button mSignInButton;
    @Bind(R.id.tvRegistration)
    TextView mRegistrationButto;
    @Bind(R.id.imageView)
    ImageView mLogo;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        app = (MyApplication) getActivity().getApplication();
        ButterKnife.bind(this, view);
        mContext = getContext();

        checkPermission();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        app.getNetManager().subscribe(this);

        Button mSignInButton = (Button) view.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });

        mRegistrationButto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://112kilo.react-logic.com/#/registration"));
                startActivity(browserIntent);
            }
        });

        String uri = "@drawable/animals";
        int imageResource = getResources().getIdentifier(uri, null, getContext().getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        mLogo.setImageDrawable(res);

//        Bitmap bitmap= BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.animals);
//        mLogo.setImageBitmap(bitmap);

        return view;
    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("password is empty");
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("login is empty");
            cancel = true;
        }
        if (!cancel) {
            showProgress(true);
            app.getNetManager().authorize(email, password);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        app.getNetManager().unsubscribe(this);

    }

    @Override
    public void onNetSuccess(int requestId, Object data) {
        if (requestId == NetManager.REQUEST_AUTH) {
            showProgress(false);
            AuthModel model = (AuthModel) data;
            if (!model.getToken().equals("false")) {
                SharedUtils.setAccessToken(mContext, model.getToken());
                if(getActivity()!=null) {
                    Toast.makeText(mContext, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show();
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, new MenuFragment(), "MenuFragment");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            } else {
                Toast.makeText(mContext, "Incorrect login or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNetError(int requestId, String error) {
        MyLog.show("AuthOnNetError");
        showProgress(false);
        Toast.makeText(getActivity(), "Проблемы с подключением к сети", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetConnectionError(int requestId) {
        MyLog.show("AuthOnNetConnectionError");
        showProgress(false);
        Toast.makeText(getActivity(), "Проблемы с подключением к сети", Toast.LENGTH_SHORT).show();
    }

    private void checkPermission() {
        if (PermissionsUtils.Location.isDenied(getActivity())) {
            if (PermissionsUtils.Location.isNeedRequest(getActivity())) {
                PermissionsUtils.Location.request(getActivity(), LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime);
            int shortAnimTime = 200;

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
