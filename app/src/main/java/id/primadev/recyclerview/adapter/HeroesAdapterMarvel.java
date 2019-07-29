package id.primadev.recyclerview.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.primadev.recyclerview.R;
import id.primadev.recyclerview.database.entity.HeroesEntity;
import id.primadev.recyclerview.model.Heroes;

import static id.primadev.recyclerview.Constant.USERNAME_KEY;

public class HeroesAdapterMarvel extends RecyclerView.Adapter<HeroesAdapterMarvel.MyViewHolder> {

    public List<HeroesEntity> heroesList;
    private Context context;

    public void setHeroesList(List<HeroesEntity> heroesList) {
        this.heroesList = heroesList;
        this.notifyDataSetChanged();
    }

    public HeroesAdapterMarvel(Context context) {
        this.context = context;
    }

    private HeroesAdapterMarvel.onRvListener listener;

    public interface onRvListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);

        void onFavClick(View view,int position);
    }

    public void setListener(HeroesAdapterMarvel.onRvListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public HeroesAdapterMarvel.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_marvel,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroesAdapterMarvel.MyViewHolder myViewHolder, int i) {
        final HeroesEntity heroesEntity = heroesList.get(i);
        Picasso.get().load(heroesEntity.getImgUrl()).into(myViewHolder.iv);
        myViewHolder.tv_name.setText(heroesEntity.getHeroesName());
        myViewHolder.tv_desc.setText(heroesEntity.getDesc());
        if (heroesEntity.isFavorite()){
            myViewHolder.tb.setChecked(true);
            myViewHolder.tb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_fav));
        }else{
            myViewHolder.tb.setChecked(false);
            myViewHolder.tb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_fav_border));
        }
    }

    @Override
    public int getItemCount() {
        return (heroesList != null) ? heroesList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_desc;
        ImageView iv;
        ToggleButton tb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tb = itemView.findViewById(R.id.togFav);
            tv_name = itemView.findViewById(R.id.txtName);
            tv_desc = itemView.findViewById(R.id.txtPhone);
            iv = itemView.findViewById(R.id.imageContact);

            tb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onFavClick(view,getAdapterPosition());
                }
            });

        }
    }
}