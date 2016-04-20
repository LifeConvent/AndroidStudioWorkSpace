package com.database.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.activity.courseList.CourseNum;
import com.example.activity.login.R;

import java.util.List;

/**
 * Created by 彪 on 2016/4/19.
 */
public class CourseAdapter extends ArrayAdapter<CourseNum>{

        private int resourceId;

        public CourseAdapter(Context context, int textViewResourceId, List<CourseNum> onjects) {
            super(context, textViewResourceId, onjects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CourseNum course = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            TextView descript = (TextView) view.findViewById(R.id.descript);
            TextView courseNum = (TextView) view.findViewById(R.id.course_name);
            descript.setText(course.getDescript());
            courseNum.setText(course.getCourseNum());
            return view;
        }

}
