package com.dev.maisrobotic.appunzip;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import java.io.*;

import java.util.*;

import java.util.zip.*;

import java.text.*;
//data/data/android

//storage/self/primary/Download/wifi_scanner.zip
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {

    private static final String APP_NAME = " Otavio";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 99;
    private String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String dataPath = SDPath + "/Android/";
    private String zipPath = SDPath + "/Zip/";
    private String unzipPath = SDPath + "/Zip/";

    final static String TAG = MainActivity.class.getName();

    Button btnUnzip, btnZip;
    CheckBox chkParent;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textview);
        checkRequiredPermissions();
        try {
            Files.copy(Paths.get(Environment.getExternalStorageDirectory() + "/Zip/wifi_scanner.zip"),Paths.get(Environment.getExternalStorageDirectory() + "/Music/"));
        } catch (IOException e) {
            Toast.makeText(this,"Não deu certo",Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }

        btnUnzip = (Button) findViewById(R.id.btnUnzip);
        //FileInputStream zip = new FileInputStream("/data/data/com.dev.maisrobotic.appunzip/wifi_scanner.zip",Context.MODE_PRIVATE);

        AssetManager assetManager = this.getAssets();
        textView.setText(String.valueOf(getFileStreamPath("wifi_scanner.zip")));

        //copyFile("default/wifi_scanner.zip",
          //      Environment.getExternalStorageDirectory() + "/data/data/com.dev.maisrobotic.appunzip/",
            //    this);

        btnUnzip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //String cm = cm.getAssets("default/wifi_scanner.zip");. /data/data/com.dev.maisrobotic.appunzip

                String zipFile = (Environment.getExternalStorageDirectory() + "/Zip/wifi_scanner.zip"); //your zip file location

                //InputStream zipFile2 = Environment.getExternalStorageDirectory() + getAssets().open("wifi_scanner.zip");

                String unzipLocation = Environment.getExternalStorageDirectory() + "/data/data/com.dev.maisrobotic.appunzip/files"; // destination folder location
              //]  DecompressFast df= new DecompressFast(zipFile, unzipLocation);
                DecompressFast.unzip2(Paths.get(Environment.getExternalStorageDirectory() + "/Zip/wifi_scanner.zip"),Paths.get(Environment.getExternalStorageDirectory() + "/Podcasts/"));


            }
        });

    }

    /*
    * Retorna diretorio do arquivo
     */
    public String fileName(String fileName){

        return Environment.getDataDirectory() + "/" + fileName;
    }


    public static String copyAsset(Context context, String assetName, File dir) throws IOException {
        File outFile = new File(dir, assetName);
        if (!outFile.exists()) {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open(assetName);
            OutputStream out = new FileOutputStream(outFile);
           // copyFile(in, out);
            in.close();
            out.close();
        }
        return outFile.getAbsolutePath();
    }


    private void copyFile(String assetPath, String localPath, Context context) {
        try {
            InputStream in = context.getAssets().open(assetPath);
            FileOutputStream out = new FileOutputStream(localPath);
            int read;
            byte[] buffer = new byte[4096];
            while ((read = in.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
            out.close();
            in.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

/*
* Permissão de leitura e escrita
 */
    private boolean checkRequiredPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.w(APP_NAME, "Requesting permission");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this).setTitle("Permission required").setMessage("Storage permission required").setPositiveButton("Yes", (dialogInterface, i) -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)).create().show();
                return false;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                return false;
            }
        }
        return true;
    }



}









