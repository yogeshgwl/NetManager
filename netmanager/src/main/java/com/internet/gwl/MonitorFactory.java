package com.internet.gwl;

import android.content.Context;
import android.support.annotation.NonNull;

public interface MonitorFactory {

    @NonNull
    Monitor create(
            @NonNull Context context,
            int connectionType,
            @NonNull Monitor.ConnectivityListener listener);
}
