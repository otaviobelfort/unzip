package com.dev.maisrobotic.appunzip;
import android.Manifest;
import android.app.AlertDialog;
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

//data/data/android

//storage/self/primary/Download/wifi_scanner.zip
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        // solicita as permissõs
        checkRequiredPermissions();
        Path source = Paths.get(String.valueOf(getFileStreamPath("wifi_scanner.zip")));
        Path newDir = Paths.get(String.valueOf(Paths.get(Environment.getExternalStorageDirectory() + "/Music/")));

        btnUnzip = (Button) findViewById(R.id.btnUnzip);


        btnUnzip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Copia o Arquivo wifi_scanner.zip
                copyAsset("wifi_scanner.zip");

                String unzipLocation = Environment.getExternalStorageDirectory() + "/Zip/"; // destination folder location

                textView.setText(String.valueOf(Environment.getExternalStorageDirectory() + "/Zip/")); // Teste

                // Descompacta o arquivo zip
                Decompacta.unzip(Paths.get(Environment.getExternalStorageDirectory() + "/Zip/wifi_scanner.zip"),Paths.get(Environment.getExternalStorageDirectory() + "/Zip"));


            }
        });

    }

    // compia o arquivo.zip na pasta
    private void copyAsset(String filename) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zip/Otavio ";
        File dir  = new File(dirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            File outFile = new File(dirPath, filename);
            out = new FileOutputStream(outFile);
            copyFile(in,out);
            Toast.makeText(this,"Deu Certo New",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Não deu Certo New",Toast.LENGTH_SHORT).show();
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e){
                        e.printStackTrace();

                }
            }
            if (out != null){
                try {
                    out.close();
                } catch (IOException e){
                    e.printStackTrace();

                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
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









