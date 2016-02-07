package com.brzapps.janynne.despensacheia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.sqlite.helper.Categories;
import com.brzapps.janynne.despensacheia.sqlite.model.Category;


import java.util.ArrayList;

/**
 * Created by janynne on 31/01/16.
 */
public class ListCategoriesAdapter  extends BaseAdapter {

    ArrayList<Category> myList = new ArrayList();
    LayoutInflater inflater;
    Context context;
    Categories categories;


    public ListCategoriesAdapter(Context context, ArrayList myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Category getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return myList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListCategoriesViewer mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_categories, parent, false);
            mViewHolder = new ListCategoriesViewer(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ListCategoriesViewer) convertView.getTag();
        }

        Category currentListData = getItem(position);

        mViewHolder.txvCategoryName.setText(currentListData.getName());
        mViewHolder.tvLetter.setText(currentListData.getName().substring(0, 1));


        return convertView;
    }

    private class ListCategoriesViewer {

        TextView txvCategoryName, tvLetter,txvCategoryDate;

        public ListCategoriesViewer(View category) {
            txvCategoryName = (TextView) category.findViewById(R.id.txvCategoryName);
            tvLetter = (TextView) category.findViewById(R.id.tvLetter);
            txvCategoryDate = (TextView) category.findViewById(R.id.txvCategoryDate);
        }
    }
}

