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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Database {

    /**
     * Requests the database for the items in the closet of the user with the given username.
     * @param username the username of the user who's items to get.
     * @param getItemCallback receives the items from the database asynchronously. Called once for each item received from the database.
     */
    public static void requestItemsFromCloset(@NonNull String username, @NonNull Function<Item, Void> getItemCallback) {
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

    /**
     * Adds the given item to the closet of the user with the given username.
     * @param username the username of the user to add the item to.
     * @param item the item to be added.
     */
    public static void addItemToCloset(@NonNull String username, @NonNull Item item) {
        addItemToCloset(username, item, null);
    }

    /**
     * Adds the given item to the closet of the user with the given username.
     * @param username the username of the user to add the item to.
     * @param item the item to be added.
     * @param onAddCallback called after the item has been added to the database.
     */
    public static void addItemToCloset(@NonNull String username, @NonNull Item item, Function<Item, Void> onAddCallback) {
        // Get reference to closet in database
        DatabaseReference closetItemsRef = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("closetItem");
        // Generate a unique key
        String itemId = closetItemsRef.push().getKey();
        // Set the item attributes in the database to the item attributes
        assert itemId != null;
        closetItemsRef.child(itemId).updateChildren(getItemAttributeMap(item));
        // Report addition
        Log.d("Database", "Added item to closet: " + item);
        // Call callback if needed
        if (onAddCallback != null)
            onAddCallback.apply(item);
    }

    /**
     * Adds the given list of items to the closet of the user with the given username.
     * @param username the username of the user to add the items to.
     * @param items the list of items to be added.
     */
    public static void addItemsToCloset(@NonNull String username, @NonNull List<Item> items) {
        addItemsToCloset(username, items, null);
    }

    /**
     * Adds the given list of items to the closet of the user with the given username.
     * @param username the username of the user to add the items to.
     * @param items the list of items to be added.
     * @param onAddCallback called once for each item that has been added to the database.
     */
    public static void addItemsToCloset(String username, List<Item> items, Function<Item, Void> onAddCallback) {
        DatabaseReference closetItemsRef = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("closetItem");
        for (Item item : items) {
            String itemId = closetItemsRef.push().getKey();
            assert itemId != null;
            closetItemsRef.child(itemId).updateChildren(getItemAttributeMap(item));
            Log.d("Database", "Added item to closet: " + item);
            if (onAddCallback != null)  // Room for optimization
                onAddCallback.apply(item);
        }
    }

    /**
     * Removes the given item from the closet of the user with given username.
     * @param username the username of the user to remove the item from.
     * @param item the item to be removed.
     */
    public static void removeItemFromCloset(@NonNull String username, @NonNull Item item) {
        removeItemFromCloset(username, item, null);
    }

    /**
     * Removes the given item from the closet of the user with the given username.
     * @param username the username of the user to remove the item from.
     * @param item the item to be removed.
     * @param onDeleteCallback called after the item has been removed from the database.
     */
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

    /**
     * Removes all items in the closet of the user with the given username.
     * @param username the username of the user to clear the closet of.
     */
    public static void removeAllItemsFromCloset(@NonNull String username) {
        removeAllItemsFromCloset(username, null);
    }

    /**
     * Removes all items in the closet of the user with the given username.
     * @param username The username of the user to clear the closet of.
     * @param onDeleteAllCallback called after all items have been removed from the database.
     */
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

    /**
     * Parses the given item snapshot into an Item instance.
     * @param itemSnapshot the snapshot of the item to parse.
     * @return An Item that represents the snapshot.
     */
    private static Item parseItem(@NonNull DataSnapshot itemSnapshot) {
        Item item = null;
        Integer type = itemSnapshot.child("type").getValue(Integer.class);
        Integer color = itemSnapshot.child("color").getValue(Integer.class);
        if (type != null && color != null) {
            item = new Item(EItemType.fromId(type), EColor.fromInt(color));
        }
        return item;
    }

    /**
     * Parses the given item into an attribute map to be stored in the database.
     * @param item the item to be parsed.
     * @return a hashmap of the attributes to store in the database.
     */
    private static Map<String, Object> getItemAttributeMap(@NonNull Item item) {
        Map<String, Object> map = new HashMap<>();

        map.put("type", item.getType().toId());
        map.put("color", item.getColor().toInt());

        return map;
    }
}
