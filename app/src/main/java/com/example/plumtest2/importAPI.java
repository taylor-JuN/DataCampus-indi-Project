package com.example.plumtest2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class importAPI extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView title;
    Button btn1;
    Button btn2;
    Button barplotbtn;
    Button nextbtn;
    String x;
    String y;
    String sickCode;
    ImageView imgView;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.api);
        title = findViewById(R.id.titleCode);

        Intent intent = getIntent();
        sickCode = intent.getExtras().getString("code");

        title.setText(sickCode);


        textView = (TextView)findViewById(R.id.parsetext);
        textView2 = (TextView)findViewById(R.id.parsetext2);
        barplotbtn = (Button)findViewById(R.id.barplotbtn);
        barplotbtn.setTypeface(null, Typeface.BOLD_ITALIC);
        nextbtn = (Button)findViewById(R.id.nextbtn);
        nextbtn.setTypeface(null, Typeface.BOLD_ITALIC);
        barplotbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), barplot.class);
                intent.putExtra("code", sickCode);
                startActivity(intent);
            }
        });

        nextbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), what.class);
                intent.putExtra("code", sickCode);
                startActivity(intent);
            }
        });

        getApi();
    }
    public void onButton1Clicked(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?sxsrf=ALeKk03cx0dw-bKOZef-I9QM44Q1eACZ-A%3A1596924151697&ei=9yAvX66DKsjYhwOK-ZNw&q="+x));
        startActivity(intent);
    }
    public void onButton2Clicked(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?sxsrf=ALeKk03cx0dw-bKOZef-I9QM44Q1eACZ-A%3A1596924151697&ei=9yAvX66DKsjYhwOK-ZNw&q="+y));
        startActivity(intent);
    }





    private void getApi(){

        new AsyncTask<Void, Void, String>() {
            ProgressDialog progress;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(importAPI.this);
                progress.setTitle("다운로드");
                progress.setMessage("download");
                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
                progress.setCancelable(false);
                progress.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                StringBuffer sb = new  StringBuffer();
                StringBuffer sb2 = new  StringBuffer();
                try {
                    JSONObject json = new JSONObject(s);
                    JSONArray rows = json.getJSONArray("data");


                    int length = rows.length();

                    for(int i=0; i < length; i ++){
                        JSONObject result = (JSONObject) rows.get(i);

                        String sicksym1 = result.getString("한글명칭");
                        String sicksym2 = result.getString("영문명칭");
                        x = sicksym1;
                        y = sicksym2;
                        sb.append(sicksym1 + "\n");
                        sb2.append(sicksym2 + "\n");
                        Log.d("abcdef", sicksym1);
                    }

                }catch (Exception e ){}

                textView.setText(sb.toString());
                textView2.setText(sb2.toString());
                progress.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                String result = "";
                try {
                    result = Remote.getData("http://192.168.219.105:8011/come?msg="+sickCode);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("aaaaaa", result);

                return result;
            }
        }.execute();
    }

}
