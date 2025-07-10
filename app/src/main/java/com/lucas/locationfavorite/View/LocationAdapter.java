package com.lucas.locationfavorite.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lucas.locationfavorite.DB.DBLocation;
import com.lucas.locationfavorite.Model.LocationItem;
import com.lucas.locationfavorite.R;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<LocationItem> locationList;
    private List<LocationItem> filteredList;
    private Context context;

    public LocationAdapter(List<LocationItem> locationList, Context context) {
        this.locationList = locationList;
        this.filteredList = new ArrayList<>(locationList);
        this.context = context;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        if (position < 0 || position >= filteredList.size()) return;
        LocationItem item = filteredList.get(position);

        holder.tvName.setText(item.getName());
        holder.tvCoords.setText(item.getLatitude() + ", " + item.getLongitude());

        holder.btnDelete.setOnClickListener(v -> {
            DBLocation dbLocation = new DBLocation(context);
            dbLocation.deleteLocation(item.getId());

            for (int i = 0; i < locationList.size(); i++) {
                if (locationList.get(i).getId() == item.getId()) {
                    locationList.remove(i);
                    break;
                }
            }
            filteredList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, filteredList.size());

            Toast.makeText(context, "Location removed", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCoords;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCoords = itemView.findViewById(R.id.tv_coords);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                List<LocationItem> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered.addAll(locationList);
                } else {
                    for (LocationItem item : locationList) {
                        if (item.getName().toLowerCase().contains(query)) {
                            filtered.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<LocationItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public void addLocation(LocationItem item) {
        locationList.add(item);
        filteredList.add(item);
        notifyItemInserted(filteredList.size() - 1);
    }
}
