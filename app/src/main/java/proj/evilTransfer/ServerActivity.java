package proj.evilTransfer;

import android.os.Bundle;
import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;



public class ServerActivity extends Activity {

    Button button_sent;
    EditText smessage;
    TextView chat, display_status;
    String str, msg = "";
    int serverport = 10000;
    ServerSocket serverSocket;
    Socket client;
    Handler handler = new Handler();
    WifiManager wmanager;



    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        wmanager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wmanager.getConnectionInfo().getIpAddress());
        smessage = (EditText) findViewById(R.id.smessage);
        chat = (TextView) findViewById(R.id.chat);
        display_status = (TextView) findViewById(R.id.display_status);
        display_status.setText("Server hosted on " + ip);

        Thread serverThread = new Thread(new serverThread());
        serverThread.start();
        button_sent = (Button) findViewById(R.id.button_sent);

        button_sent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_client, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    class sentMessage implements Runnable {
        public void run() {
            try {
                Socket client = serverSocket.accept();
                DataOutputStream os = new DataOutputStream(client.getOutputStream());
                str = smessage.getText().toString();
                msg = msg + "\n Lucky : " + str;

                handler.post(new Runnable() {
                    public void run() {
                        chat.setText(msg);
                    }
                });
                os.writeBytes(str);
                os.flush();
                os.close();
                client.close();
            } catch (IOException e) {
            }
        }
    }

    public class serverThread implements Runnable {
        public void run() {
            try{
                while(true) {
                    serverSocket = new ServerSocket(serverport);
                    Socket client = serverSocket.accept();

                    handler.post(new Runnable() {
                        public void run() {
                            display_status.setText("Connected");
                        }
                    });

                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String line = null;
                    while((line = in.readLine()) != null) {
                        msg = msg + "\n Saket : " + line;
                        handler.post(new Runnable() {
                            public void run() {
                                chat.setText(msg);
                            }
                        });
                    }
                    in.close();
                    client.close();
                    Thread.sleep(100);
                }
            }
            catch (Exception e) {

            }
        }
    }








}
