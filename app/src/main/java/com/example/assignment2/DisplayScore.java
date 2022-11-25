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

    public void setScore(int score) {
        this.score = score;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        //load the name layout
        View display_score = inflater.inflate(R.layout.display_score, null);

        final TextView text_outputscore = display_score.findViewById(R.id.text_outputscore);
        text_outputscore.setText(score+"");

        final Button button_cancel = display_score.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(view -> {
            this.dismiss();
        });

        final Button button_save = display_score.findViewById(R.id.button_save);
        final EditText edittext_name = display_score.findViewById(R.id.edittext_name);
        button_save.setOnClickListener(view -> {
            String name = edittext_name.getText().toString();
            if (name.isEmpty()){
                edittext_name.setError("This file need to be entered in order to save record");
                edittext_name.requestFocus();
            }else{
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.saveScore(name);
                this.dismiss();
            }

        });

        //set the layout to the dialog
        builder.setView(display_score);

        //return back a dialog
        return builder.create();
    }
}
