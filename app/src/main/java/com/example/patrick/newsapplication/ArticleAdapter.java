package com.example.patrick.newsapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Patrick on 6/21/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleAdapterItemHolder>{

    ArrayList<Article> articleData;
    ItemClickListener listener;

    //Various constructors for the Adapter
    public ArticleAdapter(){

    }

    public ArticleAdapter(ArrayList<Article> articleData,ItemClickListener listener){
        this.articleData=articleData;
        this.listener=listener;
    }

    //Interface to be used when clicking on different articles
    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    //Itemholder that holds the items to enable separate pieces
    public class ArticleAdapterItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Three fields that need to be displayed
        public final TextView articleTitleTextView;
        public final TextView articleAuthorTextView;
        public final TextView articleDateTextView;

        //Constructor for the initialization of the above pieces
        //A click listener is used to track the clicks
        public ArticleAdapterItemHolder(View itemView) {
            super(itemView);
            articleTitleTextView=(TextView) itemView.findViewById(R.id.news_title_text_view);
            articleAuthorTextView=(TextView) itemView.findViewById(R.id.news_author_text_view);
            articleDateTextView=(TextView) itemView.findViewById(R.id.news_date_text_view);
            itemView.setOnClickListener(this);
        }

        //Sets what goes into the views
        //Gets info from the article arraylist
        public void bind(int position){
            Article article=articleData.get(position);
            articleTitleTextView.setText(article.getArticleTitle());
            articleAuthorTextView.setText(article.getArticleAuthor());
            articleDateTextView.setText(article.getArticlePublishDate());

        }

        //Gets the location of the item that was clicked to return the correct info
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            listener.onItemClick(pos);
        }
    }

    //Creates viewholders for all the pieces
    //Allows for scrolling to occur
    @Override
    public ArticleAdapterItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context=viewGroup.getContext();
        int layoutId=R.layout.news_article_items;
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
        if(articleData==null){
            return 0;
        }
        else{
            return articleData.size();
        }
    }

    //Not Used
    //Was initially for saying if data was changed
    //And not creating unneeded viewholders
    public void setArticleData(ArrayList<Article> articles){
        articleData=new ArrayList<>(articles);
        notifyDataSetChanged();
    }
}
