package com.ubb.catrinel.feddlike;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ubb.catrinel.feddlike.Model.Article;

import java.util.List;

/**
 * Created by User on 11/5/2017.
 */

public class ArticleListAdapter extends ArrayAdapter<Article> {

    private Activity activity;
    private List<Article> articleList;
    private int resource;
    private static LayoutInflater inflater = null;


    public ArticleListAdapter(Activity activity, int resource, List<Article> articleList) {
        super(activity, resource, articleList);
        try{
            this.activity = activity;
            this.articleList = articleList;
            this.resource = resource;

            inflater = activity.getLayoutInflater();
        }
        catch (Exception  e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {
        public TextView display_title;
        public TextView display_author;
        public TextView separator;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                view = inflater.inflate(resource, null);
                holder = new ViewHolder();

                holder.display_title = view.findViewById(R.id.articletitle);
                holder.separator = view.findViewById(R.id.separator);
                holder.display_author = view.findViewById(R.id.articleauthor);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.display_title.setText(articleList.get(position).getTitle());
            holder.separator.setText(" by ");
            holder.display_author.setText(articleList.get(position).getAuthor());

        } catch (Exception e) {
        }

        return view;
    }

    public void updateListView(List<Article> articles){
        this.articleList = articles;
        this.notifyDataSetChanged();
    }
}
