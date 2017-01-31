package com.msapps.touchdetector;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrawerView.PointersListener {

    private DrawerView mTouchView;
    private CoordAdapter mCoordAdapter;
    private ListView mTouchCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTouchView = (DrawerView) findViewById(R.id.touch_view);
        mTouchCountTextView = (ListView) findViewById(R.id.touch_count);
        mTouchView.setPointerListener(this);
        mCoordAdapter = new CoordAdapter(this);
        onTouch(new ArrayList<MotionEvent.PointerCoords>());
        mTouchCountTextView.setAdapter(mCoordAdapter);
    }

    @Override
    public void onTouch(List<MotionEvent.PointerCoords> pointers) {
        mCoordAdapter.setItems(pointers);
    }

    private static class CoordAdapter extends BaseAdapter {

        private Context mContext;
        private List<MotionEvent.PointerCoords> mCoords = new ArrayList<>();

        public CoordAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mCoords.size();
        }

        @Override
        public MotionEvent.PointerCoords getItem(int i) {
            return mCoords.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.inflater_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.touch = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MotionEvent.PointerCoords coords = getItem(i);
            viewHolder.touch.setText(String.format("X: %s, Y: %s, Press: %s",
                    coords.x, coords.y, coords.pressure));
            viewHolder.touch.setTextColor(DrawerView.COLORS[i]);
            return convertView;
        }

        public void setItems(List<MotionEvent.PointerCoords> pointers) {
            mCoords = pointers;
            notifyDataSetChanged();
        }

        private static class ViewHolder {
            TextView touch;
        }
    }
}
