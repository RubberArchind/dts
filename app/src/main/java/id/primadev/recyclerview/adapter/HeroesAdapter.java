package id.primadev.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.primadev.recyclerview.R;
import id.primadev.recyclerview.model.Heroes;

public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.MyViewHolder> {

    private List<Heroes> heroesList;

    public HeroesAdapter(List<Heroes> heroesList) {
        this.heroesList = heroesList;
    }

    @NonNull
    @Override
    public HeroesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dc_fragment,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroesAdapter.MyViewHolder myViewHolder, int i) {
        Picasso.get().load(heroesList.get(i).getImgURL()).into(myViewHolder.iv);
        myViewHolder.tv_name.setText(heroesList.get(i).getName());
        myViewHolder.tv_desc.setText(heroesList.get(i).getDesc());
    }

    @Override
    public int getItemCount() {
        return heroesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_desc;
        ImageView iv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.txtName);
            tv_desc = itemView.findViewById(R.id.txtPhone);
            iv = itemView.findViewById(R.id.imageContact);
        }
    }
}
