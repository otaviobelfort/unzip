package com.dev.maisrobotic.appunzip;

import android.os.Build;
import android.os.Environment;
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
import java.nio.file.Paths;
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

import androidx.annotation.RequiresApi;

import static java.nio.file.Files.newInputStream;

public class DecompressFast {



    private String _zipFile;
    private String _location;

    public DecompressFast(String zipFile, String location) {
        _zipFile = zipFile;
        _location = location;

        _dirChecker("");
    }



    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void unzip2(Path zipFile, Path destination) {

        try (ZipInputStream zis = new ZipInputStream(newInputStream(zipFile))) {

            ZipEntry zipElement = zis.getNextEntry();

            while (zipElement != null) {

                Path newFilePath = destination.resolve(zipElement.getName());
                if (zipElement.isDirectory()) {
                    Files.createDirectories(newFilePath);
                } else {
                    if (!Files.exists(newFilePath.getParent())) {
                        Files.createDirectories(newFilePath.getParent());
                    }
                    try (OutputStream bos = Files.newOutputStream(destination.resolve(newFilePath))) {
                        byte[] buffer = new byte[Math.toIntExact(zipElement.getSize())];

                        int location;

                        while ((location = zis.read(buffer)) != -1) {
                            bos.write(buffer, 0, location);
                        }
                    }
                }
                zipElement = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
// ---------------------------------------------------------------------
    public static boolean copyFile2(File source, File dest){
        try{
            // Declaration et ouverture des flux
            FileInputStream sourceFile = new FileInputStream(source);

            try{
                FileOutputStream destinationFile = null;

                try{
                    destinationFile = new FileOutputStream(dest);

                    // Lecture par segment de 0.5Mo
                    byte buffer[] = new byte[512 * 1024];
                    int nbLecture;

                    while ((nbLecture = sourceFile.read(buffer)) != -1){
                        destinationFile.write(buffer, 0, nbLecture);
                    }
                } finally {
                    destinationFile.close();
                }
            } finally {
                sourceFile.close();
            }
        } catch (IOException e){
            e.printStackTrace();
            return false; // Erreur
        }

        return true; // Rsultat OK
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