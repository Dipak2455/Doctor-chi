package com.example.doctorchai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class MainActivity extends AppCompatActivity {

    RingProgressBar mRingProgressBar;
    int process=0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected()){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Connection Error !")
                    .setMessage("Please Check your Internet Connection.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();

        }else {

            Toasty.success(MainActivity.this, "Welcome Doctor Chai.!", Toast.LENGTH_SHORT, true).show();


        }


        mRingProgressBar = (RingProgressBar) findViewById(R.id.progress_bar_2);

        // Set the progress bar's progress
        RngProcess();



    }



    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnected();
    }
    public void RngProcess(){
        mRingProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener()
        {

            @Override
            public void progressToComplete()
            {

               Intent Browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://doctorchaibd.com"));
                startActivity(Browser);
                //Toasty.success(MainActivity.this, " Welcome !", Toast.LENGTH_SHORT, true).show();
                finish();
                //Uri.parse("http://doctorchaibd.com");
            }
        });
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==0){
                    if(process<100){
                        process++;
                        mRingProgressBar.setProgress(process);

                    }
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=100;i>0;i--) {
                    try {
                        Thread.sleep(50);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
