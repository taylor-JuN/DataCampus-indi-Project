package com.example.plumtest2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class what extends AppCompatActivity {
    String sickCode;
    ImageView imgView;
    Button btn;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.what);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        sickCode = intent.getExtras().getString("code");
        imgView = findViewById(R.id.real);
        btn = findViewById(R.id.qqqbtn);

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), what2.class);
                intent.putExtra("code", sickCode);
                startActivity(intent);
            }
        });

        sendImageRequest();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Plot이미지 다운로드 중!@");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        progressDialog.show();

    }

    public void sendImageRequest() {
        String url = "http://192.168.219.105:8011/apriori?msg=" + sickCode;
        ImageLoadTask task = new ImageLoadTask(url, imgView);
        task.execute();
    }
}

