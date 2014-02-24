package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapp.task.ImageLoader;

import java.util.List;

/**
 * @author Almazko
 */
public class PostsAdapter extends BaseAdapter {
    private final List<Post> posts;
    private Context context;
    private LayoutInflater inflater;
//    private static NewsHolder newsHolder;

    static class NewsHolder {
        TextView title;
        TextView content;
        ImageView preview;
        TextView previewLoader;
        View parent;
    }


    class Read {
        Read(Context context, Post post) {
        }
    }


    public PostsAdapter(List<Post> posts, Context context) {

        this.posts = posts;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        NewsHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.news_item, null);
            Log.v("PostsAdapter", "Created news_item");
            holder = new NewsHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.content = (TextView) view.findViewById(R.id.content);
            holder.preview = (ImageView) view.findViewById(R.id.preview);
            holder.previewLoader = (TextView) view.findViewById(R.id.previewLoader);
            holder.parent = view.findViewById(R.id.post);
            view.setTag(holder);
        } else {
            holder = (NewsHolder) view.getTag();
        }


        Post post = posts.get(i);
        holder.title.setText(post.title);
        holder.content.setText(Html.fromHtml(post.description));


        holder.parent.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, NewsActivity.class);
                intent.putExtra(Post.ID, i);
                context.startActivity(intent);
            }
        });


        if (post.imageLink != null) {
            holder.preview.setImageResource(R.drawable.ic_launcher);
            ImageLoader task = new ImageLoader(holder.preview);
            task.execute(post.imageLink);
        }  else {
            holder.previewLoader.setVisibility(View.GONE);
            holder.preview.setVisibility(View.GONE);
        }

        return view;
    }
}
