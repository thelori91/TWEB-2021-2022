package com.ium.fragmentexample;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ium.fragmentexample.databinding.FragmentLessonHistoryBinding;

import java.util.ArrayList;


public class LessonHistoryFragment extends Fragment {

    private FragmentLessonHistoryBinding binding;
    private MyViewModel myViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLessonHistoryBinding.inflate(inflater, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateSpinner();
    }

    public void updateSpinner() {
        //Update the ui
        ArrayList<String> arrayList = myViewModel.getUpcomingEventsOptions();
        ArrayList<String> arrayList2 = myViewModel.getPastEventsOptions();
        ArrayList<String> activeList = new ArrayList<>();
        ArrayList<String> doneList = new ArrayList<>();
        ArrayList<String> cancelledList = new ArrayList<>();
        if (arrayList != null && arrayList2 != null) {
            for (String lesson : arrayList) {
                activeList.add(lesson);
            }
            for (String lesson : arrayList2) {
                if (lesson.split(" ")[lesson.split(" ").length - 1].equals("Done")) {
                    doneList.add(lesson.split("\n")[0]);
                } else {
                    cancelledList.add(lesson.split("\n")[0]);
                }
            }
        }

        if (activeList != null && doneList != null && cancelledList != null) {

            ArrayAdapter<String> activeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, activeList);
            binding.activeHistoryListView.setAdapter(activeAdapter);

            ArrayAdapter<String> doneAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, doneList);
            binding.doneHistoryListView.setAdapter(doneAdapter);

            ArrayAdapter<String> cancelledAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, cancelledList);
            binding.cancelledHistoryListView.setAdapter(cancelledAdapter);

        }
    }
}
