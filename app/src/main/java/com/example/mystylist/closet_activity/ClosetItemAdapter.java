package com.example.mystylist.closet_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystylist.R;
import com.example.mystylist.structures.Closet;
import com.example.mystylist.structures.Item;

public class ClosetItemAdapter extends RecyclerView.Adapter<ClosetItemAdapter.ViewHolder>{
    private Closet closet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView type_image;
        private final TextView type_text;
        private final TextView color_text;
        private final CardView color_preview;
        private final CheckBox check_box;

        public ViewHolder(View view) {
            super(view);
            type_image = view.findViewById(R.id.type_image);
            type_text = view.findViewById(R.id.type_text);
            color_text = view.findViewById(R.id.color_text);
            color_preview = view.findViewById(R.id.color_preview);
            check_box = view.findViewById(R.id.check_box);
        }

        public ImageView getTypeImage(){
            return type_image;
        }
        public TextView getTypeText() {
            return type_text;
        }
        public TextView getColorText() {
            return color_text;
        }
        public CardView getColorPreview() {return color_preview;}
        public CheckBox getCheckBox() { return check_box; }

    }

    public ClosetItemAdapter(Closet closet) {
        this.closet = closet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_closet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView type_image = holder.getTypeImage();
        TextView type_text = holder.getTypeText();
        TextView color_text = holder.getColorText();
        CardView color_preview = holder.getColorPreview();
        CheckBox check_box = holder.getCheckBox();

        Item item = closet.getItemAt(position);

        type_image.setImageResource(item.drawable_id);
        type_text.setText(item.type.text);
        color_text.setText(item.color.asText);
        color_preview.setCardBackgroundColor(item.color.asInt);
    }

    @Override
    public int getItemCount() {
        return closet.getItemCount();
    }
}
