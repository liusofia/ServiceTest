package service.com.sofia.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private String TAG = "sofia";
    public MyService() {
    }

    //创建一个mBinder对象对于Service中要进行的操作进行管理,这个mBinder对象是Activity可以获得到的
    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{

        public void startDownload(){
            Log.d(TAG,"startDownload");
        }

        public int getProgress(){
            Log.d(TAG,"getProgress");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        //服务创建时调用
        super.onCreate();
        Log.d(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
        //服务启动时调用，通常我们希望服务一启动就执行的操作我们写在onStartCommand中
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
        //服务销毁时调用
    }
}
