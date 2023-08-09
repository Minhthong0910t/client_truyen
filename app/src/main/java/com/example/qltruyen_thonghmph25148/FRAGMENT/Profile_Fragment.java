package com.example.qltruyen_thonghmph25148.FRAGMENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qltruyen_thonghmph25148.ADAPTER.viewpager2.SlideAdapterHome;
import com.example.qltruyen_thonghmph25148.MODEL.UserData;
import com.example.qltruyen_thonghmph25148.MainActivity;
import com.example.qltruyen_thonghmph25148.R;
import com.example.qltruyen_thonghmph25148.welcome.LoginActivity;
import com.example.qltruyen_thonghmph25148.welcome.SignUpActivity;
import com.example.qltruyen_thonghmph25148.welcome.WelcomeActivity;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button btn_login;
    private Button btn_sigup;
    private UserData userData;
    LinearLayout ln_logout;
    private TextView txtname,txtid;
    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    public static Profile_Fragment newInstance(UserData userData) {
        Profile_Fragment fragment = new Profile_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_profile_, container, false);
        if (getArguments() != null) {
            userData = getArguments().getParcelable("userData");
            if (userData != null) {
                String username = userData.getUserName();
                String userId = userData.get_id();
                // Tiếp tục xử lý dữ liệu username và userId theo ý muốn

            }
        }
        anhXa();
        return view;
    }


    private void anhXa(){
        //btn
        btn_login=view.findViewById(R.id.btn_login);

        btn_sigup=view.findViewById(R.id.btn_signup);
        txtname = view.findViewById(R.id.nameText);
        ln_logout=view.findViewById(R.id.ln_logOut);

        ln_logout.setVisibility(View.GONE);
        // Kiểm tra nếu userData không null thì hiển thị chào mừng và ẩn nút đăng nhập và đăng ký
        if (userData != null && userData.getUserName() != null) {
            Log.d("zzzzz", "anhXa: "+userData.getUserName());
            txtname.setText("Xin chào " + userData.getUserName());
            ln_logout.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);
            btn_sigup.setVisibility(View.GONE);
        }

        ln_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        btn_sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });
    }
}