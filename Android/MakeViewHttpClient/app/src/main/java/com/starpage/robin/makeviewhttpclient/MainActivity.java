package com.starpage.robin.makeviewhttpclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;

public class MainActivity extends AppCompatActivity {

    Bitmap result = null;
    ImageView image;
    InputStream is;

    public static final String SAMPLEURL = "http://developer.android.com/assets/"+
            "images/home/honeycomb-android.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button StartDownload = (Button)findViewById(R.id.startdownload);
        image = (ImageView)findViewById(R.id.imageview);
        StartDownload.setOnClickListener(StartDownloadOnClickListener);


    }

    Button.OnClickListener StartDownloadOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            mThread a = new mThread();
            a.start();
        }
    };

    private class mThread extends Thread{
        @Override
        public void run() {
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .build();

            HttpGet httpget = new HttpGet(SAMPLEURL);
            httpget.setHeader("User-Agent","Mozilla/5.0 (X11; U; Linux x86_64;" +
                    "en-US; rv:1.9.2.13) Gecko/20101206 Firefox/3.6.13");

            //단순히 HttpClientContext 객체를 생성하여 HTTP 버전 1.1로 명시
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("http.protocol.version", HttpVersion.HTTP_1_1);
            CloseableHttpClient client = HttpClients.custom()
                    .setDefaultRequestConfig(config)
                    .build();

            try{
                HttpResponse response = client.execute(httpget,context);
                StatusLine status = response.getStatusLine();

                //정상적으로 리소스를 받았는 지 확인한다.
                if(status.getStatusCode() == HttpStatus.SC_OK )
                {
                    InputStream is = response.getEntity().getContent();
                    result = BitmapFactory.decodeStream(is);
                    is.close();
                }
                image.post(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(result);
                    }
                });

            }catch(ClientProtocolException e){e.printStackTrace();}
            catch (Exception e )
            { e.printStackTrace();}
            finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }
    }
}
