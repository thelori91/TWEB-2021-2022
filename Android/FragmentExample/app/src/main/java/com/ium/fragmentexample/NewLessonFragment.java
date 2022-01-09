package com.ium.fragmentexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ium.fragmentexample.databinding.FragmentNewLessonBinding;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class NewLessonFragment extends Fragment {


    FragmentNewLessonBinding binding;
    MyViewModel myViewModel;

    ArrayList<CourseWithTeachers> courseOptions = new ArrayList<>();
    ArrayList<TeacherWithLessons> teacherOptions = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewLessonBinding.inflate(inflater, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Request the all CoursesWithTeacher
        String url = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/getAllTeacherCourses-servlet";
        myViewModel.myHttpClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody, StandardCharsets.UTF_8);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject courseJson = jsonArray.getJSONObject(i);
                        String course = (courseJson.getString("courseName"));
                        JSONArray teachers = courseJson.getJSONArray("teachers");
                        ArrayList<String> teachersAL = new ArrayList<>();
                        for (int j = 0; j < teachers.length(); j++) {
                            JSONObject teacher = teachers.getJSONObject(j);
                            String teacherString = teacher.getString("teacherName") + " " + teacher.getString("teacherSurname") + " " + teacher.getString("teacherId");
                            teachersAL.add(teacherString);
                        }
                        courseOptions.add(new CourseWithTeachers(course, teachersAL));
                    }
                    ArrayList<String> courses = new ArrayList<>();
                    for (CourseWithTeachers courseWithTeachers : courseOptions) {
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
                Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        ///////////////////////////////////////////////////////////////////////////////

        //Request all TeacherWithLessons
        url = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/getAllLessons-servlet";
        myViewModel.myHttpClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody, StandardCharsets.UTF_8);
                try {
                    JSONArray teachers = new JSONArray(response);

                    for (int i = 0; i < teachers.length(); i++) {
                        JSONObject teacher = teachers.getJSONObject(i);
                        JSONArray lessonsOfTeacher = teacher.getJSONArray("lessons");
                        ArrayList<Lesson> lessonArrayList = new ArrayList<>();
                        for (int j = 0; j < lessonsOfTeacher.length(); j++) {
                            JSONObject lesson = lessonsOfTeacher.getJSONObject(j);
                            String user = (String) lesson.get("lessonWithUser");
                            long id = lesson.getLong("lessonId");
                            String course = lesson.getString("lessonCourse");
                            String day = lesson.getString("lessonDay");
                            String time = lesson.getString("lessonTime");
                            String state = lesson.getString("lessonState");
                            lessonArrayList.add(new Lesson(user, id, course, day, time, state));
                        }
                        String teacherName = teacher.getString("teacherName");
                        String teacherSurname = teacher.getString("teacherSurname");
                        String teacherId = teacher.getString("teacherId");
                        teacherOptions.add(new TeacherWithLessons(teacherName, teacherSurname, teacherId, lessonArrayList));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        ///////////////////////////////////////////////////////////////////////


        //When we select a course we update the teachers options
        binding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = (String) binding.courseSpinner.getSelectedItem();
                binding.teacherLayout.setVisibility(selectedCourse.equals("") ? View.INVISIBLE : View.VISIBLE);
                if (!selectedCourse.equals("")) {
                    //A valid option has been selected
                    for (CourseWithTeachers courseWithTeachers : courseOptions) {
                        if (selectedCourse.equals(courseWithTeachers.course)) {
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
        ///////////////////////////////////////////////////////


        //When we select a teacher we update the Day options
        binding.teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Look for teacher in teacherOptions
                String selectedTeacher = (String) binding.teacherSpinner.getSelectedItem();
                binding.dayLayout.setVisibility(selectedTeacher.equals("") ? View.INVISIBLE : View.VISIBLE);
                if (selectedTeacher.equals("")) return;
                ArrayList<String> days = new ArrayList<String>(Arrays.asList(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
                for (TeacherWithLessons teacherWithLessons : teacherOptions) {
                    if (teacherWithLessons.getOption().equals(selectedTeacher)) {
                        for (String day : days) {
                            ArrayList<String> times = new ArrayList<>(Arrays.asList(new String[]{"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"}));
                            for (Lesson lesson : teacherWithLessons.lessons) {
                                if (lesson.day.equals(day) && !lesson.state.equals("Cancelled")) {
                                    times.remove(lesson.time);
                                }
                            }
                            if (times.isEmpty()) {
                                days.remove(day);
                            }
                        }
                        break; //There is only one teacher, if we find it we stop
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, days);
                binding.daySpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ////////////////////////////////////////////////////////


        //When we select the day we show all possible time options
        binding.daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTeacher = (String) binding.teacherSpinner.getSelectedItem();
                String selectedDay = (String) binding.daySpinner.getSelectedItem();
                binding.timeLayout.setVisibility(selectedDay.equals("") ? View.INVISIBLE : View.VISIBLE);
                if (selectedDay.equals("")) return;
                ArrayList<String> times = new ArrayList<>(Arrays.asList(new String[]{"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"}));
                for (TeacherWithLessons teacherWithLessons : teacherOptions) {
                    if (teacherWithLessons.getOption().equals(selectedTeacher)) {
                        for (Lesson lesson : teacherWithLessons.lessons) {
                            if (lesson.day.equals(selectedDay) && !lesson.state.equals("Cancelled")) {
                                times.remove(lesson.time);
                            }
                        }
                        break; //There is only one teacher, if we find it we stop
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, times);
                binding.timeSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////

        //Actually make a reservation
        binding.bookLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(myViewModel.username.getValue().equals(""))
                {
                    Toast.makeText(getContext(), "Please Log in before attempting to book a lesson", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONArray oneItemArray = new JSONArray();
                JSONObject reservation = new JSONObject();
                String url1 = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/reservation-servlet";
                RequestParams params = new RequestParams();
                String[] teacherDissection = ((String) binding.teacherSpinner.getSelectedItem()).split(" ");
                try {
                    reservation.put("teacherId", teacherDissection[teacherDissection.length - 1]);
                    reservation.put("course", (String) binding.courseSpinner.getSelectedItem());
                    reservation.put("day", (String) binding.daySpinner.getSelectedItem());
                    reservation.put("time", (String) binding.timeSpinner.getSelectedItem());
                    oneItemArray.put(reservation);

                    params.add("reservations", oneItemArray.toString());
                    myViewModel.myHttpClient.post(url1, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody, StandardCharsets.UTF_8);
                            String[] responseDissected = response.split("\n");
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            if(responseDissected[0].equals("Success:"))
                            {
                                NavHostFragment.findNavController(NewLessonFragment.this).navigate(R.id.action_NewLesson_to_FirstFragment);
                            }
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getContext(), "Server is unavailable at the moment, please try again later", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////
    }

    public class CourseWithTeachers {
        public String course;
        public ArrayList<String> teachers;

        public CourseWithTeachers(String course, ArrayList<String> teachersAL) {
            this.course = course;
            this.teachers = teachersAL;
        }
    }

    public class TeacherWithLessons {
        public String teacherName;
        public String teacherSurname;
        public String teacherId;
        public ArrayList<Lesson> lessons;

        public String getOption() {
            return teacherName + " " + teacherSurname + " " + teacherId;
        }

        public TeacherWithLessons(String teacherName, String teacherSurname, String teacherId, ArrayList<Lesson> lessons) {
            this.teacherName = teacherName;
            this.teacherSurname = teacherSurname;
            this.teacherId = teacherId;
            this.lessons = lessons;
        }
    }

    public class Lesson {
        String username;
        long id;
        String course;
        String day;
        String time;
        String state;

        public Lesson(String username, long id, String course, String day, String time, String state) {
            this.username = username;
            this.id = id;
            this.course = course;
            this.day = day;
            this.time = time;
            this.state = state;
        }
    }
}

