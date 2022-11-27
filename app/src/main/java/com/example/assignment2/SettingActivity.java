package com.example.assignment2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.SettingBinding;

import java.io.File;

public class SettingActivity extends AppCompatActivity {
    private SettingBinding settingBinding = null;

    public final static String SETTING_FILE_NAME = "setting.txt";
    private FileHandler fileHandler;
    private static boolean gotChanges = false;

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

        //use to see if the settings got changes or not
        //set oncheck change listener for checkbox
        settingBinding.checkboxSplashScreen.setOnCheckedChangeListener((compoundButton, b) -> gotChanges = true);
        //set oncheck change listener for checkbox
        settingBinding.switchSoundEffect.setOnCheckedChangeListener((compoundButton, b) -> gotChanges = true);
        //set on seek bar change listener
        settingBinding.seekbarSoundEffectVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                gotChanges = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //set the onclick listener to the set default button
        settingBinding.buttonSettingSetDefault.setOnClickListener(view -> {
            //set the settings to default
            setDefaultSettingStatus();
            //save the setting
            savingSettingStatus();
            //toast the success message
            Toast.makeText(this, getString(R.string.setting_toast_set_default), Toast.LENGTH_SHORT).show();

            gotChanges = false;
        });

        //set the onclick listener to the save button
        settingBinding.buttonSettingSave.setOnClickListener(view -> {
            //save the setting
            savingSettingStatus();
            //toast the success message
            Toast.makeText(this, getString(R.string.setting_toast_save), Toast.LENGTH_SHORT).show();

            gotChanges = false;
        });

        //set the onclick listener to the close button
        //use to close the current activity
        settingBinding.buttonSettingClose.setOnClickListener(view -> {
            if(gotChanges){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Unsave Settings Changes");
                builder.setMessage("There are unsaved changes, do you want to leave without saving them?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gotChanges =false;
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                finish();
            }
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
        settingBinding.seekbarSoundEffectVolume.setProgress(Integer.parseInt(stat[2]));
    }

    /**
     * setDefaultSettingStatus: <br/>
     * - set the settings to default value
     */
    private void setDefaultSettingStatus() {
        //set the settings view into default setting status
        settingBinding.checkboxSplashScreen.setChecked(false);
        settingBinding.switchSoundEffect.setChecked(true);
        settingBinding.seekbarSoundEffectVolume.setProgress(50);
    }

    /**
     * savingSettingStatus: <br/>
     * - use to save the settings into the file
     */
    private void savingSettingStatus() {
        //create string array that need to be saved
        String[] saveString = new String[3];

        //add item into string array
        saveString[0] = "Skip Splash Screen = " + settingBinding.checkboxSplashScreen.isChecked();
        saveString[1] = "Sound Effect = " + settingBinding.switchSoundEffect.isChecked();
        saveString[2] = "Sound Effect Volume = " + settingBinding.seekbarSoundEffectVolume.getProgress();

        //save the setting status
        fileHandler.setData(saveString);
        fileHandler.saveData();
    }
}
