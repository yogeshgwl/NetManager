package com.internet.gwl.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.internet.gwl.Monitor;
import com.internet.gwl.R;

public class DefaultMonitor implements Monitor {

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Context context;
    private final ConnectivityListener listener;
    private final int connectionType;

    private boolean isConnected;
    private boolean isRegistered;


    private final BroadcastReceiver connectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            boolean wasConnected = isConnected;
            isConnected = NetworkUtil.isConnected(context, connectionType);
            if (wasConnected != isConnected) {
                emitEvent();
            }
        }
    };

    public DefaultMonitor(Context context, ConnectivityListener listener, int connectionType) {
        this.context = context;
        this.listener = listener;
        this.connectionType = connectionType;
    }

    public DefaultMonitor(Context context, ConnectivityListener listener) {
        this(context, listener, -1);
    }

    private void emitEvent() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onConnectivityChanged(connectionType, isConnected, isConnected && NetworkUtil.isConnectionFast(context));
            }
        });

        if (isConnected) {
            ViewUtility.setColor(R.color.color_green);
            ViewUtility.setDuration(Snackbar.LENGTH_LONG);
            ViewUtility.setMessage(context.getResources().getString(NetworkUtil.isConnectionFast(context) ? R.string.strong_connecting_msg : R.string._normal_connecting_msg));
            showMessageToView(null, context);
        } else {
            ViewUtility.setColor(R.color.colorAccent);
            ViewUtility.setDuration(Snackbar.LENGTH_INDEFINITE);
            ViewUtility.setMessage(context.getResources().getString(R.string.seems_you_are_offline));
            showMessageToView(null, context);
        }
    }

    private void register() {
        if (isRegistered) {
            return;
        }

        Log.i("Monitor", "Registering");
        isConnected = NetworkUtil.isConnected(context, connectionType);
        //emit once
        emitEvent();
        context.registerReceiver(
                connectivityReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        isRegistered = true;
    }

    private void unregister() {
        if (!isRegistered) {
            return;
        }

        Log.i("Monitor", "Unregistering");
        context.unregisterReceiver(connectivityReceiver);
        isRegistered = false;
    }

    @Override
    public void onStart() {
        register();
    }

    @Override
    public void onStop() {
        unregister();
    }



    static Snackbar snackbar;

    private static Snackbar showMessage(View view,
                                        Context context) {
        View defaultView = view;
        if (view == null && ((AppCompatActivity) context).findViewById(android.R.id.content) != null) {
            defaultView = ((AppCompatActivity) context).findViewById(android.R.id.content);
        } else
            return null;
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        if (defaultView != null) {
            snackbar = Snackbar.make(defaultView, ViewUtility.getMessage(), ViewUtility.getDuration());
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(ContextCompat.getColor(context, ViewUtility.getColor()));
            TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            if (NetworkUtil.isConnectionFast(context))
                snackbar.setActionTextColor(Color.WHITE);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = ViewUtility.getBarGravity();
            snackbarView.setLayoutParams(params);
            snackbar.show();
        } else {
            snackbar = null;
        }
        return snackbar;
    }

    private static void showMessageToView(View view, Context context) {
        showMessage(view, context);
    }


}

