package com.ium.fragmentexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ium.fragmentexample.databinding.FragmentFirstBinding;
import com.ium.fragmentexample.databinding.FragmentNewLessonBinding;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NewLessonFragment extends Fragment {


    FragmentNewLessonBinding binding;
    MyViewModel myViewModel;

    ArrayList<CourseWithTeachers> courseOptions = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewLessonBinding.inflate(inflater, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Request the all TeacherWithCourses and allTeachersWithLessons
        String url = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/getAllTeacherCourses-servlet";
        myViewModel.myHttpClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody, StandardCharsets.UTF_8);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i < jsonArray.length(); i++) {
                        JSONObject courseJson = jsonArray.getJSONObject(i);
                        String course = (courseJson.getString("courseName"));
                        JSONArray teachers = courseJson.getJSONArray("teachers");
                        ArrayList<String> teachersAL = new ArrayList<>();
                        for(int j = 0; j < teachers.length(); j++)
                        {
                            JSONObject teacher = teachers.getJSONObject(j);
                            String teacherString = teacher.getString("teacherName") + " " + teacher.getString("teacherSurname") + " " + teacher.getString("teacherId");
                            teachersAL.add(teacherString);
                        }
                        courseOptions.add(new CourseWithTeachers(course, teachersAL));
                    }
                    ArrayList<String> courses = new ArrayList<>();
                    for(CourseWithTeachers courseWithTeachers : courseOptions)
                    {
                        courses.add(courseWithTeachers.course);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, courses);
                    binding.courseSpinner.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Fallito", "onFailureTriggered");
            }
        });

        binding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = (String)binding.courseSpinner.getSelectedItem();
                binding.teacherLayout.setVisibility(selectedCourse.equals("") ? View.INVISIBLE : View.VISIBLE);
                if(!selectedCourse.equals(""))
                {
                    //A valid option has been selected
                    for(CourseWithTeachers courseWithTeachers : courseOptions)
                    {
                        if(selectedCourse.equals(courseWithTeachers.course))
                        {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, courseWithTeachers.teachers);
                            binding.teacherSpinner.setAdapter(arrayAdapter);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public class CourseWithTeachers
    {
        public String course;
        public ArrayList<String> teachers;

        public CourseWithTeachers(String course, ArrayList<String> teachersAL){
            this.course = course;
            this.teachers = teachersAL;
        }
    }

}