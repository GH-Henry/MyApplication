package com.example.mystylist;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.structures.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Database {

    public static void receiveItemsFromCloset(@NonNull String username, @NonNull Function<Item, Void> getItemCallback) {
        DatabaseReference closetItemsRef = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("closetItem");
        closetItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Item item = parseItem(itemSnapshot);
                        if (item != null) {
                            Log.d("Database", "Loaded Item: " + item);
                            getItemCallback.apply(item);
                        }
                        else {
                            Log.d("Database", "Failed to load item: " + itemSnapshot);
                        }
                    }
                } else {
                    // Handle the case when there is no data (snapshot doesn't exist)
                    // For example, you can display a message to the user.
                    Log.d("Database", "No data found in Firebase");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors if necessary
                Log.e("Database", "Firebase data loading error: " + error.getMessage());
            }
        });
    }

    public static void addItemToCloset(@NonNull String username, @NonNull Item item) {
        addItemToCloset(username, item, null);
    }
    public static void addItemToCloset(@NonNull String username, @NonNull Item item, Function<Item, Void> onAddCallback) {
        // Get reference to closet in database
        DatabaseReference closetItemsRef = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("closetItem");
        // Generate a unique key
        String itemId = closetItemsRef.push().getKey();
        // Set the item attributes in the database to the item attributes
        assert itemId != null;
        closetItemsRef.child(itemId).updateChildren(item.getAttributeMap());
        // Report addition
        Log.d("Database", "Added item to closet: " + item);
        // Call callback if needed
        if (onAddCallback != null)
            onAddCallback.apply(item);
    }

    public static void addItemsToCloset(@NonNull String username, @NonNull List<Item> items) {
        addItemsToCloset(username, items, null);
    }
    public static void addItemsToCloset(String username, List<Item> items, Function<Item, Void> onAddCallback) {
        DatabaseReference closetItemsRef = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("closetItem");
        for (Item item : items) {
            String itemId = closetItemsRef.push().getKey();
            assert itemId != null;
            closetItemsRef.child(itemId).updateChildren(item.getAttributeMap());
            Log.d("Database", "Added item to closet: " + item);
            if (onAddCallback != null)  // Room for optimization
                onAddCallback.apply(item);
        }
    }

    public static void removeItemFromCloset(@NonNull String username, @NonNull Item item) {
        removeItemFromCloset(username, item, null);
    }
    public static void removeItemFromCloset(String username, Item item, Function<Item, Void> onDeleteCallback) {
        DatabaseReference closetItemReference = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("closetItem");


        closetItemReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Known bug: When closet has more than 1 item, deleting causes many "phantom" deletes of item
                // No functional side effects
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Item check = parseItem(itemSnapshot);
                        if (check != null) {
                            Log.d("Database", "Loaded " + check);
                            if (item.equals(check)) {
                                itemSnapshot.getRef().removeValue();
                                Log.d("Database", "Removed item: " + item);
                                if (onDeleteCallback != null)
                                    onDeleteCallback.apply(item);
                                break;  // Only delete the first match
                            }
                        } else {
                            Log.d("Database", "Failed to check item for removal: " + itemSnapshot);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors if necessary
                Log.e("Database", "Firebase data deletion error: " + error.getMessage());
            }
        });
    }

    public static void removeAllItemsFromCloset(@NonNull String username) {
        removeAllItemsFromCloset(username, null);
    }
    public static void removeAllItemsFromCloset(@NonNull String username, Function<List<Item>, Void> onDeleteAllCallback) {
        DatabaseReference closetItemReference = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("closetItem");
        if (onDeleteAllCallback == null) {
            closetItemReference.removeValue();
        }
        else {
            // If callback provided, need to retrieve data before deleting from database
            closetItemReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    LinkedList<Item> removedItems = new LinkedList<>();
                    if (snapshot.exists()) {
                        for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                            Item item = parseItem(itemSnapshot);
                            if (item != null) {
                                removedItems.add(item);
                            } else {
                                Log.d("Database", "Failed to load item for removal: " + itemSnapshot);
                            }
                        }
                    }
                    snapshot.getRef().removeValue();
                    onDeleteAllCallback.apply(new ArrayList<>(removedItems));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle any errors if necessary
                    Log.e("Database", "Firebase data deletion error: " + error.getMessage());
                }
            });
        }
    }

    private static Item parseItem(@NonNull DataSnapshot itemSnapshot) {
        Item item = null;
        Integer type = itemSnapshot.child("type").getValue(Integer.class);
        Integer color = itemSnapshot.child("color").getValue(Integer.class);
        if (type != null && color != null) {
            item = new Item(EItemType.fromId(type), EColor.fromInt(color));
        }
        return item;
    }
}
