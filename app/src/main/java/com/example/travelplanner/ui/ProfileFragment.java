package com.example.travelplanner.ui;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.travelplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class ProfileFragment extends Fragment {
    @Nullable @Override public View onCreateView(LayoutInflater inf, @Nullable ViewGroup c, @Nullable Bundle b){
        View v = inf.inflate(R.layout.fragment_profile, c, false);
        ImageView img = v.findViewById(R.id.imgAvatar);
        TextView name = v.findViewById(R.id.tvName);
        TextView email = v.findViewById(R.id.tvEmail);
        Button logout = v.findViewById(R.id.btnLogout);
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u != null){
            name.setText(u.getDisplayName());
            email.setText(u.getEmail());
            if (u.getPhotoUrl()!=null) Glide.with(this).load(u.getPhotoUrl()).into(img);
        }
        logout.setOnClickListener(x -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
        return v;
    }
}
