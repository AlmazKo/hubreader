package com.github.hubreader.data;

/**
 * @author Almazko
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.github.hubreader.AndroidSaxFeedParser;
import com.github.hubreader.Post;
import com.github.hubreader.PostMapper;
import com.github.hubreader.task.RssReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PostProvider extends ContentProvider {

    private DatabaseHelper dbHelper;

    private static final int SINGLE_POST = 1;
    private static final int ALL_POSTS = 2;
    private static final int NEW_POSTS = 3;


    // authority is the symbolic name of your provider
    // To avoid conflicts with other providers, you should use
    // Internet domain ownership (in reverse) as the basis of your provider authority.
    private static final String AUTHORITY = "com.github.hubreader.data.contentprovider";

    // create content URIs from the authority by appending path to database table
    public static final Uri URI_POSTS = Uri.parse("content://" + AUTHORITY + "/posts");
    public static final Uri URI_NEW_POSTS = Uri.parse("content://" + AUTHORITY + "/posts/new/");
    public static final String PARAM_ACTUAL = "ACTUAL";

    // a content URI pattern matches content URIs using wildcard characters:
    // *: Matches a string of any valid characters of any length.
    // #: Matches a string of numeric characters of any length.
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "posts/#", SINGLE_POST);
        uriMatcher.addURI(AUTHORITY, "posts", ALL_POSTS);
        uriMatcher.addURI(AUTHORITY, "posts/new/", NEW_POSTS);
    }

    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new DatabaseHelper(getContext());
        return false;
    }

    //Return the MIME type corresponding to a content URI
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case ALL_POSTS:
                return "vnd.android.cursor.dir/vnd.com.example.myapp.data.posts";
            case SINGLE_POST:
                return "vnd.android.cursor.item/vnd.com.example.myapp.data.posts";
            case NEW_POSTS:
                return "vnd.android.cursor.item/vnd.com.example.myapp.data.posts.new";
            default:
                throw new IllegalArgumentException("Unsupported URI1: " + uri);
        }
    }

    // The insert() method adds a new row to the appropriate table, using the values
    // in the ContentValues argument. If a column name is not in the ContentValues argument,
    // you may want to provide a default value for it either in your provider code or in
    // your database schema.
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        switch (uriMatcher.match(uri)) {
//            case ALL_POSTS:
//                //do nothing
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported URI2: " + uri);
//        }
        long id = db.insert(PostTable.TABLE_NAME, null, values);
//        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(URI_POSTS + "/" + id);
    }

    // The query() method must return a Cursor object, or if it fails,
    // throw an Exception. If you are using an SQLite database as your data storage,
    // you can simply return the Cursor returned by one of the query() methods of the
    // SQLiteDatabase class. If the query does not match any rows, you should return a
    // Cursor instance whose getCount() method returns 0. You should return null only
    // if an internal error occurred during the query process.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PostTable.TABLE_NAME);

        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case ALL_POSTS:
                cursor = queryBuilder.query(db, projection, null, selectionArgs, null, null, sortOrder);
                if (selection == null && cursor.getCount() == 0) {
                    loadData();
                }
                break;
            case NEW_POSTS:
                loadData();

                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

                break;
            case SINGLE_POST:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(PostTable._ID + "=" + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI3: " + uri);
        }

        return cursor;
    }

    private int loadData() {
        AndroidSaxFeedParser parser = new AndroidSaxFeedParser("http://habrahabr.ru/rss/hubs/");

        List<Post> posts = parser.parse();

        for (Post post : posts) {
            try {
                String src = RssReader.findSrcPreview(post.description);
                if (src != null) {
                    post.previewLink = new URL(src);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            insert(URI_POSTS, PostMapper.toValues(post));
        }

        return posts.size();
    }

    // The delete() method deletes rows based on the seletion or if an id is
    // provided then it deleted a single row. The methods returns the numbers
    // of records delete from the database. If you choose not to delete the data
    // physically then just update a flag here.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int deleteCount = 0;

        switch (uriMatcher.match(uri)) {
            case ALL_POSTS:
                deleteCount = db.delete(PostTable.TABLE_NAME, selection, selectionArgs);
                break;
            case SINGLE_POST:
//                String id = uri.getPathSegments().get(1);
//                selection = PostTable._ID + "=" + id
//                        + (!TextUtils.isEmpty(selection) ?
//                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI4: " + uri);
        }
//        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    // The update method() is same as delete() which updates multiple rows
    // based on the selection or a single row if the row id is provided. The
    // update method returns the number of updated rows.
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_POSTS:
                //do nothing
                break;
            case SINGLE_POST:
                String id = uri.getPathSegments().get(1);
                selection = PostTable._ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI5: " + uri);
        }
        int updateCount = db.update(PostTable.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

}