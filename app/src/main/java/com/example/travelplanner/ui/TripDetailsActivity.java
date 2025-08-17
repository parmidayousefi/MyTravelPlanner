package com.example.travelplanner.ui;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.travelplanner.R;
import com.example.travelplanner.data.db.*;
import com.google.android.gms.maps.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.List;
public class TripDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private long tripId;
    private AppDatabase db;
    private GoogleMap map;
    private RecyclerView rvTodos, rvMedia;
    private TodoAdapter todoAdapter;
    private MediaAdapter mediaAdapter;
    private final ActivityResultLauncher<String> pickMedia = registerForActivityResult(
            new ActivityResultContracts.GetContent(), uri -> { if (uri != null) uploadToFirebase(uri); });
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        db = AppDatabase.get(this);
        tripId = getIntent().getLongExtra("tripId", -1);
        rvTodos = findViewById(R.id.rvTodos); rvTodos.setLayoutManager(new LinearLayoutManager(this));
        rvMedia = findViewById(R.id.rvMedia); rvMedia.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.map_container, mapFragment).commitNow();
        mapFragment.getMapAsync(this);
        rvMedia.setOnClickListener(v -> pickMedia.launch("*/*"));
        loadData();
    }
    private void loadData(){
        List<Todo> todos = db.dao().getTodos(tripId);
        todoAdapter = new TodoAdapter(todos); rvTodos.setAdapter(todoAdapter);
        List<Photo> photos = db.dao().getPhotos(tripId);
        mediaAdapter = new MediaAdapter(photos); rvMedia.setAdapter(mediaAdapter);
        List<Place> places = db.dao().getPlaces(tripId);
        if (map != null) for (Place p : places)
            map.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(new com.google.android.gms.maps.model.LatLng(p.lat, p.lng)).title(p.name));
    }
    @Override public void onMapReady(GoogleMap googleMap) { this.map = googleMap; loadData(); }
    private String getFileName(android.net.Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            String name = cursor.getString(nameIndex);
            cursor.close();
            return name;
        }
        return "media";
    }
    private void uploadToFirebase(Uri uri){
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Toast.makeText(this, "ابتدا وارد شوید", Toast.LENGTH_LONG).show(); return;
        }
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String path = "users/" + uid + "/trips/" + tripId + "/" + getFileName(uri);
        StorageReference ref = FirebaseStorage.getInstance().getReference(path);
        ref.putFile(uri).addOnSuccessListener(t -> {
            Photo p = new Photo(); p.tripId = tripId; p.storagePath = path; p.localUri = uri.toString();
            db.dao().insertPhoto(p);
            loadData();
            Toast.makeText(this, "آپلود شد", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(this, "خطا: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
    class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.VH> {
        List<Todo> items;
        TodoAdapter(List<Todo> items){ this.items = items; }
        @Override public VH onCreateViewHolder(ViewGroup parent, int viewType){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
            return new VH(v);
        }
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
    class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.VH>{
        List<Photo> items;
        MediaAdapter(List<Photo> items){ this.items = items; }
        @Override public VH onCreateViewHolder(ViewGroup p, int v){ return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_media, p, false)); }
        @Override public void onBindViewHolder(VH h, int pos){ Photo ph = items.get(pos); if (ph.localUri != null) Glide.with(h.img.getContext()).load(Uri.parse(ph.localUri)).into(h.img); }
        @Override public int getItemCount(){ return items==null?0:items.size(); }
        class VH extends RecyclerView.ViewHolder{ ImageView img; VH(View v){ super(v); img=v.findViewById(R.id.imgThumb); } }
    }
}
