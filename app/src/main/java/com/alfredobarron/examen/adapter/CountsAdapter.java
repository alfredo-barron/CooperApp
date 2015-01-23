package com.alfredobarron.examen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alfredobarron.examen.R;
import com.alfredobarron.examen.model.Counts;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.LayoutInflater.from;

public class CountsAdapter extends BaseAdapter {

    private final Context context;
    private List<Counts> counts;

    public CountsAdapter(Context context, List<Counts> counts){
        this.context = context;
        this.counts = counts;
    }



    public void setCounts(List<Counts> counts) {
        this.counts = counts;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.counts.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return counts.size();
    }

    @Override
    public Counts getItem(int position) {
        return counts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Counts count = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(context).inflate(R.layout.count_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.titleTextView.setText(count.getName());
        return convertView;
    }

    static class ViewHolder{

        @InjectView(R.id.title)
        TextView titleTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
