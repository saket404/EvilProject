package proj.evilTransfer;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

import java.io.File;

public class MainActivity2 extends Activity {

    public final int fileRequestID = 55;
    public final int port = 7950;


    private WifiP2pManager wifiManager;
    private WifiP2pManager.Channel wifichannel;
    private BroadcastReceiver wifiServerReceiver;

    private IntentFilter wifiServerReceiverIntentFilter;

    private String path;
    private File downloadTarget;

    private Intent serverServiceIntent;

    private boolean serverThreadActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main2, menu);
        return true;
    }

    public void startFileBrowseActivity(View view) {

        Intent clientStartIntent = new Intent(this, FileBrowser.class);
        startActivityForResult(clientStartIntent, fileRequestID);
        //Path returned to onActivityResult

    }

    public void startRecieve(View view) {
        Intent clientStartIntent = new Intent(this, Recieve.class);
        startActivity(clientStartIntent);
    }

    public void startClientActivity(View view) {
        Intent clientStartIntent = new Intent(this, ClientActivity.class);
        startActivity(clientStartIntent);
    }

    public void startReport(View view) {
        Intent clientStartIntent = new Intent(this, Report.class);
        startActivity(clientStartIntent);
    }

}
