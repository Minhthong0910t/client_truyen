package com.example.qltruyen_thonghmph25148.FRAGMENT;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qltruyen_thonghmph25148.MODEL.Comics;
import com.example.qltruyen_thonghmph25148.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentShort_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentShort_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Comics comic;
    View view;


    public ContentShort_Fragment() {
        // Required empty public constructor
    }


    public static ContentShort_Fragment newInstance(Comics comic) {
        ContentShort_Fragment fragment = new ContentShort_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("comic", (Parcelable) comic); // Đặt Commic vào arguments
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comic = getArguments().getParcelable("comic"); // Nhận Commic từ arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_content_short_, container, false);
        TextView txtDescription = view.findViewById(R.id.txtDescription);
        TextView txtAuther = view.findViewById(R.id.txtAuther);
        TextView txtYear = view.findViewById(R.id.txtYear);




        if (comic != null) {
            // Hiển thị thông tin Commic lên TextView
            txtAuther.setText(" Tác giả:  " + comic.getTacgia());
            txtYear.setText(" Năm xuất bản:  " + comic.getDate() );
            txtDescription.setText(" Nội dung ngắn: "+ comic.getDisc());
        }
        // Inflate the layout for this fragment
        return view;
    }
}