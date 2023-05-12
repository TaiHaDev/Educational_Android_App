package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.ga_23s1_comp2100_6442.adapter.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.example.ga_23s1_comp2100_6442.utilities.CourseUtil;
import com.example.ga_23s1_comp2100_6442.utilities.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesPage extends AppCompatActivity {
    CourseAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses_page);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MyCoursesPage.this, LoginPage.class));
            finish();
        }
        adapter = new CourseAdapter();
        adapter.setData(new ArrayList<>());
        FirebaseUtil.getMyCourses(adapter);
        recyclerView = findViewById(R.id.courses_list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FirebaseUtil.getMyCourses(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}