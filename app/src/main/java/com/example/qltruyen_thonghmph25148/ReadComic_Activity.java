package com.example.qltruyen_thonghmph25148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qltruyen_thonghmph25148.ADAPTER.viewpager2.TabPagerAdapter;
import com.example.qltruyen_thonghmph25148.MODEL.Comics;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReadComic_Activity extends AppCompatActivity {
    private Comics comic;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);
        webView=findViewById(R.id.webView);
        // Nhận thông tin comic từ Intent
        Intent intent = getIntent();
        comic = intent.getParcelableExtra("COMIC_EXTRA");
        if (comic != null) {
            Log.d("zzzzzzzzz", "onCreate: "+comic.getContent());
            webView.loadUrl(comic.getContent());

        } else {
            // Xử lý trường hợp comic là null
        }


    }
}