package com.example.patrick.newsapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.patrick.newsapplication.database_classes.Contract.TABLE_ARTICLES.*;

/**
 * Created by Patrick on 6/21/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleAdapterItemHolder>{

    private Cursor cursor;
    private ItemClickListener listener;
    private Context context;

    //Various constructors for the Adapter
    private ArticleAdapter(){

    }

    public ArticleAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor=cursor;
        this.listener=listener;
    }

    //Interface to be used when clicking on different articles
    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    //Creates viewholders for all the pieces
    //Allows for scrolling to occur
    @Override
    public ArticleAdapterItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.context=viewGroup.getContext();
        int layoutId= R.layout.news_article_items;

        LayoutInflater inflater=LayoutInflater.from(context);
        boolean shouldAttachToParent=false;

        View view=inflater.inflate(layoutId,viewGroup,shouldAttachToParent);

        return new ArticleAdapterItemHolder(view);
    }

    //Calls the bind on the appropiate piece
    @Override
    public void onBindViewHolder(ArticleAdapterItemHolder articleHolder, int position) {
        articleHolder.bind(position);
    }

    //Number of items to display
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/

    //Itemholder that holds the items to enable separate pieces
    public class ArticleAdapterItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Five fields that need to be displayed
        private final TextView articleTitleTextView;
        private final TextView articleAuthorTextView;
        private final TextView articleDateTextView;
        private final TextView articleDescriptionTextView;
        private final ImageView articleImageImageView;

        //Constructor for the initialization of the above pieces
        //A click listener is used to track the clicks
        public ArticleAdapterItemHolder(View itemView) {
            super(itemView);
            articleTitleTextView=(TextView) itemView.findViewById(R.id.news_title_text_view);
            articleAuthorTextView=(TextView) itemView.findViewById(R.id.news_author_text_view);
            articleDateTextView=(TextView) itemView.findViewById(R.id.news_date_text_view);
            articleDescriptionTextView=(TextView) itemView.findViewById(R.id.news_description_text_view);
            articleImageImageView=(ImageView) itemView.findViewById(R.id.image_image_view);
            itemView.setOnClickListener(this);
        }

        //Sets what goes into the views
        //Gets info from the cursor, which gets info from the database
        public void bind(int position){
            cursor.moveToPosition(position);
            articleTitleTextView.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE)));
            articleAuthorTextView.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_AUTHOR)));
            articleDateTextView.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PUBLISHED)));
            String imgUrl=cursor.getString(cursor.getColumnIndex(COLUMN_NAME_IMG_URL));
            if(imgUrl != null){
                Picasso.with(context).load(imgUrl).into(articleImageImageView);
            }
        }

        //Gets the location of the item that was clicked to return the correct info
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}
