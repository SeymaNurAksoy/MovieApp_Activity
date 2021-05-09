package com.example.movieappactivity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> implements Filterable {

    private Context mcontext;
    private List<MovieModelClass> mdata;
    private List<MovieModelClass> mdataFilter;

    private CustomItemClickListener customItemClickListener;


    public Adaptery(Context mcontext, List<MovieModelClass> mdata,CustomItemClickListener customItemClickListener) {
        this.mcontext = mcontext;
        this.mdata = mdata;
        this.mdataFilter = mdata;
        this.customItemClickListener = customItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        v = inflater.inflate(R.layout.movie_item, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                final MyViewHolder myViewHolder = new MyViewHolder(v);
                customItemClickListener.onItemClick(mdataFilter.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.id.setText(mdataFilter.get(position).getId());
        holder.name.setText(mdataFilter.get(position).getName());

        //using  glide library to dissplay the image
        //we need  to add a link before the image string
        //https://image.tmdb.org/t/p/w500/...
        Glide.with(mcontext)
                .load("https://image.tmdb.org/t/p/w500/" + mdataFilter.get(position).getImg())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mdataFilter.size();
    }






 public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        ImageView img;
        Button btn;
        EditText filterText;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.textView_id);
            name = itemView.findViewById(R.id.textView2_name);
            img = itemView.findViewById(R.id.imageView);
            btn = itemView.findViewById(R.id.kaydet);

        }
    }



    @Override
        public Filter getFilter () {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String searchString = charSequence.toString();
                    if (searchString.isEmpty()) {
                        mdataFilter = mdata;
                    } else {
                        ArrayList<MovieModelClass> tempFilteredList = new ArrayList<>();
                        for (MovieModelClass station : mdata) {
                            // search for station name
                            if (station.getId().toLowerCase().contains(searchString)) {
                                tempFilteredList.add(station);
                            }
                        }
                        mdataFilter = tempFilteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mdataFilter;
                    return filterResults;

                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mdataFilter = (ArrayList<MovieModelClass>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

        }
    }


