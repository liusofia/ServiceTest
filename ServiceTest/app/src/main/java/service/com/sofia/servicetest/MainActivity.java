package service.com.sofia.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "sofia";
    private MyService.DownloadBinder downloadBinder;

    //创建ServiceConnection 重写onServiceConnected和onServiceDisconnected()方法，这两个方法会在活动和服务绑定时候调用和接触绑定时候调用
    //在onServiceConnected方法中，通过乡下转型得到DownloadBinder实例，活动和服务之间的关系就变得非常紧密了。
    //因为可以通过binder调用DownloadBinder中的任何一个方法
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = findViewById(R.id.start_service);
        Button stopService = findViewById(R.id.stop_service);
        Button bindService = findViewById(R.id.bind_service);
        Button unbindService = findViewById(R.id.unbind_service);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_service:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                //注意完全由activity来决定service何时停止的，服务有没有办法让自己停止下来呢？当然有，只需要在MyService的任何一个位置调用stopSelf()
                //就能让Service自己停下来
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                //BIND_AUTO_CREATE表示活动和服务丙丁完毕后自动创建服务
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
                default:
                    break;
        }
    }
}
