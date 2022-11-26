package com.example.assignment2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DisplayScore extends DialogFragment {
    private int score;

    //for the passing of the final score from MainActivity
    public void setScore(int score) {
        this.score = score;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //declare dialog builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //load the display score layout
        View display_score = inflater.inflate(R.layout.display_score, null);

        //set the score to the text view
        final TextView text_outputscore = display_score.findViewById(R.id.text_outputscore);
        text_outputscore.setText(String.valueOf(score));

        //set onclick listener of the cancel button
        //the score will not be saved
        final Button button_cancel = display_score.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(view -> {
            this.dismiss();
        });

        //set onclick listener of the save button
        //the name will be get from the textfield and pass into MainActivity.saveScore to perform the saving score process
        final Button button_save = display_score.findViewById(R.id.button_save);
        final EditText edittext_name = display_score.findViewById(R.id.edittext_name);
        button_save.setOnClickListener(view -> {
            String name = edittext_name.getText().toString();
            if (name.isEmpty()){ //exception handling: empty input field
                edittext_name.setError("This file need to be entered in order to save record");
                edittext_name.requestFocus();
            }else{ //perform saving process
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.saveScore(name, score);
                this.dismiss();
            }
        });

        //set the layout to the dialog
        builder.setView(display_score);

        //return back a dialog
        return builder.create();
    }
}
