package com.github.almazko.hubreader;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.almazko.hubreader.activity.PostActivity;
import com.github.almazko.hubreader.data.Updater;
import com.github.almazko.hubreader.task.ImageLoader;

import java.text.DateFormat;
import java.util.List;

/**
 * @author Almazko
 */
public class PostsAdapter extends BaseAdapter {
    private final List<Post> posts;
    private Context context;
    private LayoutInflater inflater;
    private Updater updater;
    private final DateFormat dateFormat;
    private final DateFormat timeFormat;

    static class PostHolder {
        TextView title;
        TextView content;
        ImageView preview;
        TextView previewLoader;
        TextView date;
        View parent;
    }

    public PostsAdapter(List<Post> posts, Context context) {

        this.posts = posts;
        this.context = context;
        inflater = LayoutInflater.from(context);
        updater = new Updater(context.getContentResolver());

        dateFormat = android.text.format.DateFormat.getDateFormat(context);
        timeFormat = android.text.format.DateFormat.getTimeFormat(context);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        PostHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.post_item, null);
            Log.v("PostsAdapter", "Created post_item");
            holder = new PostHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.content = (TextView) view.findViewById(R.id.content);
            holder.preview = (ImageView) view.findViewById(R.id.preview);
            holder.previewLoader = (TextView) view.findViewById(R.id.previewLoader);
            holder.date = (TextView) view.findViewById(R.id.post_date);
            holder.parent = view.findViewById(R.id.post);
            view.setTag(holder);
        } else {
            holder = (PostHolder) view.getTag();
        }


        Post post = posts.get(i);
        holder.title.setText(post.title);
        holder.content.setText(Html.fromHtml(post.description));
        holder.date.setText(timeFormat.format(post.publishDate) + " " + dateFormat.format(post.publishDate));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openPost(i);
            }
        });


        if (post.previewLink != null) {
            if (post.preview == null) {
                holder.previewLoader.setVisibility(View.VISIBLE);
                holder.preview.setVisibility(View.INVISIBLE);
                ImageLoader task = new ImageLoader(updater, holder.preview, holder.previewLoader);
                task.execute(post);
            } else {
                holder.preview.setVisibility(View.INVISIBLE);
                holder.preview.setImageBitmap(post.preview);
                holder.preview.setVisibility(View.VISIBLE);
                holder.previewLoader.setVisibility(View.GONE);
            }

        } else {
            holder.previewLoader.setVisibility(View.GONE);
            holder.preview.setVisibility(View.GONE);
        }

        return view;
    }

    void openPost(int i) {
        Post post = posts.get(i);

        Intent intent = new Intent();
        intent.setClass(context, PostActivity.class);
        intent.putExtra(Post.ID, post.id);
        context.startActivity(intent);
    }
}
