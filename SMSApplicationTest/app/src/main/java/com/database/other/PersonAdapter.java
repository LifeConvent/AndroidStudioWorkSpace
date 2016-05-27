package com.database.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.database.ob.PersonTemp;
import com.example.activity.login.R;

import java.util.List;

/**
 * Created by 彪 on 2016/4/20.
 */
public class PersonAdapter extends ArrayAdapter<PersonTemp> {

    private int resourceId;

    public PersonAdapter(Context context, int textViewResourceId, List<PersonTemp> onjects) {
        super(context, textViewResourceId, onjects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonTemp person = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.person_item, null);
        TextView text = (TextView) view.findViewById(R.id.my_info_tv);
        text.setText(person.getWords());
    return view;
}
}
