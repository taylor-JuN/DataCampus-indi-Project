package com.example.plumtest2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class barplot extends AppCompatActivity {
    ImageView imgView;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    Button btn;
    String sickCode;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barplot);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        sickCode = intent.getExtras().getString("code");
        textView = findViewById(R.id.barplotText);
        textView2 = findViewById(R.id.barplotText2);
        textView3 = findViewById(R.id.warnText);
        textView4 = findViewById(R.id.titleCode2);
        imgView = (ImageView) findViewById(R.id.barplot);
        btn = findViewById(R.id.nextbtn);
        textView4.setText(sickCode);
        sendImageRequest();
        getApi();
    }
    public void sendImageRequest() {
        String url = "http://192.168.219.105:8011/cohort?msg="+sickCode;
        ImageLoadTask task = new ImageLoadTask(url,imgView);
        task.execute();
    }
    private void getApi(){

        new AsyncTask<Void, Void, String>() {
            ProgressDialog progress;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(barplot.this);
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

                        String sicksym1 = result.getString("a");
                        String freq = result.getString("Freq");
                        sb.append( (i+1) +" : " + sicksym1 + "\n");
                        sb2.append( freq +"명"+ "\n");
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
                    //서울시 오픈 API 제공(샘플 주소 json으로 작업)
                    result = Remote.getData("http://192.168.219.105:8011/cohorttext?msg="+sickCode);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("aaaaaa", result);

                return result;
            }
        }.execute();
    }
}
