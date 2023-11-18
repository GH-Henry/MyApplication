package com.example.mystylist;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystylist.R;
import com.example.mystylist.closet_activity.ClosetActivity;
import com.example.mystylist.structures.Outfit;
import com.example.mystylist.structures.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class OutfitItemAdapter extends RecyclerView.Adapter<OutfitItemAdapter.ViewHolder> {

    // Data list
    private final ArrayList<Outfit> filteredOutfits;

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Item Layout Members
        private TextView text;
        private TextView outfit_text;
        private TextView color_text;
        private Button see_details_button;

        // ViewHolder constructor
        public ViewHolder(View view) {
            super(view);
            //text = view.findViewById(R.id.text);
            outfit_text = view.findViewById(R.id.outfit_text);
            color_text = view.findViewById(R.id.color_text);
            see_details_button = view.findViewById(R.id.see_details_button);
        }

        // Getters
        public TextView getText() { return text; }
        public TextView getOutfitText() { return outfit_text; }
        public TextView getColorText() { return color_text; }
        public Button getSeeDetailsButton() {
            return see_details_button;
        }
    }

    // Adapter constructor
    public OutfitItemAdapter(ArrayList<Outfit> filteredOutfits) {
        this.filteredOutfits = filteredOutfits;
    }

    // Item type
    @Override
    public int getItemViewType(int position) { return 0; }

    // Create view holders
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_outfit, parent, false));
        return holder;
    }

    // Bind data to item layouts
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Outfit outfit = filteredOutfits.get(position);

        TextView outfitText = holder.getOutfitText();
        TextView colorText = holder.getColorText();
        Button seeDetailsButton = holder.getSeeDetailsButton();

        outfitText.setText(outfit.getOutfitName());

        colorText.setText(String.valueOf(outfit.numberOfItems()));

        seeDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug", "success");
                ((OutfitActivity) unwrap(v.getContext())).startDisplayOutfitFragment(outfit);
            }
        });


    }

    // Return number of items in list.
    @Override
    public int getItemCount() { return filteredOutfits.size(); }

    private static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) context;
    }

}
