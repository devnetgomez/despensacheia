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
import com.brzapps.janynne.despensacheia.sqlite.model.List;

import java.util.ArrayList;

/**
 * Created by janynne on 06/02/16.
 */
public class ItemsAdapter  extends BaseAdapter {

        ArrayList<List> myList = new ArrayList();
        LayoutInflater inflater;
        Context context;


        public ItemsAdapter(Context context, ArrayList myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public List getItem(int position) {
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
                convertView = inflater.inflate(R.layout.itemslist_row, parent, false);
                mViewHolder = new ListItemsViewer(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ListItemsViewer) convertView.getTag();
            }

            List currentListData = getItem(position);

            mViewHolder.txvTitle.setText(currentListData.getName());

            mViewHolder.tvDesc.setText( String.valueOf( currentListData.getMonth()) + " - "+
                                        String.valueOf( currentListData.getYear()));

            return convertView;
        }

        private class ListItemsViewer {

            TextView txvTitle, tvDesc;
            ImageView ivIcon;

            public ListItemsViewer(View item) {
                txvTitle = (TextView) item.findViewById(R.id.tvTitle);
                tvDesc = (TextView) item.findViewById(R.id.tvDesc);
                ivIcon = (ImageView) item.findViewById(R.id.ivIcon);
            }
        }
    }


