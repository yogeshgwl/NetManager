# Tovuti
A Simple Android library to check various types of network connections, to allow an app check internet connectivity status before making HTTP Requests. The library also offers real-time capabilities, so that you can do some task automatically when connected to the internet.

## Getting Started

These instructions will help you set up this library easily on your current project and working in no time. You only need a few configurations to start working!

## Installing

To be able to use the following library, you will need to add the following gradle dependency in your build.gradle(module:app) file

```
implementation 'com.androidstudy:tovuti:0.0.1'
```
That is the basic set up needed to be able to use the library in your applications!

## Permissions

First, we need to add these permission to our Android Manifest file :

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

Also, for android M and above, you must request the runtime permission for ACCESS_NETWORK_STATE.

That's it, you have set up the required permissions and ready to go!

## Quick Example

You can use it in both Android Activities and Fragments, as shown using the code snippets below. You can now replace the toasts with a call to make your HTTP Request now!

```java
import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
...

public class MyActivity extends Activity {
    private Tovuti tovuti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(...);

        tovuti = Tovuti.from(this)
                .monitor(new Monitor.ConnectivityListener() {
                    @Override
                    public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast) {
                        ...
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        tovuti.stop();
    }
}
```

Take a look at this sample project which has a sample implementation of the library.

## Contributing and Issues

Please feel free to contribute or open issues, if any and I will be happy to help out!

## License

This project is licensed under the MIT License.