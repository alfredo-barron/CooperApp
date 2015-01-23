package com.alfredobarron.examen.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alfredobarron.examen.DetailCount;
import com.alfredobarron.examen.R;
import com.alfredobarron.examen.model.Counts;
import com.alfredobarron.examen.model.Users;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.LayoutInflater.from;

public class UsersAdapter extends BaseAdapter {

    private final Context context;
    private List<Users> counts;
    private long id;

    public UsersAdapter(Context context, List<Users> counts, long id){
        this.context = context;
        this.counts = counts;
        this.id = id;
    }

    public void setUsers(List<Users> counts) {
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
    public Users getItem(int position) {
        return counts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Users count = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(context).inflate(R.layout.person_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.nameTextView.setText(count.getName());
        String lot = Integer.toString(count.getLot());
        viewHolder.lotTextView.setText("$ "+lot);
        viewHolder.lotMustTextView.setText(" ");
        int people = (int) Select.from(Users.class).where(Condition.prop("count").eq(id)).count();
        Counts c = Counts.findById(Counts.class, id);
        int lot_count = c.getLot();
        int lot_person = lot_count/people;
        if (lot_person > count.getLot()) {
            int cant = lot_person - count.getLot();
            viewHolder.lotMustTextView.setText(Html.fromHtml("<font color=\"#E81809\"> - $ "+cant+ "</font>"));
        } else if(lot_person < count.getLot()) {
            int cant = count.getLot() - lot_person;
            viewHolder.lotMustTextView.setText(Html.fromHtml("<font color=\"#FB7E01\"> + $ "+cant+ "</font>"));
        } else {
            viewHolder.lotMustTextView.setText(" ");
        }

        return convertView;
    }

    static class ViewHolder{

        @InjectView(R.id.name_people)
        TextView nameTextView;
        @InjectView(R.id.lot_people)
        TextView lotTextView;
        @InjectView(R.id.lot_must)
        TextView lotMustTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
