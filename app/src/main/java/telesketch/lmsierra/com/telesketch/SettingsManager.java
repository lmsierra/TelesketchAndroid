package telesketch.lmsierra.com.telesketch;

import android.content.Context;

import java.lang.ref.WeakReference;

public class SettingsManager {

    private static SettingsManager instance;
    private WeakReference<Context> context;

    private SettingsManager(Context context){
        this.context = new WeakReference<>(context);
    }

    public static synchronized SettingsManager getInstance(Context context){
        if(instance == null){
            instance = new SettingsManager(context);
        }
        return instance;
    }

    public void
}
