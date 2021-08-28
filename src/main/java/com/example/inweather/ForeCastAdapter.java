package com.example.inweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

public class ForeCastAdapter extends RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder> {
    @NonNull
    @Override
    public ForeCastAdapter.ForeCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fore_cast_list_item,parent,false);
return new ForeCastViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull ForeCastAdapter.ForeCastViewHolder holder, int position) {
holder.fore_date_screen.setText(ForeCastTemp.getForecastlist().get(position).getFore_first_date());
   //   holder.fore_date.setText(ForeCastTemp.getForecastlist().get(position).getFore_date());
        holder.fore_description.setText(ForeCastTemp.getForecastlist().get(position).getFore_description());

        holder.fore_temp.setText(ForeCastTemp.getForecastlist().get(position).getFore_temp());
        holder.foretempmax.setText(ForeCastTemp.getForecastlist().get(position).getFore_temp_max());

        if(ForeCastTemp.getForecastlist().get(position).getFore_description().equalsIgnoreCase("clear sky")) {
    holder.fore_pic.setImageResource(R.drawable.day_clear);
}

        if(ForeCastTemp.getForecastlist().get(position).getFore_description().equalsIgnoreCase("clear sky")) {
            holder.fore_pic.setImageResource(R.drawable.day_clear);
        }
       else if(ForeCastTemp.getForecastlist().get(position).getFore_description().equalsIgnoreCase("overcast clouds")) {
            holder.fore_pic.setImageResource(R.drawable.overcast);
        }

        else if(ForeCastTemp.getForecastlist().get(position).getFore_description().equalsIgnoreCase("light rain")) {
            holder.fore_pic.setImageResource(R.drawable.rain);
        }

        else if(ForeCastTemp.getForecastlist().get(position).getFore_description().equalsIgnoreCase("few clouds")) {
            holder.fore_pic.setImageResource(R.drawable.day_partial_cloud);

        }

        else if(ForeCastTemp.getForecastlist().get(position).getFore_description().equalsIgnoreCase("broken clouds")) {
            holder.fore_pic.setImageResource(R.drawable.day_partial_cloud);
        }



        else if(ForeCastTemp.getForecastlist().get(position).getFore_description().equalsIgnoreCase("moderate rain")) {
            holder.fore_pic.setImageResource(R.drawable.rain);
        }

        else if(ForeCastTemp.getForecastlist().get(position).getFore_description().equalsIgnoreCase("scattered clouds")) {
            holder.fore_pic.setImageResource(R.drawable.day_partial_cloud);
        }

    }

    @Override
    public int getItemCount() {

        return ForeCastTemp.getForecastlist().size();

    }

    class ForeCastViewHolder extends RecyclerView.ViewHolder{
TextView fore_date_screen;
        TextView fore_description;

        TextView fore_date;
        TextView fore_temp;
        TextView foretempmax;

ImageView fore_pic;
        public ForeCastViewHolder(@NonNull View itemView) {
            super(itemView);
           fore_date_screen= itemView.findViewById(R.id.fore_date_screen);
           fore_pic = itemView.findViewById(R.id.fore_pic);
            fore_date= itemView.findViewById(R.id.fore_date);
            fore_description= itemView.findViewById(R.id.fore_description);
            fore_temp= itemView.findViewById(R.id.fore_temp);
            foretempmax= itemView.findViewById(R.id.fore_tempmax);



        }
    }
}
