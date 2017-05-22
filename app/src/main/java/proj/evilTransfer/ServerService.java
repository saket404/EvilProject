

package proj.evilTransfer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Bundle;



import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;

public class ServerService extends IntentService {

	private boolean serviceEnabled;
	
	private int port;
	private File saveLocation;
	private ResultReceiver serverResult;
	
	public ServerService() {
		super("ServerService");
		serviceEnabled = true;
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		

		
		port = ((Integer) intent.getExtras().get("port")).intValue();	
		saveLocation = (File) intent.getExtras().get("saveLocation");
		serverResult = (ResultReceiver) intent.getExtras().get("serverResult");	
		
		
		//signalActivity("Starting to download");
		 
		
		String fileName = "";
		
        ServerSocket welcomeSocket = null;
        Socket socket = null;
                      
		try {
			

			
				welcomeSocket = new ServerSocket(port);
				
				while(true && serviceEnabled)
				{
				
				//Listen for incoming connections on specified port
				//Block thread until someone connects 
				socket = welcomeSocket.accept();
				

				
				
				
				
				
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);			
				
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os);
				
				
				String inputData = "";
				
				
				
				signalActivity("About to start handshake");
				//Client-Server handshake
				

				String savedAs = "WDFL_File_" + System.currentTimeMillis();
			    File file = new File(saveLocation, savedAs);
			    
			    byte[] buffer = new byte[4096];
			    int bytesRead;
			    
			    FileOutputStream fos = new FileOutputStream(file);
			    BufferedOutputStream bos = new BufferedOutputStream(fos);
			    
			    while(true)
			    {
				    bytesRead = is.read(buffer, 0, buffer.length);
				    if(bytesRead == -1)
				    {
				    	break;
				    }			    
				    bos.write(buffer, 0, bytesRead);
				    bos.flush();
	
			    }
			    		    
	

			    
			    bos.close();
			    socket.close();
	
			    
			    signalActivity("File Transfer Complete, saved as: " + savedAs);
			    //Start writing to file

			}
			
	    
		} catch (IOException e) {
			signalActivity(e.getMessage());
			
			
		}
		catch(Exception e)
		{
			signalActivity(e.getMessage());

		}
			
		//Signal that operation is complete
		serverResult.send(port, null);
		
		
		
	
		
	}
	

	public void signalActivity(String message)
	{
		Bundle b = new Bundle();
		b.putString("message", message);
		serverResult.send(port, b);
	}
	
	
	public void onDestroy()
	{
		serviceEnabled = false;
		
		//Signal that the service was stopped 
		//serverResult.send(port, new Bundle());
		
		stopSelf();
	}

}
