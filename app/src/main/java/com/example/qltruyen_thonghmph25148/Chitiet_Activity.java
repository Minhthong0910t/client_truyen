package com.example.qltruyen_thonghmph25148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qltruyen_thonghmph25148.ADAPTER.viewpager2.TabPagerAdapter;
import com.example.qltruyen_thonghmph25148.FRAGMENT.Comment_Fragment;
import com.example.qltruyen_thonghmph25148.FRAGMENT.ContentShort_Fragment;
import com.example.qltruyen_thonghmph25148.MODEL.Comics;
import com.example.qltruyen_thonghmph25148.MODEL.UserData;
import com.example.qltruyen_thonghmph25148.welcome.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Chitiet_Activity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter tabsPagerAdapter;
    private Comics comic;
    private UserData userData;
    List<UserData> userDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        TextView txtname = findViewById(R.id.name);
        ImageView avt = findViewById(R.id.avt);
        Button btnread = findViewById(R.id.btnread);
        comic = getIntent().getParcelableExtra("COMIC_EXTRA");
        userData = getIntent().getParcelableExtra("USER_DATA_EXTRA");
        if (comic != null) {

            // Hiển thị chi tiết thông tin Commic
            // Ví dụ: gán giá trị cho các TextView
            txtname.setText(comic.getName());

            Picasso.get().load(comic.getImg()).into(avt);
            Log.d("zzzzzzzz", " aa" + comic.getImg());

        }
        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Chitiet_Activity.this, ReadComic_Activity.class);
                    // Đính kèm thông tin comic vào Intent
                    intent.putExtra("COMIC_EXTRA", comic);
                    startActivity(intent);

            }
        });
        // Khởi tạo và cấu hình Adapter cho ViewPager
        tabsPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        ContentShort_Fragment fragmentND = ContentShort_Fragment.newInstance(comic); // Truyền Commic vào FragmentGT
        Comment_Fragment fragmentCM = Comment_Fragment.newInstance(comic, userData, userDataList);
        tabsPagerAdapter.addFragment(fragmentND, "Giới Thiệu");
        tabsPagerAdapter.addFragment(fragmentCM, "Đánh Giá");
        viewPager.setAdapter(tabsPagerAdapter);

        // Kết nối ViewPager với TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }
}