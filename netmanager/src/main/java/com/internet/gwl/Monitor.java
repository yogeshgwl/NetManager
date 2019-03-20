package com.internet.gwl;

public interface Monitor extends LifecycleListener {

    interface ConnectivityListener {
        void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast);
    }

}
