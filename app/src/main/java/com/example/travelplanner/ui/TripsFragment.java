package com.example.travelplanner.ui;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import com.example.travelplanner.R;
import com.example.travelplanner.data.db.*;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;
public class TripsFragment extends Fragment {
    private AppDatabase db;
    private RecyclerView rv;
    @Nullable @Override public View onCreateView(LayoutInflater inf, @Nullable ViewGroup c, @Nullable Bundle b){
        View v = inf.inflate(R.layout.fragment_trips, c, false);
        db = AppDatabase.get(getContext());
        rv = v.findViewById(R.id.rvTrips);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        load(); return v;
    }
    @Override public void onResume(){ super.onResume(); load(); }
    private void load(){
        String uid = FirebaseAuth.getInstance().getCurrentUser()!=null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "guest";
        List<Trip> trips = db.dao().getTrips(uid);
        rv.setAdapter(new TripAdapter(trips));
    }
    class TripAdapter extends RecyclerView.Adapter<TripAdapter.VH>{ 
        private final List<Trip> items;
        TripAdapter(List<Trip> items){ this.items = items; }
        @Override public VH onCreateViewHolder(ViewGroup p, int v){ View view = LayoutInflater.from(p.getContext()).inflate(R.layout.item_trip, p, false); return new VH(view); }
        @Override public void onBindViewHolder(VH h, int pos){
            Trip t = items.get(pos);
            h.title.setText(t.title);
            h.dates.setText(t.startDate + " — " + t.endDate);
            h.itemView.setOnClickListener(v -> {
                Intent i = new Intent(getContext(), TripDetailsActivity.class);
                i.putExtra("tripId", t.id);
                startActivity(i);
            });
        }
        @Override public int getItemCount(){ return items==null?0:items.size(); }
        class VH extends RecyclerView.ViewHolder{
            TextView title, dates;
            VH(View v){ super(v); title=v.findViewById(R.id.tvTripTitle); dates=v.findViewById(R.id.tvTripDates); }
        }
    }
}
