package id.primadev.recyclerview.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.primadev.recyclerview.HeroesRepository;
import id.primadev.recyclerview.R;
import id.primadev.recyclerview.database.entity.HeroesEntity;

public class HeroesAdapterDc extends RecyclerView.Adapter<HeroesAdapterDc.MyViewHolder> {

//    private List<Heroes> heroesList;
    private Context context;
    public List<HeroesEntity> heroesList;

    public void setHeroesList(List<HeroesEntity> heroesList) {
        this.heroesList = heroesList;
//        this.heroesEntities = heroesList;
        this.notifyDataSetChanged();
    }

    public HeroesAdapterDc(Context context) {

        this.context = context;
    }

    private onRvListener listener;

    public interface onRvListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);

        void onFavClick(View view,int position);
    }

    public void setListener(onRvListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public HeroesAdapterDc.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dc, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroesAdapterDc.MyViewHolder myViewHolder, int i) {
//        Picasso.get().load(heroesList.get(i).getImgURL()).into(myViewHolder.iv);
//        myViewHolder.tv_name.setText(heroesList.get(i).getName());
//        myViewHolder.tv_desc.setText(heroesList.get(i).getDesc());
        final HeroesEntity heroesEntity = heroesList.get(i);
        Picasso.get().load(heroesEntity.getImgUrl()).into(myViewHolder.iv);
        myViewHolder.tv_name.setText(heroesEntity.getHeroesName());
        myViewHolder.tv_desc.setText(heroesEntity.getDesc());
        if (heroesEntity.isFavorite()){
//            myViewHolder.tb.setChecked(true);
            myViewHolder.tb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_fav));
        }else{
//            myViewHolder.tb.setChecked(false);
            myViewHolder.tb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_fav_border));
        }

    }

    @Override
    public int getItemCount() {
        return (heroesList != null) ? heroesList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_desc;
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
