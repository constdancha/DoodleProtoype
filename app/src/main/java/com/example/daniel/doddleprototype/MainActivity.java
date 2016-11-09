package com.example.daniel.doddleprototype;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button brushsize;
    Button brushsize2;
    Button brushsize3;
    Button colorButton;
    ImageButton picButton;
    ImageButton eraseButton;
    Bitmap bitmap;
    DoddleView doddleview;
    View layout;
    AlertDialog dialog;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(RESULT_OK == resultCode){
                Uri imageUri = data.getData();

                try {
                    Log.d("Dani","Cool");
                    bitmap = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), imageUri);
                    doddleview.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        brushsize = (Button)findViewById(R.id.brushButton);
        brushsize2 = (Button)findViewById(R.id.brushButton2);
        brushsize3 = (Button)findViewById(R.id.brushButton3);
        eraseButton = (ImageButton)findViewById(R.id.eraseButton);
        colorButton = (Button)findViewById(R.id.colorButton);
        doddleview = (DoddleView)findViewById(R.id.doddleview);
        picButton = (ImageButton)findViewById(R.id.pictureButton);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.color_dialog, (ViewGroup)findViewById(R.id.topelement));

        final SeekBar redBar = (SeekBar)layout.findViewById(R.id.red_color);
        final SeekBar greenBar = (SeekBar)layout.findViewById(R.id.green_color);
        final SeekBar blueBar = (SeekBar)layout.findViewById(R.id.blue_color);



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Color Picker");
        builder.setView(layout);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int red = redBar.getProgress();
                int green = greenBar.getProgress();
                int blue = blueBar.getProgress();
                int color = Color.rgb(red,green,blue);

                doddleview.setColor(color);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        dialog = builder.create();


        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doddleview.clear();

            }
        });

        brushsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doddleview.setWidth(50);
            }
        });
        brushsize2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doddleview.setWidth(20);
            }
        });
        brushsize3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doddleview.setWidth(5);
            }
        });
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });
        picButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        redBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView)layout.findViewById(R.id.red_num);
                tv.setText(Integer.toString(i));
                layout.setBackgroundColor(Color.rgb(i,greenBar.getProgress(),blueBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        greenBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView)layout.findViewById(R.id.green_num);
                tv.setText(Integer.toString(i));
                layout.setBackgroundColor(Color.rgb(redBar.getProgress(),i,blueBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView)layout.findViewById(R.id.blue_num);
                tv.setText(Integer.toString(i));
                layout.setBackgroundColor(Color.rgb(redBar.getProgress(),greenBar.getProgress(),i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }



}
