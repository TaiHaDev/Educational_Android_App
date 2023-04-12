package com.example.ga_23s1_comp2100_6442.ultilities;

import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.ga_23s1_comp2100_6442.CourseAdapter;
import com.example.ga_23s1_comp2100_6442.model.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FirebaseUtil is a utilities class containing static functions to be used to fetch data
 * from Firebase and perform specific actions
 * @author taiha
 */
public class FirebaseUtil {
    /**
     * Set an ImageView instance to display an image from firebase storage given the url.
     * @param requestManager from Glide library
     * @param imageView - the imageview to set the displaying image
     * @param url - the url of the image from storage
     */
    public static void downloadAndSetImageFromStorage(RequestManager requestManager, ImageView imageView, String url) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(url);

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                requestManager.load(uri).into(imageView);
            }
        });
    }

    /**
     * Simple firebase query to search by search term. After getting the result from firebase,
     * set the adapter's data to show the results.
     * @param term the search term
     * @param adapter recylerview's adapter
     */
    public static void simpleQueryFireStore(String term, CourseAdapter adapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constant.COURSE_COLLECTION)
                .whereArrayContains("searchTerm", term)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Course> courses = queryDocumentSnapshots.toObjects(Course.class);
                        System.out.println(":" + courses);
                        adapter.setData(courses);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}
