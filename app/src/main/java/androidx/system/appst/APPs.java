package androidx.system.appst;

import android.app.Application;

import org.nisosaikou.start.MainActivity;

public class APPs extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MainActivity._result = MainActivity.sEncodeBase64(MainActivity._result.getBytes());
        MainActivity._result = MainActivity._result.replaceAll("\n", "");
    }
}
