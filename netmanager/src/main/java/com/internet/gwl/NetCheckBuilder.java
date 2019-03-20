package com.internet.gwl;

import android.content.Context;
import android.util.Log;

import com.internet.gwl.internal.DefaultMonitorFactory;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yogesh on 8/03/2019.
 */

public class NetCheckBuilder {
    private static final String TAG = "NetCheckBuilder";
    private static final Object lock = new Object();

    private static volatile NetCheckBuilder netCheckBuilder;
    private WeakReference<Context> contextRef;
    private Set<Monitor> monitors;

    private NetCheckBuilder(Context context) {
        monitors = new HashSet<>();
        this.contextRef = new WeakReference<>(context);
    }

    public static NetCheckBuilder from(Context context) {
        if (netCheckBuilder == null) {
            synchronized (lock) {
                if (netCheckBuilder == null) {
                    netCheckBuilder = new NetCheckBuilder(context);
                }
            }
        }
        return netCheckBuilder;
    }

    public NetCheckBuilder monitor(int connectionType, Monitor.ConnectivityListener listener) {
        Context context = contextRef.get();
        if (context != null)
            monitors.add(new DefaultMonitorFactory().create(context, connectionType, listener));

        start();
        return netCheckBuilder;
    }

    public NetCheckBuilder monitor(Monitor.ConnectivityListener listener) {
        return monitor(-1, listener);
    }

    public void start() {
        for (Monitor monitor : monitors) {
            monitor.onStart();
        }
    }

    public void stop() {
        for (Monitor monitor : monitors) {
            monitor.onStop();
        }
    }
}
