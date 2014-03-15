package com.github.almazko.hubreader;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;

/**
 * @author Almazko
 */
public class PostsCursorAdapter extends CursorAdapter {

    private final LayoutInflater inflater;
    private final DateFormat dateFormat;

    static class RowHolder {
        TextView id;
        TextView link;
        TextView title;
        TextView date;
    }

    public PostsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        inflater = LayoutInflater.from(context);
        dateFormat = android.text.format.DateFormat.getDateFormat(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view = inflater.inflate(R.layout.data_row, null);

        RowHolder holder = new RowHolder();
        holder.id = (TextView) view.findViewById(R.id.data_id);
        holder.link = (TextView) view.findViewById(R.id.data_link);
        holder.date = (TextView) view.findViewById(R.id.data_date);
        holder.title = (TextView) view.findViewById(R.id.data_title);

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        RowHolder holder = (RowHolder) view.getTag();

        Post post = PostMapper.toPost(cursor);

        holder.id.setText(String.valueOf(post.id));

        if (post.getPreviewLink() != null) {
            holder.link.setText(post.getPreviewLink().toString());
        }  else {
            holder.link.setText("null");
        }

        holder.title.setText(post.title);
        holder.date.setText(dateFormat.format(post.publishDate));
    }
}
