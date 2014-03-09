package com.github.hubreader;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.github.hubreader.data.PostTable;

import java.text.DateFormat;

import static android.provider.BaseColumns._ID;
import static com.github.hubreader.data.PostTable.LINK;
import static com.github.hubreader.data.PostTable.TITLE;

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

        String link = cursor.getString(cursor.getColumnIndex(LINK));
        String postId = link.substring(25, link.length() - 1);
        holder.id.setText(cursor.getString(cursor.getColumnIndex(_ID)));
        holder.link.setText(postId);
        holder.title.setText(cursor.getString(cursor.getColumnIndex(TITLE)));
       holder.date.setText(cursor.getString(cursor.getColumnIndex(PostTable.DATE_CREATE)));

    }
}
