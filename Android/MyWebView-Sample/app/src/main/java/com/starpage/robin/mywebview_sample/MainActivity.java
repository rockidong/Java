package com.starpage.robin.mywebview_sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final String URL = "http://www.naver.com";
    final Activity activity = this;
    WebView webView;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //사용자가 뒤로가기 버튼을 누르면 액티비티를 종료하는 것이 아니라
        //이전 사이트를 다시 호출
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack() ){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);


        webView.setWebViewClient(new HelloWebView());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                activity.setProgress(newProgress * 100);
            }


        });




    }


    private class HelloWebView extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // HTML 문서 내 존재하는 새로운 URL을 웹뷰에서 로드한다.
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(activity,"Loading error" + description,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Toast.makeText(activity,"로딩이 완료되었습니다.",Toast.LENGTH_SHORT).show();
        }
    }
}
