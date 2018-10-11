package com.example.teja.autotheft;


import android.app.Service;
import android.content.Context;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MakeToast extends Service{


    public static String phno="09165602840";
    public static String mess="default";
    String temp1="",temp2="";
    String sdmess="",sdphno="09165602840";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Next msg service", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "will activate", Toast.LENGTH_LONG).show();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File dir = new File(sdcard.getAbsolutePath());
            File fmess = new File(dir, "Mesaage.txt");
            File fphno = new File(dir, "Phone.txt");
            FileInputStream fmessio=new FileInputStream(fmess);
            FileInputStream fphio=new FileInputStream(fphno);
            BufferedReader messread=new BufferedReader(new InputStreamReader(fmessio));
            BufferedReader phread=new BufferedReader(new InputStreamReader(fphio));
            try {
                while ((temp1=messread.readLine())!=null)
                    sdmess+=temp1+"\n";
                Toast.makeText(getBaseContext(),"Done reading 'Message.txt'", Toast.LENGTH_SHORT).show();
                while ((temp2=phread.readLine())!=null)
                    sdphno=temp2;
                Toast.makeText(getBaseContext(),"Done reading 'Phone.txt'", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            messread.close();
            phread.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sdphno, null, sdmess, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent to "+sdphno,
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        Toast.makeText(this, sdmess, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "End of service", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Remember my choice in autostart", Toast.LENGTH_LONG).show();
    }

}
