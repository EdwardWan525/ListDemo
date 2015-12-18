package com.smartisan.edward.listdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.smartisan.edward.listdemo.R;

import java.util.List;
import java.util.Map;

/**
 * Created by smartisan on 15-12-17.
 */
public class ListDemoAdapter extends SimpleAdapter {
    private List<Map<String ,String>> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public ListDemoAdapter(Context context, List<Map<String,String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mData = data;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        //optimize adapter
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_item,null);
            viewHolder.titleTextView = (TextView)convertView.findViewById(R.id.list_item_title);
            viewHolder.subjectTextView = (TextView)convertView.findViewById(R.id.list_item_subject);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.titleTextView.setText(mData.get(position).get("title"));
        viewHolder.subjectTextView.setText(mData.get(position).get("subject"));

        return convertView;
    }
    public static final class ViewHolder{
        public TextView titleTextView;
        public TextView subjectTextView;

    }
}
