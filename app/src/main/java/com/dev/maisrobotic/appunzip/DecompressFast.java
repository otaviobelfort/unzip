package com.dev.maisrobotic.appunzip;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import android.util.Log;

public class DecompressFast {



    private String _zipFile;
    private String _location;

    public DecompressFast(String zipFile, String location) {
        _zipFile = zipFile;
        _location = location;

        _dirChecker("");
    }

    public void unzip() {
        try  {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    Files.createDirectories();
                    BufferedOutputStream bufout = new BufferedOutputStream(fout);
                    byte[] buffer = new byte[1024];
                    int read = 0;
                    while ((read = zin.read(buffer)) != -1) {
                        bufout.write(buffer, 0, read);
                    }




                    bufout.close();

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();


            Log.d("Unzip", "Unzipping complete. path :  " +_location );
        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);

            Log.d("Unzip", "Unzipping failed");
        }

    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }


}


/*
public class DecompressFast {

    public static OutputStream out;
    public static InputStream in;
    private static Object BufferedOutputStream;

    public static void descompactarZip(String dir, String arquivo) {
        Enumeration entries;
        ZipFile zipFile;

        try {
            zipFile = new ZipFile(dir+arquivo);
            entries = zipFile.entries();

            // Verifica ser o arquivo especificado existe
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    System.err.println("Descompactando diretório: "+ entry.getName());
                    (new File(entry.getName())).mkdir();
                    continue;
                }
                System.out.println("Descompactando arquivo:" + entry.getName());
                // Extraindo o Arquivo no diretório específicado
                copyInputStream(zipFile.getInputStream(entry),
                        new BufferedOutputStream(new FileOutputStream(dir+entry.getName())));
            }
            zipFile.close();
        } catch (IOException ioe) {
            System.err.println("Erro ao descompactar:" + ioe.getMessage());
            return;
        }
    }
    public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);

        in.close();
        out.close();
    }

}


*/