package com.internet.gwl.internal;

import com.internet.gwl.Monitor;

public class NoopMonitor implements Monitor {
    @Override
    public void onStart() {
        //no-op
    }

    @Override
    public void onStop() {
        //no-op
    }
}
