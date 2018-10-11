package com.example.teja.autotheft;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements LocationListener{


    private static EditText etxt;
    private static TextView txt;
    private static Button bt;
    public static String str="default text";
    private String simID;
    private String IMEI;
    private String latit="\nnot found";
    private String longit="\nlocation";
    LocationManager locationManager;
    String mprovider;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etxt=(EditText)findViewById(R.id.editText2mainid);
        txt=(TextView)findViewById(R.id.textView2mainid);
        bt=(Button)findViewById(R.id.buttonmainid);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        simID = tm.getSimSerialNumber();
        if (simID != null)
            Toast.makeText(this, "SIM card ID: " + simID, Toast.LENGTH_LONG).show();

        IMEI = tm.getDeviceId();
        if (IMEI != null)
            Toast.makeText(this, "IMEI number: " + IMEI, Toast.LENGTH_LONG).show();

        str="SIM card ID: " + simID+"\nIMEI number: " + IMEI;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }
        str+=longit+latit;
        str+="\nDelivers msg to: "+MakeToast.phno;
        txt.setText(str);
        MakeToast.mess=str;
    }
    @Override
    public void onLocationChanged(Location location) {
        longit="\nCurrent Longitude:" + location.getLongitude();
        Toast.makeText(this, longit, Toast.LENGTH_LONG).show();
        latit="\nCurrent Latitude:" + location.getLatitude();
        Toast.makeText(this, latit, Toast.LENGTH_LONG).show();
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath());
        File file1 = new File(dir, "Mesaage.txt");
        FileOutputStream os = null;
        String temp3="SIM card ID: " + simID+"\nIMEI number: " + IMEI+longit+latit;
        try {
            os = new FileOutputStream(file1);
            os.write(temp3.getBytes());
            os.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        temp3+="\nDelivers msg to: "+MakeToast.phno;
        txt.setText(temp3);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void onclick(View view){
    MakeToast.phno=etxt.getText().toString();
        Toast.makeText(this, "Messages will be delivered to "+etxt.getText().toString(), Toast.LENGTH_LONG).show();
        if(str!=null){
            str="SIM card ID: " + simID+"\nIMEI number: " + IMEI +longit+latit+"\nDelivers msg to: "+MakeToast.phno;
        }
        txt.setText(str);

        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath());
        File file1 = new File(dir, "Mesaage.txt");
        File file2 = new File(dir, "Phone.txt");
        FileOutputStream os = null;
        FileOutputStream os1 = null;
        String data = str;
        String phdata=etxt.getText().toString();
        try {
            os = new FileOutputStream(file1);
            os1 = new FileOutputStream(file2);
            os.write(data.getBytes());
            os.close();
            os1.write(phdata.getBytes());
            os1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
