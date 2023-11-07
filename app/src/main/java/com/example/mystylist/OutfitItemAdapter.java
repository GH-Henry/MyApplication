package com.example.mystylist;

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

        // ViewHolder constructor
        public ViewHolder(View view) {
            super(view);
            //text = view.findViewById(R.id.text);
            outfit_text = view.findViewById(R.id.outfit_text);
            color_text = view.findViewById(R.id.color_text);
        }

        // Getters
        public TextView getText() { return text; }
        public TextView getOutfitText() { return outfit_text; }
        public TextView getColorText() { return color_text; }
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

        outfitText.setText(outfit.getOutfitName());

        colorText.setText(String.valueOf(outfit.numberOfItems()));
        // Retrieve data to put in item
        //Outfit itemData = filteredOutfits.get(position);
//        Outfit outfit = filteredOutfits.get(position);
//
//        // Retrieve views from holder
//        TextView text = holder.getText();
//
//        TextView outfitText = holder.getOutfitText();
//
//        outfitText.setText(outfit.getItems()[0].type.toString());
//
////        // Retrieve data to put in item
////        //Outfit itemData = filteredOutfits.get(position);
////        Outfit outfit = filteredOutfits.get(position);
//
//        // Fill layout with data
//        //text.setText(itemData.toString());
//
//        for (Outfit oneOutfit : filteredOutfits) {
//            for (Item item : outfit.getItems()) {
//                String textForOutfit = item.type.toString();
//                outfitText.setText(outfit.getItems()[0].type.toString());
//                outfit_text.setText(textForOutfit);
//            }
//        }

    }

    // Return number of items in list.
    @Override
    public int getItemCount() { return filteredOutfits.size(); }

}
