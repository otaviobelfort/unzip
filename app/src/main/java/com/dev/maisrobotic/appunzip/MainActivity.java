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
        //AssetManager assetManager = this.getAssets("default/wifi_scanner.zip");
        Path source = Paths.get(String.valueOf(getFileStreamPath("wifi_scanner.zip")));
        Path newDir = Paths.get(String.valueOf(Paths.get(Environment.getExternalStorageDirectory() + "/Music/")));


        //Files.copy(source,newDir.resolve(source.getFileName()));
       // copyFile("default/wifi_scanner.zip",String.valueOf(newDir),this);

        copyAsset("wifi_scanner.zip");


        // Exemplo 02 da classe -> DescompressFast
       // DecompressFast.copyFile2(new File("/storage/self/primary/Zip/wifi_scanner.zip"),new File(String.valueOf(newDir)));

        btnUnzip = (Button) findViewById(R.id.btnUnzip);

        AssetManager assetManager = getResources().getAssets();

        textView.setText(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zip"));



        // acessar diretótio do arquivo.zip na pasta assets
        File localZip = getAssetFile(this,"wifi_scanner.zip");


        btnUnzip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    // /data/data/com.dev.maisrobotic.appunzip
                //String cm = cm.getAssets("default/wifi_scanner.zip");. /data/data/com.dev.maisrobotic.appunzip /storage/self/primary/data

                String zipFile = (Environment.getExternalStorageDirectory() + String.valueOf(localZip)); //your zip file location

                //InputStream zipFile2 = Environment.getExternalStorageDirectory() + getAssets().open("wifi_scanner.zip");

                String unzipLocation = Environment.getExternalStorageDirectory() + "/Zip/"; // destination folder location
              //]  DecompressFast df= new DecompressFast(zipFile, unzipLocation);
                DecompressFast.unzip2(Paths.get(Environment.getExternalStorageDirectory() + String.valueOf(localZip)),Paths.get(Environment.getExternalStorageDirectory() + "/Zip/"));


            }
        });

    }

    /*
    * Retorna diretorio do arquivo
     */
    public String fileName(String fileName){

        return Environment.getDataDirectory() + "/" + fileName;
    }

    public File getAssetFile(Context context, String fileName) {
        File bitmap = null;

        Log.i("default", "getAssetFile: fileName: "+fileName);
        File filePath = null;

        try {

            filePath = context.getFileStreamPath(fileName);
            //bitmap = BitmapFactory.decodeFile(filePath.toString());
            return filePath;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("default", "getAssetFile: "+e.getMessage());
            e.printStackTrace();

        }
        return filePath;
    }

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

    // -----------------------------------------------------------------------




}









