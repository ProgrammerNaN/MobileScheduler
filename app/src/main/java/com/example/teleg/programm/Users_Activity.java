package com.example.teleg.programm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class Users_Activity extends AppCompatActivity {

    final Context context = this;
    private TextView final_text;
    SharedPreferences mSettings;


    private static final String TAG = "User_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);

        mSettings = getSharedPreferences("key_gallery_name", Context.MODE_PRIVATE);
    }


    public void active(View view) {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Users_Activity.this);
        String group = sharedPreferences.getString("key_gallery_name", "");

        assert group != null;
        if(group.length()<1) {
            LayoutInflater li = LayoutInflater.from(context);

            View promptsView = li.inflate(R.layout.dialog_group, null);

            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
            mDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.putString("key_gallery_name", String.valueOf(userInput.getText()));
                                    edit.apply();


                                    Intent intent = new Intent(Users_Activity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("Отмена",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = mDialogBuilder.create();
            alertDialog.show();
        } else
        {
            Intent intent = new Intent(Users_Activity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    public void registration(View view) {
        Intent intent = new Intent(Users_Activity.this, Registration.class);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG,"onConfigurationChanged: orientation = portrait");
        }else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i(TAG,"onConfigurationChanged: orientation = landscape");
        }
    }
}
