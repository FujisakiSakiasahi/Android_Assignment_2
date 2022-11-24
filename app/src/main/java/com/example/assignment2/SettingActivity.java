package com.example.assignment2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.SettingBinding;

public class SettingActivity extends AppCompatActivity {
    private SettingBinding settingBinding = null;

    public final static String SETTING_FILE_NAME = "setting.txt";
    private FileHandler fileHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        settingBinding = SettingBinding.inflate(getLayoutInflater());
        setContentView(settingBinding.getRoot());

        //load data setting
        fileHandler = new FileHandler(getApplicationContext(), SETTING_FILE_NAME);
        setCurrentSettingStatus(fileHandler.loadData());

        settingBinding.buttonSettingSetDefault.setOnClickListener(view -> {
            //set the settings view into default setting status
            settingBinding.checkboxSplashScreen.setChecked(false);

            //create string array that need to be saved
            String[] saveString = new String[1];

            savingSettingStatus(saveString);
        });

        settingBinding.buttonSettingSave.setOnClickListener(view -> {
            //create string array that need to be saved
            String[] saveString = new String[1];

            savingSettingStatus(saveString);
        });

        settingBinding.buttonSettingCancel.setOnClickListener(view -> finish());
    }

    private void savingSettingStatus(String[] saveString) {
        //add item into string array
        saveString[0] = "Skip Splash Screen = " + settingBinding.checkboxSplashScreen.isChecked();

        //save the setting status
        fileHandler.setData(saveString);
        fileHandler.saveData();
    }

    private void setCurrentSettingStatus(String[] status) {
        String[] stat = new String[status.length];

        for(int i = 0; i<status.length; i++){
            stat[i] = status[i].split(" = ")[1];
        }

        settingBinding.checkboxSplashScreen.setChecked(Boolean.parseBoolean(stat[0]));
    }
}
