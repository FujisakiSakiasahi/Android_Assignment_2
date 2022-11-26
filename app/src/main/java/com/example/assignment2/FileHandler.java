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

/**
 * FileHandler:<br/>
 * - handling read files and write files
 */
public class FileHandler {
    private String fileName;
    private ArrayList<String> data;
    private Context context;

    /**
     * FileHandler Constructor:<br/>
     * - use to create an object of FileHandler<br/>
     * - will be used for performing read and write file action
     *
     * @param context Application context
     * @param fileName Name of the file that will be read and written
     */
    public FileHandler(Context context, String fileName){
        this.fileName = fileName;
        this.data = new ArrayList<String>();
        this.context = context;
    }

    //unused code, to be deleted
    public FileHandler(Context context, String fileName, String[] input) {
        this.fileName = fileName;
        this.data = new ArrayList<String>(Arrays.asList(input));
        this.context = context;
    }

    /**
     * loadData:<br/>
     * - use to load the data from a file <br/>
     * - the data will be return as a string array based on the lines in the file
     *
     * @return array that stores the data
     */
    public String[] loadData(){
        try {
            //open and scanning a file line by line
            FileInputStream fis = context.openFileInput(fileName);
            Scanner sc = new Scanner(fis);

            //add the data from the file into a string array
            while(sc.hasNextLine()){
                data.add(sc.nextLine());
            }

            //close the scanner and the file
            sc.close();
            fis.close();
        }catch (FileNotFoundException e){ //exception handling
            Log.d("Error", "No data found");
        }catch (Exception e){
            Log.d("Error", e.getLocalizedMessage());
        }
        //returning the data string array
        return data.toArray(new String[0]);
    }

    /**
     * saveData:<br/>
     * - use to write the data into a file
     */
    public void saveData(){
        try {
            //open file
            FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);

            //write the data into the file
            PrintStream ps = new PrintStream(fos);
            for (int i = 0 ; i < 10 ; i++) {
                ps.println(data.get(i));
            }

            //close the writer and the file
            fos.close();
            ps.close();
        } catch (FileNotFoundException e) {
            Log.d("Error", "File is missing");
        } catch (Exception e){
            Log.d("Error", e.getLocalizedMessage());
        }
    }

    //unused code, to be deleted
    public String getFileName() {
        return fileName;
    }

    //unused code, to be deleted
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    //unused code, to be deleted
    public String[] getData() {
        return data.toArray(new String[0]);
    }

    /**
     * setData:<br/>
     * - use to set the data that will be saved into the file
     *
     * @param data the data that will be written into the file
     */
    public void setData(String[] data) {
        this.data = new ArrayList<String>(Arrays.asList(data));
    }
}
