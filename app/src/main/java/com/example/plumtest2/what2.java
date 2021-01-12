package com.example.plumtest2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class what2 extends AppCompatActivity {
    TextView textview;
    TextView textview2;
    TextView textview3;
    TextView textview4;
    String sickCode;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.what2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        sickCode = intent.getExtras().getString("code");

        textview = findViewById(R.id.explainText);
        textview2 = findViewById(R.id.explainText2);
        textview3 = findViewById(R.id.explainText3);
        textview4 = findViewById(R.id.titleCode123);
        textview4.setText(sickCode);

        getApi();
    }
    private void getApi(){

        new AsyncTask<Void, Void, String>() {
            ProgressDialog progress;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(what2.this);
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
                StringBuffer sb3 = new  StringBuffer();
                try {
                    JSONObject json = new JSONObject(s);
                    JSONArray rows = json.getJSONArray("data");


                    int length = rows.length();

                    for(int i=0; i < length; i ++){
                        JSONObject result = (JSONObject) rows.get(i);

                        String sicksym1 = result.getString("lhs");
                        String sicksym2 = result.getString("rhs");
                        String sicksym3 = result.getString("lift");
                        sb.append(sicksym1 + "\n");
                        sb2.append(sicksym2 + "\n");
                        sb3.append(sicksym3 + "\n");
                        Log.d("abcdef", sicksym1);
                    }

                }catch (Exception e ){}

                textview.setText(sb.toString());
                textview2.setText(sb2.toString());
                textview3.setText(sb3.toString());


                progress.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                String result = "";
                try {
                    result = Remote.getData("http://192.168.219.105:8011/aprtext?msg="+sickCode);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("aaaaaa", result);

                return result;
            }
        }.execute();
    }
}
