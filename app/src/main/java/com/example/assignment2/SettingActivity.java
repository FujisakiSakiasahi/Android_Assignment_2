package com.example.assignment2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.SettingBinding;

import java.io.File;

public class SettingActivity extends AppCompatActivity {
    private SettingBinding settingBinding = null;

    public final static String SETTING_FILE_NAME = "setting.txt";
    private FileHandler fileHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        //set up view binding
        settingBinding = SettingBinding.inflate(getLayoutInflater());
        setContentView(settingBinding.getRoot());

        //initialize the file handler
        fileHandler = new FileHandler(getApplicationContext(), SETTING_FILE_NAME);

        //check if the setting status file is created or not
        //if exists load status else create file
        checkSettingStatusFile();

        //set the onclick listener to the set default button
        settingBinding.buttonSettingSetDefault.setOnClickListener(view -> {
            //set the settings to default
            setDefaultSettingStatus();
            //save the setting
            savingSettingStatus();
            //toast the success message
            Toast.makeText(this, getString(R.string.setting_toast_set_default), Toast.LENGTH_SHORT).show();
        });

        //set the onclick listener to the save button
        settingBinding.buttonSettingSave.setOnClickListener(view -> {
            //save the setting
            savingSettingStatus();
            //toast the success message
            Toast.makeText(this, getString(R.string.setting_toast_save), Toast.LENGTH_SHORT).show();
        });

        //set the onclick listener to the close button
        //use to close the current activity
        settingBinding.buttonSettingClose.setOnClickListener(view -> {
            finish();
        });
    }//end of onCreate

    /**
     * checkSettingStatusFile: <br/>
     * - check if the setting status file is created or not <br/>
     * - if exists load status else create file
     */
    private void checkSettingStatusFile() {
        //check if file exist
        if(new File(getApplicationContext().getFilesDir(), SETTING_FILE_NAME).exists()){
            //load data setting
            setCurrentSettingStatus(fileHandler.loadData());
        }else{
            //set the setting to the default status
            setDefaultSettingStatus();
            //create and save the file
            savingSettingStatus();
        }
    }

    /**
     * setCurrentSettingStatus: <br/>
     * - use to load the data settings
     *
     * @param status the settings that need to be loaded
     */
    private void setCurrentSettingStatus(String[] status) {
        String[] stat = new String[status.length];

        for(int i = 0; i<status.length; i++){
            stat[i] = status[i].split(" = ")[1];
        }

        settingBinding.checkboxSplashScreen.setChecked(Boolean.parseBoolean(stat[0]));
        settingBinding.switchSoundEffect.setChecked(Boolean.parseBoolean(stat[1]));
    }

    /**
     * setDefaultSettingStatus: <br/>
     * - set the settings to default value
     */
    private void setDefaultSettingStatus() {
        //set the settings view into default setting status
        settingBinding.checkboxSplashScreen.setChecked(false);
        settingBinding.switchSoundEffect.setChecked(true);
    }

    /**
     * savingSettingStatus: <br/>
     * - use to save the settings into the file
     */
    private void savingSettingStatus() {
        //create string array that need to be saved
        String[] saveString = new String[2];

        //add item into string array
        saveString[0] = "Skip Splash Screen = " + settingBinding.checkboxSplashScreen.isChecked();
        saveString[1] = "Sound effect = " + settingBinding.switchSoundEffect.isChecked();

        //save the setting status
        fileHandler.setData(saveString);
        fileHandler.saveData();
    }
}
