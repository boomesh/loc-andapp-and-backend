package com.boojoo.boomesh.listofcities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sumeshjohn on 2014-12-31.
 */
public class ListOfCitiesAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mCities;

    public ListOfCitiesAdapter(List<String> cities, Context context) {
        mCities = cities;
        mContext = context;

    }

    @Override
    public int getCount() {
        return (mCities == null) ? 0 : mCities.size();
    }

    @Override
    public Object getItem(int position) {
        return (mCities == null) ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (mCities == null) ? Long.MIN_VALUE : (mCities.get(position) == null) ? Long.MIN_VALUE : mCities.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new ViewHolder();
            holder.mCityTextView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mCityTextView.setText((String) getItem(position));

        return convertView;
    }

    private static class ViewHolder {
        public TextView mCityTextView;
    }
}
