package com.nanodegree.android.guardiannewsapp;

/**
 * Created by jdifuntorum on 7/9/17.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Custom Adapter with ViewHolder supporting the use of the RecyclerView
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    /**
     * Log Tag for console msgs
     */
    public static final String LOG_TAG = NewsAdapter.class.getSimpleName();
    private List<NewsList> mNewsListItem;
    private Context mContext;
    private String mWebURL;

    // Pass in the contact array into the constructor
    public NewsAdapter(Context context, List<NewsList> newsItems) {
        mContext = context;
        mNewsListItem = newsItems;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Involves inflating a layout from XML and returning the holder
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View listView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new ViewHolder instance
        ViewHolder viewHolder = new ViewHolder(mContext, listView);
        return viewHolder;
    }

    // Involves populating data into the item through the holder
    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        NewsList newsItem = mNewsListItem.get(position);

        // Set item views based on views and data model
        TextView titleTextView = viewHolder.title;
        TextView sectionTextView = viewHolder.section;
        TextView publicationDateTimeTextView = viewHolder.publicationDateTime;

        titleTextView.setText(newsItem.getTitle());
        sectionTextView.setText(newsItem.getSection());
        publicationDateTimeTextView.setText(convertDateTimeFormat(newsItem.getPublished_Date()));
    }

    // Convert json DateTime to Date + Time as seperate values
    public String convertDateTimeFormat(String input) {
        input = input.substring(0, input.length() - 1);
        String oldFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String newFormat = "MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(oldFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(newFormat);
        Date date = null;
        String output = "";
        try {
            date = inputFormat.parse(input);
            output = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "DateTime parse exception: " + e);
        }
        return output;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mNewsListItem.size();
    }

    // Adds new items to mNewsListItem and refreshes the layout
    public void addAll(List<NewsList> newsItemList) {
        mNewsListItem.clear();
        mNewsListItem.addAll(newsItemList);
        notifyDataSetChanged();
    }

    // Clears mNewsListItem
    public void clearAll() {
        mNewsListItem.clear();
        notifyDataSetChanged();
    }

    // Provide a direct reference to each of the views within a list_item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // The ViewHolder holds a variable for every View that will be used
        public TextView title;
        public TextView section;
        public TextView publicationDateTime;
        private Context context;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context, View listItemView) {
            // Stores the listItemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(listItemView);

            // Store the context
            this.context = context;

            // Attach a click listener to the entire row view
            listItemView.setOnClickListener(this);

            title = (TextView) listItemView.findViewById(R.id.news_title);
            section = (TextView) listItemView.findViewById(R.id.news_section);
            publicationDateTime = (TextView) listItemView.findViewById(R.id.news_publication_date);
        }

        // set up onClick event on article being being clicked to open up link
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            NewsList newsItem = mNewsListItem.get(position);

            // Get the Url from the current NewsList
            mWebURL = newsItem.getURL_Link();

            // Convert the String URL into a URI object (to pass into the Intent constructor)
            Uri newsURI = Uri.parse(mWebURL);
            // Create new intent to view the article's URL
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsURI);
            // Start the intent
            context.startActivity(websiteIntent);
        }
    }


}