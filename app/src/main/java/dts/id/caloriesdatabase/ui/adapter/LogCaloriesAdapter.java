package dts.id.caloriesdatabase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dts.id.caloriesdatabase.R;
import dts.id.caloriesdatabase.db.entity.LogCaloriesEntity;

public class LogCaloriesAdapter extends RecyclerView.Adapter<LogCaloriesAdapter.MyViewHolder> {

    private final LayoutInflater mInflater;
    private List<LogCaloriesEntity> mLogCaloriesEntities;

    public LogCaloriesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void SetListLogCalories(List<LogCaloriesEntity> logCaloriesEntities){
        mLogCaloriesEntities = logCaloriesEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView =mInflater.inflate(R.layout.item_log_calories,parent,false);
        return  new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Todo Melengkapi onBindViewHolder
        LogCaloriesEntity logCaloriesEntity = mLogCaloriesEntities.get(position);
        holder.tvWaktuMakan.setText(logCaloriesEntity.getWaktuMakan());
        holder.tvFoodName.setText(logCaloriesEntity.getMakanan());
        holder.tvCalory.setText(logCaloriesEntity.getKaloriMakanan());
    }

    @Override
    public int getItemCount() {
        return mLogCaloriesEntities==null? 0:mLogCaloriesEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvWaktuMakan, tvFoodName, tvCalory;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWaktuMakan = itemView.findViewById(R.id.tvWaktuMakan);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvCalory= itemView.findViewById(R.id.tvCalory);
        }
    }
}
