package com.example.travelplanner.ui;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.travelplanner.R;
import com.example.travelplanner.data.db.*;
import java.util.ArrayList;
import java.util.List;
public class TodosFragment extends Fragment {
    private AppDatabase db;
    private RecyclerView rv;
    @Nullable @Override public View onCreateView(LayoutInflater inf, @Nullable ViewGroup c, @Nullable Bundle b){
        View v = inf.inflate(R.layout.fragment_todos, c, false);
        db = AppDatabase.get(getContext());
        rv = v.findViewById(R.id.rvTodosAll);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        load(); return v;
    }
    private void load(){
        List<Todo> all = new ArrayList<>(); // aggregate later if needed
        rv.setAdapter(new Adapter(all));
    }
    class Adapter extends RecyclerView.Adapter<Adapter.VH>{
        List<Todo> items;
        Adapter(List<Todo> items){ this.items=items; }
        @Override public VH onCreateViewHolder(ViewGroup p, int v){ return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_todo, p, false)); }
        @Override public void onBindViewHolder(VH h, int pos){
            Todo t = items.get(pos);
            h.title.setText(t.title);
            h.cb.setChecked(t.done);
            h.cb.setOnCheckedChangeListener((b,checked)-> db.dao().updateTodoDone(t.id, checked));
        }
        @Override public int getItemCount(){ return items==null?0:items.size(); }
        class VH extends RecyclerView.ViewHolder{
            TextView title; CheckBox cb;
            VH(View v){ super(v); title=v.findViewById(R.id.tvTitle); cb=v.findViewById(R.id.cbDone); }
        }
    }
}
