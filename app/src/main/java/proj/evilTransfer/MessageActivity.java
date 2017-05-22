package proj.evilTransfer;

import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MessageActivity extends Activity {

    EditText serverIp,smessage;
    TextView chat;
    Button connectPhones,sent;
    String serverIpAddress = "",msg = "",str;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        chat = (TextView) findViewById(R.id.chat);
        serverIp = (EditText) findViewById(R.id.server_ip);
        smessage = (EditText) findViewById(R.id.smessage);
        sent = (Button) findViewById(R.id.sent_button);

        sent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
            }
        });
        connectPhones = (Button) findViewById(R.id.connect_phones);

        connectPhones.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                serverIpAddress = serverIp.getText().toString();
                if (!serverIpAddress.equals(""))
                {
                    Thread clientThread = new Thread(new ClientThread());
                    clientThread.start();
                }
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
    class sentMessage implements Runnable
    {
        public void run()
        {
            try
            {
                InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
                Socket socket = new Socket(serverAddr, 10000); //create client socket
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                str = smessage.getText().toString();
                str = str + "\n";
                msg = msg + "Saket : " + str;

                handler.post(new Runnable() {
                    public void run() {
                        chat.setText(msg);
                    }
                });

                os.writeBytes(str);
                os.flush();
                os.close();
                socket.close();
            }
            catch(IOException e)
            {
            }

        }
    }

    @SuppressWarnings("deprecation")
    public class ClientThread implements Runnable
    {
        public void run()
        {
            try
            {
                while(true)
                {
                    InetAddress serverAddr = InetAddress.getByName(serverIpAddress);

                    Socket socket = new Socket(serverAddr, 10000); //create client socket
                    /*******************************************
                     setup i/p streams
                     ******************************************/
                    DataInputStream in = new
                            DataInputStream(socket.getInputStream());
                    String line = null;
                    while ((line = in.readLine()) != null)
                    {
                        msg = msg + "Lucky : " + line + "\n";
                        handler.post(new Runnable()
                        {
                            public void run()
                            {
                                chat.setText(msg);
                            }
                        });
                    }
                    in.close();
                    socket.close();
                    Thread.sleep(100);
                }
            }
            catch (Exception e)
            {

            }
        }
    }

}
