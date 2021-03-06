package com.brzapps.janynne.despensacheia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brzapps.janynne.despensacheia.R;
import com.brzapps.janynne.despensacheia.sqlite.helper.Categories;
import com.brzapps.janynne.despensacheia.sqlite.helper.DatabaseHelper;
import com.brzapps.janynne.despensacheia.sqlite.model.Category;
import com.brzapps.janynne.despensacheia.sqlite.model.Item;

import java.util.ArrayList;

/**
 * Created by janynne on 20/01/16.
 */

public class ListItemsAdapter extends BaseAdapter {

    ArrayList<Item> myList = new ArrayList();
    LayoutInflater inflater;
    Context context;


    public ListItemsAdapter(Context context, ArrayList myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Item getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  myList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemsViewer mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_items, parent, false);
            mViewHolder = new ListItemsViewer(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ListItemsViewer) convertView.getTag();
        }

        Item currentListData = getItem(position);

        mViewHolder.txvTitle.setText(currentListData.getName());

        mViewHolder.tvLetter.setText(currentListData.getName().substring(0,1));

        /*if(currentListData.getIcon() > 0) {
            mViewHolder.ivIcon.setImageResource(currentListData.getIcon());
        }*/

        long categoryId = currentListData.getCategoryId();

        if(categoryId > 0) {

            DatabaseHelper db = new DatabaseHelper(this.context);

            Categories categories = new Categories(db.getReadableDatabase());

            Category category = categories.get(categoryId);

            mViewHolder.tvDesc.setText(category.getName());

        }

        return convertView;
    }

    private class ListItemsViewer {

        TextView txvTitle, tvDesc, tvLetter;
        ImageView ivIcon;

        public ListItemsViewer(View item) {
            txvTitle = (TextView) item.findViewById(R.id.tvTitle);
            tvDesc = (TextView) item.findViewById(R.id.tvDesc);
            tvLetter = (TextView) item.findViewById(R.id.tvLetter);
        }
    }
}

