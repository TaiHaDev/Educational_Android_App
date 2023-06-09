package com.example.ga_23s1_comp2100_6442;

import static com.example.ga_23s1_comp2100_6442.utilities.UploadingDataJob.readingDataFromCSV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ga_23s1_comp2100_6442.adapter.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.example.ga_23s1_comp2100_6442.model.DatabaseUser;
import com.example.ga_23s1_comp2100_6442.storage.AVLTree;
import com.example.ga_23s1_comp2100_6442.utilities.Constant;

import com.example.ga_23s1_comp2100_6442.utilities.CourseUtil;
import com.example.ga_23s1_comp2100_6442.utilities.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePage extends AppCompatActivity{
    private CourseAdapter adapter;
    public static AVLTree historySearchTree;
    private SharedPreferences sharedPref;
    private String bigFilter;
    private String descriptFilter;
    private Button filterButton;

    private TextView searchHint;
    private LinearLayout filterContainer;
    private RadioGroup radioGroup;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private long limit = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
//        loadRecentlySearch();
        adapter = new CourseAdapter(sharedPref);
        filterButton = findViewById(R.id.filter_button);
        filterContainer = findViewById(R.id.filter_container);
        radioGroup = findViewById(R.id.radioGroup);
        searchHint = findViewById(R.id.search_hint);
        bigFilter = "";
        descriptFilter = null;

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(HomePage.this, LoginPage.class));
            finish();
        }

        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNewData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(HomePage.this, LoginPage.class));
            finish();
        }
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterContainer.getVisibility() == View.VISIBLE) {
                    filterContainer.setVisibility(View.GONE);
                } else {
                    filterContainer.setVisibility(View.VISIBLE);
                }
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            bigFilter = intent.getStringExtra(Constant.BIG_FILTER_KEY);
            System.out.println(bigFilter);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onCreateOptionsMenu(toolbar.getMenu());
        bottomNavigationHandler();
        fetchAndDisplayCourses();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedButton = findViewById(checkedId);
                descriptFilter = selectedButton.getText().toString();
                System.out.println(descriptFilter);
            }
        });
    }

    private void bottomNavigationHandler() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.coursesMenu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.forumsMenu) {
                    Intent intent = new Intent(getApplicationContext(), ForumPage.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.profileMenu) {
                    Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.coursesMenu) {
                    Intent intent = new Intent(getApplicationContext(), BigfilterPage.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    private void fetchAndDisplayCourses() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        Query fbc = fb.collection(Constant.COURSE_COLLECTION);
        System.out.println(bigFilter);
        if (bigFilter != null) {
            fbc = fbc.whereEqualTo("bigFilter", bigFilter);
        }
        fbc.limit(limit).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Course> fireBaseData = new ArrayList<>();
                CourseUtil.SetCoursesFromDocumentSnapshots(queryDocumentSnapshots, fireBaseData);
                adapter.setData(fireBaseData);
                RecyclerView recyclerView = findViewById(R.id.courses_list);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void fetchNewData() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        Query fbc = fb.collection(Constant.COURSE_COLLECTION);
        System.out.println(bigFilter);
        if (bigFilter != null) {
            fbc = fbc.whereEqualTo("bigFilter", bigFilter);
        }
        fbc.limit(limit += 30).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Course> fireBaseData = new ArrayList<>();
                CourseUtil.SetCoursesFromDocumentSnapshots(queryDocumentSnapshots, fireBaseData);
                Collections.reverse(fireBaseData);
                adapter.setData(fireBaseData);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search our Courses");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //FirebaseUtil.simpleQueryFireStore(query, adapter);
                FirebaseUtil.QueryFireStore(query, adapter, bigFilter, descriptFilter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        if (bigFilter == null) {
            searchItem.expandActionView();
            searchHint.setVisibility(View.VISIBLE);
        }

        //show search hint(parser rule) after click the button and hide after searching
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                searchHint.setVisibility(View.VISIBLE);
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                searchHint.setVisibility(View.GONE);
                return true;
            }
        });

        // messaging icon behaviour
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.start_chatting) {
            Intent intent = new Intent(this, ChatListingPage.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * load the search history tree from shared preferences
     *
     * @author: Tai Ha
     */

    public void loadRecentlySearch() {
        if (historySearchTree != null) return;
        Gson gson = new Gson();
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String jsonString = sharedPref.getString("historySearchTree", null);
        historySearchTree = gson.fromJson(jsonString, AVLTree.class);
        if (historySearchTree == null) historySearchTree = new AVLTree();
        historySearchTree.inOrderTraversal();
    }


}
