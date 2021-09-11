package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;
import android.widget.TextView;

public class AppDocument extends AppCompatActivity {

    WebView document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_document);
        document = findViewById(R.id.webView);

        String htmlAsString = getString(R.string.html);
        //Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);

        document.loadDataWithBaseURL(null,htmlAsString,"text/html", "utf-8", null);
    }
}