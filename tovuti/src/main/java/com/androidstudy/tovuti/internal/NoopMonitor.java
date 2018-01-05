package com.androidstudy.tovuti.internal;

import com.androidstudy.tovuti.Monitor;

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
