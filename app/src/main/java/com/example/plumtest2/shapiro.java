package com.example.plumtest2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class shapiro extends AppCompatActivity {
    TextView textView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shapiro);

        textView = (TextView)findViewById(R.id.parsetext);
        getApi();
    }

    private void getApi(){

        new AsyncTask<Void, Void, String>() {
            ProgressDialog progress;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(shapiro.this);
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
                try {
                    JSONObject json = new JSONObject(s);


                    JSONArray rows = json.getJSONArray("realtimeArrivalList");


                    int length = rows.length();
                    for(int i=0; i < length; i ++){
                        JSONObject result = (JSONObject) rows.get(i);
                        String trainName = result.getString("subwayList");
                        sb.append(trainName + "\n");
                        Log.d("abcdef", trainName);
                    }

                }catch (Exception e ){}

                textView.setText(sb.toString());
                progress.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                String result = "";
                try {
                    //서울시 오픈 API 제공(샘플 주소 json으로 작업)
                    result = Remote.getData("http://192.168.219.105:8011/echo");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }
        }.execute();
    }
}