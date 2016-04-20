package com.database.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.database.ob.Menu;
import com.example.activity.login.R;

import java.util.List;

/**
 * Created by 彪 on 2016/4/18.
 */

public class MenuAdapter extends ArrayAdapter<Menu> {

    private int resourceId;

    public MenuAdapter(Context context, int textViewResourceId, List<Menu> onjects) {
        super(context, textViewResourceId, onjects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Menu menu = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView muenImage = (ImageView) view.findViewById(R.id.menu_image);
        TextView menuName = (TextView) view.findViewById(R.id.menu_name);
        muenImage.setImageResource(menu.getImageId());
        menuName.setText(menu.getName());
        return view;
    }
}
