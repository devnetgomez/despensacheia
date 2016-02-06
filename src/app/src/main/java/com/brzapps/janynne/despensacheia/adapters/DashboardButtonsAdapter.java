package com.brzapps.janynne.despensacheia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brzapps.janynne.despensacheia.DashboardButtons;
import com.brzapps.janynne.despensacheia.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by janynne on 05/02/16.
 */
public class DashboardButtonsAdapter extends BaseAdapter{

    private Context context;
    ArrayList<DashboardButtons> dashButtons;

    public DashboardButtonsAdapter(Context c, ArrayList<DashboardButtons> list )
    {
        this.context = c;
        this.dashButtons = list;

    }

    @Override
    public int getCount() {
        return this.dashButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return this.dashButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dashButtons.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = LayoutInflater.from(this.context).inflate(R.layout.dashboard_button, parent, false);
        ImageView img = (ImageView)v.findViewById(R.id.imvDashButton);
        TextView txt = (TextView)v.findViewById(R.id.txvDashButton);

        img.setImageResource(dashButtons.get(position).getResource());
        txt.setText(dashButtons.get(position).getText());

        return v;
    }
}
