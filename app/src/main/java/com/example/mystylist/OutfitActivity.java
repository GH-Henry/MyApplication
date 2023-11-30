package com.example.mystylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;
import com.example.mystylist.enums.ETag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class OutfitActivity extends AppCompatActivity {

    public ConstraintLayout layout;
    ImageButton backButton;

    Button btnApplySelections;
    CheckBox casualCheckbox;
    CheckBox winterCheckbox;

    //weather checkboxes
    CheckBox weather_hot_checkbox;
    CheckBox weather_cold_checkbox;
    CheckBox weather_fair_checkbox;
    CheckBox weather_rainy_checkbox;

    //gender checkboxes
    CheckBox gender_neutral_checkbox;
    CheckBox gender_masculine_checkbox;
    CheckBox gender_feminine_checkbox;

    //style checkboxes
    CheckBox style_casual_checkbox;
    CheckBox style_smartcasual_checkbox;
    CheckBox style_businesscasual_checkbox;
    CheckBox style_businessprofessional_checkbox;
    CheckBox style_semiformal_checkbox;
    CheckBox style_formal_checkbox;

    //season checkboxes
    CheckBox season_spring_checkbox;
    CheckBox season_winter_checkbox;
    CheckBox season_fall_checkbox;
    CheckBox season_summer_checkbox;

    public Button filterButton;

    private RecyclerView recyclerView;
    private OutfitItemAdapter adapter;

    private ArrayList<Outfit> outfits;

    public static List<Outfit> givenOutfits = null;

    public static List<ETag> selectedItems = new LinkedList<ETag>();
    public static List<Item> filterItems = null;
    public static Long filterTags = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);

        backButton = findViewById(R.id.back_button);
        filterButton = findViewById(R.id.filter_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemPopup();
                //View dialogView = getLayoutInflater().inflate(R.layout.popup_checkboxes, null);
                //Button btnApplySelections = dialogView.findViewById(R.id.btnApplySelections2);

//                weather_hot_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxHot);
//                weather_cold_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxCold);
//                weather_fair_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxFair);
//                weather_rainy_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxRainy);

//                LayoutInflater inflater = LayoutInflater.from(this);
//                View popupView = inflater.inflate(R.layout.popup_checkboxes, null);
//                int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
//                int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
//                PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
//                Button btnApplySelections = popupView.findViewById(R.id.btnApplySelections2);
//
//                weather_hot_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxHot);
//                weather_cold_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxCold);
//                weather_fair_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFair);
//                weather_rainy_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxRainy);

//                gender_neutral_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxNeutral);
//                gender_masculine_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxMasculine);
//                gender_feminine_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxFeminine);
//
//                style_casual_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxCasual);
//                style_smartcasual_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxSmartCasual);
//                style_businesscasual_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxBusinessCasual);
//                style_businessprofessional_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxBusinessProfessional);
//                style_semiformal_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxSemiFormal);
//                style_formal_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxFormal);
//
//                season_spring_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxSpring);
//                season_winter_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxWinter);
//                season_fall_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxFall);
//                season_summer_checkbox = (CheckBox)dialogView.findViewById(R.id.checkboxSummer);

//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OutfitActivity.this);
//                dialogBuilder.setView(dialogView);
//                final AlertDialog dialog = dialogBuilder.create();

//                weather_hot_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.WEATHER_HOT);
//                        }
//                        else {
//                            selectedItems.remove(ETag.WEATHER_HOT);
//                        }
//                    }
//                });
//
//                weather_cold_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.WEATHER_COLD);
//                        }
//                        else {
//                            selectedItems.remove(ETag.WEATHER_COLD);
//                        }
//                    }
//                });
//
//                weather_fair_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.WEATHER_FAIR);
//                        }
//                        else {
//                            selectedItems.remove(ETag.WEATHER_FAIR);
//                        }
//                    }
//                });
//
//                weather_rainy_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.WEATHER_RAINY);
//                        }
//                        else {
//                            selectedItems.remove(ETag.WEATHER_RAINY);
//                        }
//                    }
//                });
//
//                gender_neutral_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.GENDER_NEUTRAL);
//                        }
//                        else {
//                            selectedItems.remove(ETag.GENDER_NEUTRAL);
//                        }
//                    }
//                });
//
//                gender_masculine_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.GENDER_MASCULINE);
//                        }
//                        else {
//                            selectedItems.remove(ETag.GENDER_MASCULINE);
//                        }
//                    }
//                });
//
//                gender_feminine_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.GENDER_FEMININE);
//                        }
//                        else {
//                            selectedItems.remove(ETag.GENDER_FEMININE);
//                        }
//                    }
//                });
//
//                season_fall_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.SEASON_FALL);
//                        }
//                        else {
//                            selectedItems.remove(ETag.SEASON_FALL);
//                        }
//                    }
//                });
//
//                season_spring_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.SEASON_SPRING);
//                        }
//                        else {
//                            selectedItems.remove(ETag.SEASON_SPRING);
//                        }
//                    }
//                });
//
//                season_summer_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.SEASON_SUMMER);
//                        }
//                        else {
//                            selectedItems.remove(ETag.SEASON_SUMMER);
//                        }
//                    }
//                });
//
//                season_winter_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.SEASON_WINTER);
//                        }
//                        else {
//                            selectedItems.remove(ETag.SEASON_WINTER);
//                        }
//                    }
//                });
//
//                style_casual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.STYLE_CASUAL);
//                        }
//                        else {
//                            selectedItems.remove(ETag.STYLE_CASUAL);
//                        }
//                    }
//                });
//
//                style_smartcasual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.STYLE_SMART_CASUAL);
//                        }
//                        else {
//                            selectedItems.remove(ETag.STYLE_SMART_CASUAL);
//                        }
//                    }
//                });
//
//                style_businesscasual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.STYLE_BUSINESS_CASUAL);
//                        }
//                        else {
//                            selectedItems.remove(ETag.STYLE_BUSINESS_CASUAL);
//                        }
//                    }
//                });
//
//                style_businessprofessional_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.STYLE_BUSINESS_PROFESSIONAL);
//                        }
//                        else {
//                            selectedItems.remove(ETag.STYLE_BUSINESS_PROFESSIONAL);
//                        }
//                    }
//                });
//
//                style_semiformal_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.STYLE_SEMI_FORMAL);
//                        }
//                        else {
//                            selectedItems.remove(ETag.STYLE_SEMI_FORMAL);
//                        }
//                    }
//                });
//
//                style_formal_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            selectedItems.add(ETag.STYLE_FORMAL);
//                        }
//                        else {
//                            selectedItems.remove(ETag.STYLE_FORMAL);
//                        }
//                    }
//                });
//
//                btnApplySelections.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int size = outfits.size();
//                        outfits.clear();
//                        adapter.notifyItemRangeRemoved(0, size);
//                        Database.requestOutfitsMatching(filterTags, new receiveOutfitCallback());
//                    }
//                });
                //dialog.show();
            }
        });



        // Decide how to load outfits
        outfits = new ArrayList<>();
        if (givenOutfits != null) {
            // Use the outfit(s) given to you.
            outfits = new ArrayList<>(givenOutfits);
        }
        else if (filterItems != null) {
            // Use the outfits from the database that match the items
            Database.getOutfitsMatching(filterItems, new receiveOutfitCallback());
        }
        else if (filterTags != null) {
            // Use the outfits from the database that match the filter
            Database.getOutfitsMatching(filterTags, new receiveOutfitCallback());
        }
        else {
            // Use every outfit from the database
            Database.getOutfits(new receiveOutfitCallback());
        }

        recyclerView = findViewById(R.id.list_of_filtered);
        adapter = new OutfitItemAdapter(outfits);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);


    }

    public void showAddItemPopup() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_checkboxes, null);
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        btnApplySelections = popupView.findViewById(R.id.btnApplySelections2);

        weather_hot_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxHot);
        weather_cold_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxCold);
        weather_fair_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFair);
        weather_rainy_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxRainy);


        gender_neutral_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxNeutral);
        gender_masculine_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxMasculine);
        gender_feminine_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFeminine);

        style_casual_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxCasual);
        style_smartcasual_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxSmartCasual);
        style_businesscasual_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxBusinessCasual);
        style_businessprofessional_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxBusinessProfessional);
        style_semiformal_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxSemiFormal);
        style_formal_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFormal);

        season_spring_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxSpring);
        season_winter_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxWinter);
        season_fall_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFall);
        season_summer_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxSummer);

        weather_hot_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.WEATHER_HOT);
                }
                else {
                    selectedItems.remove(ETag.WEATHER_HOT);
                }
            }
        });

        weather_cold_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.WEATHER_COLD);
                }
                else {
                    selectedItems.remove(ETag.WEATHER_COLD);
                }
            }
        });

        weather_fair_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.WEATHER_FAIR);
                }
                else {
                    selectedItems.remove(ETag.WEATHER_FAIR);
                }
            }
        });

        weather_rainy_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.WEATHER_RAINY);
                }
                else {
                    selectedItems.remove(ETag.WEATHER_RAINY);
                }
            }
        });

        gender_neutral_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.GENDER_NEUTRAL);
                }
                else {
                    selectedItems.remove(ETag.GENDER_NEUTRAL);
                }
            }
        });

        gender_masculine_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.GENDER_MASCULINE);
                }
                else {
                    selectedItems.remove(ETag.GENDER_MASCULINE);
                }
            }
        });

        gender_feminine_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.GENDER_FEMININE);
                }
                else {
                    selectedItems.remove(ETag.GENDER_FEMININE);
                }
            }
        });

        season_fall_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.SEASON_FALL);
                }
                else {
                    selectedItems.remove(ETag.SEASON_FALL);
                }
            }
        });

        season_spring_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.SEASON_SPRING);
                }
                else {
                    selectedItems.remove(ETag.SEASON_SPRING);
                }
            }
        });

        season_summer_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.SEASON_SUMMER);
                }
                else {
                    selectedItems.remove(ETag.SEASON_SUMMER);
                }
            }
        });

        season_winter_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.SEASON_WINTER);
                }
                else {
                    selectedItems.remove(ETag.SEASON_WINTER);
                }
            }
        });

        style_casual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.STYLE_CASUAL);
                }
                else {
                    selectedItems.remove(ETag.STYLE_CASUAL);
                }
            }
        });

        style_smartcasual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.STYLE_SMART_CASUAL);
                }
                else {
                    selectedItems.remove(ETag.STYLE_SMART_CASUAL);
                }
            }
        });

        style_businesscasual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.STYLE_BUSINESS_CASUAL);
                }
                else {
                    selectedItems.remove(ETag.STYLE_BUSINESS_CASUAL);
                }
            }
        });

        style_businessprofessional_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.STYLE_BUSINESS_PROFESSIONAL);
                }
                else {
                    selectedItems.remove(ETag.STYLE_BUSINESS_PROFESSIONAL);
                }
            }
        });

        style_semiformal_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.STYLE_SEMI_FORMAL);
                }
                else {
                    selectedItems.remove(ETag.STYLE_SEMI_FORMAL);
                }
            }
        });

        style_formal_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(ETag.STYLE_FORMAL);
                }
                else {
                    selectedItems.remove(ETag.STYLE_FORMAL);
                }
            }
        });

        btnApplySelections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = outfits.size();
                outfits.clear();
                adapter.notifyItemRangeRemoved(0, size);
                Database.getOutfitsMatching(filterTags, new receiveOutfitCallback());
            }
        });

        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);

    }

    private class receiveOutfitCallback implements Function<Outfit, Void> {
        @Override
        public Void apply(Outfit outfit) {
            OutfitActivity context = OutfitActivity.this;

            outfits.add(0, outfit);
            context.adapter.notifyItemInserted(0);
            Log.d("OutfitActivity", "Added outfit to list: " + outfit.toString());

            return null;
        }
    }

    public void startDisplayOutfitFragment(Outfit outfit) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("outfit", outfit);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setReorderingAllowed(true);
        trans.add(R.id.fragmentContainerView, OutfitDisplayFragment.class, bundle);
        trans.commit();
    }

    public void stopDisplayOutfitFragment(Fragment fragment) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.remove(fragment);
        trans.commit();
    }

    @Override
    public void finish() {
        super.finish();
        givenOutfits = null;
        filterItems = null;
        filterTags = null;
    }


}
