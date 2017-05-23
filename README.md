# EvilProject
Evil Transfer 1.0 (recv send search report and chat)


Steps to Run

1. Install Android Studio and SDK.
2. Import project from github repository or Clone repository and import by building in android studio.
3. Build APK.
4. Test on 2 physical deviced with wifi direct.


Bugs

1. Due to limitation of Wifi direct API authorization must be done externally too.
2. One can only send or recieve at an instance. (Need to restart to server in order to swap).
3. Need to enter ip address inorder to connect to the chat. (little buggy).

Future Development

1. Optimize performance inorder for fast transfer.
2. Implement Login and profile system.
3. Implement group chat system. 

Files Distribution

1. Chat Implementation Files 
   1.1 ServerActivity.java : Initialization of server chat the sender initializes this. 
   1.2 MessageActivity.java: initialization of client or sender to the chat.

2. Report Bug Feature Files
   2.1 Report.java 
  
3. Main Initiator activites Files
  3.1 MainActivity.java: Intro to App
  3.2 MainActivity2.java: Main menu App
  
4. Transfer Files Implementeation Files: (ClientActivity.java, ClientService.java, FileBrowser.java, Recieve.java, ServerService.java, WiFiClientBroadcastReceiver.java, WiFiServerBroadcastReceiver.java)
  
