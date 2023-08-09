package com.example.qltruyen_thonghmph25148.ADAPTER.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.qltruyen_thonghmph25148.FRAGMENT.FavoriteFragment;
import com.example.qltruyen_thonghmph25148.FRAGMENT.Home_Fragment;
import com.example.qltruyen_thonghmph25148.FRAGMENT.Profile_Fragment;
import com.example.qltruyen_thonghmph25148.MODEL.UserData;

public class ViewPager2Adapter extends FragmentStateAdapter {
    private UserData userData; // Biến để lưu trữ thông tin người dùng
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity,UserData userData) {
        super(fragmentActivity);
        this.userData = userData;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return Home_Fragment.newInstance(userData); // Truyền thông tin userData vào Home_Fragment
            case 1:
                return new FavoriteFragment(); // Truyền thông tin userData vào FavoriteFragment
            case 2:
                return Profile_Fragment.newInstance(userData); // Truyền thông tin userData vào Profile_Fragment
            default:
                return Home_Fragment.newInstance(userData); // Truyền thông tin userData vào mặc định fragment
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
