package com.androidstudy.tovuti.internal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

public class DefaultMonitorFactory implements MonitorFactory {
    public static final String ACCESS_NETWORK_PERMISSION = Manifest.permission.ACCESS_NETWORK_STATE;

    @NonNull
    @Override
    public Monitor create(
            @NonNull Context context,
            int connectionType,
            @NonNull Monitor.ConnectivityListener listener) {

        int permissionResult = ContextCompat.checkSelfPermission(context, ACCESS_NETWORK_PERMISSION);
        boolean hasPermission = permissionResult == PackageManager.PERMISSION_GRANTED;

        return hasPermission ? new DefaultMonitor(context, listener, connectionType)
                : new NoopMonitor();
    }
}
