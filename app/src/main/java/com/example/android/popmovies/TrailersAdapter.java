package com.example.android.popmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toda on 2017/12/20.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.NumberViewHolder> {

    private static final String TAG = TrailersAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;
    private ArrayList<HashMap<String, String>> mTrailersData;
    private HashMap<String, String> currentData;

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(String mv_url);
    }

    /**
     * Constructor for GreenAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     *
     * @param listener Listener for list item clicks
     */
    //FIX: Removing the hardcoded size parameter and making it dependent on the size of the list passed.
    public TrailersAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
        viewHolderCount = 0;
        mTrailersData = new ArrayList<>();
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailers_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolderCount++;
//        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
//                + viewHolderCount);
        return viewHolder;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        currentData = mTrailersData.get(position);
        //FIX: Change key from trailer to "name"
        String trailerName = currentData.get("name");
        holder.listItemNumberView.setText(trailerName);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        return mTrailersData.size();
    }

    /**
     * Cache of the children views for a list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;

        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = itemView.findViewById(R.id.recyclerview_trailer_name);
            itemView.setOnClickListener(this);
        }

        /**
         * Called whenever a user clicks on an item in the list.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            currentData = mTrailersData.get(clickedPosition);
            String mv_url = currentData.get("trailersUrl");
            mOnClickListener.onListItemClick(mv_url);
        }
    }

    public void setTrailersData(ArrayList<HashMap<String, String>> mTrailerData) {
        this.mTrailersData = mTrailerData;
        notifyDataSetChanged();
    }
}