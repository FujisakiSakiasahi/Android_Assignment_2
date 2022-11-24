package com.example.assignment2;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class FileHandler {
    private String fileName;
    private ArrayList<String> data;
    private Context context;

    public FileHandler(Context context, String fileName){
        this.fileName = fileName;
        this.data = null;
        this.context = context;
    }

    public FileHandler(Context context, String fileName, String[] input) {
        this.fileName = fileName;
        this.data = new ArrayList<String>(Arrays.asList(input));
        this.context = context;
    }

    public String[] loadData(){
        try {
            FileInputStream fis = context.openFileInput(fileName);
            Scanner sc = new Scanner(fis);

            while(sc.hasNextLine()){
                data.add(sc.nextLine());
            }

            sc.close();
            fis.close();
        }catch (FileNotFoundException e){
            Log.d("Error", "No data found");
        }catch (Exception e){
            Log.d("Error", e.getLocalizedMessage());
        }
        return data.toArray(new String[0]);
    }

    public void saveData(){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);

            PrintStream ps = new PrintStream(fos);

            for (int i = 0 ; i < 10 ; i++) {
                ps.println(data.get(i));
            }

            fos.close();
            ps.close();
        } catch (FileNotFoundException e) {
            Log.d("Error", "File is missing");
        } catch (Exception e){
            Log.d("Error", e.getLocalizedMessage());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getData() {
        return data.toArray(new String[0]);
    }

    public void setData(String[] data) {
        this.data = new ArrayList<String>(Arrays.asList(data));
    }
}
