package com.example.plumtest2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button submitbtn;

//    private ImageView imgAndroid;
//    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputinfo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        textView = findViewById(R.id.codeText);
        editText = findViewById(R.id.codeEdit);
//        final String code = editText.getText().toString();
//        Log.d("memememe", code);
        Button submitbtn = findViewById(R.id.submitbtn);


//        apibtn = (Button)findViewById(R.id.api);
//        barplotbtn = (Button)findViewById(R.id.barplot);
//        shabtn = (Button)findViewById(R.id.shapriro);
//        whatbtn = (Button)findViewById(R.id.what);

        submitbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), importAPI.class);
                intent.putExtra("code", editText.getText().toString());

                startActivity(intent);
            }
        });
//        barplotbtn.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(getApplicationContext(), barplot.class);
//                startActivity(intent);
//            }
//        });
//        shabtn.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(getApplicationContext(), shapiro.class);
//                startActivity(intent);
//            }
//        });
//        whatbtn.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(getApplicationContext(), what.class);
//                startActivity(intent);
//            }
//        });
    }
}
