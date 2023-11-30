package com.example.mystylist;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.enums.ETag;
import com.example.mystylist.structures.Account;
import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;
import com.example.mystylist.structures.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Database {

    // Use when traversing/building database json. (keeps inputs/outputs uniform and synchronized).
    private static final String USERS_KEY = "users";
    private static final String USER_USERNAME_KEY = "username";
    private static final String USER_PASSWORD_KEY = "password";
    private static final String USER_EMAIL_KEY = "email";
    private static final String USER_PERSONAL_NAME_KEY = "name";

    private static final String USER_PROFILES_KEY = "profiles";
    private static final String PROFILE_NAME_KEY = "name";

    private static final String PROFILE_CLOSET_KEY = "closet";
    private static final String ITEM_TYPE_KEY = "type";
    private static final String ITEM_COLOR_KEY = "color";

    private static final String PROFILE_FAVORITES_KEY = "favorites";
    private static final String FAVORITE_OUTFIT_REF_KEY = "outfitRef";

    private static final String OUTFITS_KEY = "designerOutfits";
    private static final String OUTFIT_NAME_KEY = "name";
    private static final String OUTFIT_DESC_KEY = "desc";
    private static final String OUTFIT_NOI_KEY = "numberOfItems";
    private static final String OUTFIT_ITEMS_KEY = "items";
    private static final String OUTFIT_TAGS_KEY = "tags";



    public static void addAccount(@NonNull Account account) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference()
                .child(USERS_KEY)
                .child(account.getUsername());

        userReference.updateChildren(getAccountAttributeMap(account));
        Log.d("Database", "Added user to database: " + account.getUsername());
    }

    public static void getAccount(@NonNull String username, @NonNull String password, @NonNull Function<Account, Void> getAccountCallback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(USERS_KEY);
        Query checkUserDatabase = reference.orderByChild(USER_USERNAME_KEY).equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Account account = parseAccount(snapshot.child(username));
                    if (account != null) {
                        if (account.getPassword().equals(password)) {
                            getAccountCallback.apply(account);
                        } else {
                            Log.d("Database", "Failed to load account (invalid password): " + username);
                            getAccountCallback.apply(null);
                        }
                    }
                    else {
                        Log.d("Database", "Failed to load account: " + username);
                    }
                } else {
                    Log.d("Database", "Failed to load account (user not found): " + username);
                    getAccountCallback.apply(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    public static void addProfile(@NonNull String username, @NonNull Profile profile) {
        DatabaseReference profileReference = getProfileReference(username, profile.getName());
        Map<String, Object> map = new HashMap<String, Object>() { {
            put(PROFILE_NAME_KEY, profile.getName());
        } };

        profileReference.updateChildren(map);
        Log.d("Database", "Added profile to user: " + profile.getName() + " ---> "  + username);
    }

    public static void getProfiles(@NonNull String username, @NonNull Function<Profile, Void> getProfileCallback) {
        DatabaseReference profilesRef = FirebaseDatabase.getInstance().getReference()
                .child(USERS_KEY)
                .child(username)
                .child(USER_PROFILES_KEY);
        profilesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot profileSnapshot : snapshot.getChildren()) {
                        String name = profileSnapshot.child(PROFILE_NAME_KEY).getValue(String.class);
                        if (name != null) {
                            Log.d("Database", "Loaded profile: " + name);
                            getProfileCallback.apply(new Profile(name));
                        }
                        else {
                            Log.d("Database", "Failed to load profile: " + profileSnapshot.toString());
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

    public static void removeProfile(@NonNull String username, @NonNull String profileName) {
        DatabaseReference profileReference = getProfileReference(username, profileName);
        profileReference.removeValue();
    }


    /**
     * Requests items from the closet of the given user from the database.
     * @param username the username of the user who's items to get.
     * @param profileName the profile to get from.
     * @param getItemCallback receives the items from the database asynchronously. Called once for each item received from the database.
     */
    public static void getItemsFromCloset(@NonNull String username, @NonNull String profileName, @NonNull Function<Item, Void> getItemCallback) {
        DatabaseReference closetItemsRef = getProfileReference(username, profileName).child(PROFILE_CLOSET_KEY);
        closetItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Item item = parseItem(itemSnapshot);
                        if (item != null) {
                            Log.d("Database", "Loaded Item: " + item.toString());
                            getItemCallback.apply(item);
                        }
                        else {
                            Log.d("Database", "Failed to load item: " + itemSnapshot.toString());
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
     * @param profileName the profile to add to.
     * @param item the item to be added.
     */
    public static void addItemToCloset(@NonNull String username, @NonNull String profileName, @NonNull Item item) {
        addItemToCloset(username, profileName, item, null);
    }

    /**
     * Adds the given item to the closet of the user with the given username.
     * @param username the username of the user to add the item to.
     * @param profileName the profile to add to.
     * @param item the item to be added.
     * @param onAddCallback called after the item has been added to the database.
     */
    public static void addItemToCloset(@NonNull String username, @NonNull String profileName, @NonNull Item item, Function<Item, Void> onAddCallback) {
        // Get reference to closet in database
        DatabaseReference closetItemsRef = getProfileReference(username, profileName).child(PROFILE_CLOSET_KEY);
        // Generate a unique key
        String itemId = closetItemsRef.push().getKey();
        // Set the item attributes in the database to the item attributes
        assert itemId != null;
        closetItemsRef.child(itemId).updateChildren(getItemAttributeMap(item));
        // Report addition
        Log.d("Database", "Added item to closet: " + item.toString());
        // Call callback if needed
        if (onAddCallback != null)
            onAddCallback.apply(item);
    }

    /**
     * Adds the given list of items to the closet of the user with the given username.
     * @param username the username of the user to add the items to.
     * @param profileName the profile to add to.
     * @param items the list of items to be added.
     */
    public static void addItemsToCloset(@NonNull String username, @NonNull String profileName, @NonNull List<Item> items) {
        addItemsToCloset(username, profileName, items, null);
    }

    /**
     * Adds the given list of items to the closet of the user with the given username.
     * @param username the username of the user to add the items to.
     * @param profileName the profile to add to.
     * @param items the list of items to be added.
     * @param onAddCallback called once for each item that has been added to the database.
     */
    public static void addItemsToCloset(@NonNull String username, @NonNull String profileName, List<Item> items, Function<Item, Void> onAddCallback) {
        DatabaseReference closetItemsRef = getProfileReference(username, profileName).child(PROFILE_CLOSET_KEY);
        for (Item item : items) {
            String itemId = closetItemsRef.push().getKey();
            assert itemId != null;
            closetItemsRef.child(itemId).updateChildren(getItemAttributeMap(item));
            Log.d("Database", "Added item to closet: " + item.toString());
            if (onAddCallback != null)  // Room for optimization
                onAddCallback.apply(item);
        }
    }


    /**
     * Removes the given item from the closet of the user with given username.
     * @param username the username of the user to remove the item from.
     * @param profileName the profile to remove from.
     * @param item the item to be removed.
     */
    public static void removeItemFromCloset(@NonNull String username, @NonNull String profileName, @NonNull Item item) {
        removeItemFromCloset(username, profileName, item, null);
    }

    /**
     * Removes the given item from the closet of the user with the given username.
     * @param username the username of the user to remove the item from.
     * @param profileName the profile to remove from.
     * @param item the item to be removed.
     * @param onDeleteCallback called after the item has been removed from the database.
     */
    public static void removeItemFromCloset(@NonNull String username, @NonNull String profileName, Item item, Function<Item, Void> onDeleteCallback) {
        DatabaseReference closetItemReference = getProfileReference(username, profileName).child(PROFILE_CLOSET_KEY);
        closetItemReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Known bug: When closet has more than 1 item, deleting causes many "phantom" deletes of item
                // No functional side effects
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Item check = parseItem(itemSnapshot);
                        if (check != null) {
                            Log.d("Database", "Loaded " + check.toString());
                            if (item.equals(check)) {
                                itemSnapshot.getRef().removeValue();
                                Log.d("Database", "Removed item: " + item.toString());
                                if (onDeleteCallback != null)
                                    onDeleteCallback.apply(item);
                                break;  // Only delete the first match
                            }
                        } else {
                            Log.d("Database", "Failed to check item for removal: " + itemSnapshot.toString());
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
     * @param profileName the profile to clear.
     */
    public static void removeAllItemsFromCloset(@NonNull String username, @NonNull String profileName) {
        removeAllItemsFromCloset(username, profileName, null);
    }

    /**
     * Removes all items in the closet of the user with the given username.
     * @param username The username of the user to clear the closet of.
     * @param profileName the profile to clear.
     * @param onDeleteAllCallback called after all items have been removed from the database.
     */
    public static void removeAllItemsFromCloset(@NonNull String username, @NonNull String profileName, Function<List<Item>, Void> onDeleteAllCallback) {
        DatabaseReference closetItemReference = getProfileReference(username, profileName).child(PROFILE_CLOSET_KEY);
        if (onDeleteAllCallback == null) {
            // Easy version
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
                                Log.d("Database", "Failed to load item for removal: " + itemSnapshot.toString());
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
     * Requests favorited outfits from the user.
     * @param username the username of the user to get favorites of.
     * @param profileName the profile to get from.
     * @param getOutfitCallback receives the outfits from the database asynchronously. Called once for each item received from the database.
     */
    public static void getFavoritedOutfits(@NonNull String username, @NonNull String profileName, @NonNull Function<Outfit, Void> getOutfitCallback) {
        DatabaseReference favoritedOutfitsRef = getProfileReference(username, profileName).child(PROFILE_FAVORITES_KEY);
        favoritedOutfitsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference outfitsRef = FirebaseDatabase.getInstance().getReference().child(OUTFITS_KEY);
                if (snapshot.exists()) {
                    for (DataSnapshot favoriteSnapshot : snapshot.getChildren()) {
                        String outfitKey = favoriteSnapshot.child(FAVORITE_OUTFIT_REF_KEY).getValue(String.class);
                        if (outfitKey != null) {
                            getOutfitFromKey(outfitKey, getOutfitCallback);
                        }
                        else {
                            Log.d("Database", "Failed to load favorite: " + favoriteSnapshot.toString());
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
     * Adds the given outfit to the favorites of the user with the given username.
     * @param username the username of the user to add favorite outfit to.
     * @param profileName the profile to add to.
     * @param outfit the outfit to add.
     */
    public static void addFavoritedOutfit(@NonNull String username, @NonNull String profileName, @NonNull Outfit outfit) {
        addFavoritedOutfit(username, profileName, outfit, null);
    }

    /**
     * Adds the given outfit to the favorites of the user with the given username.
     * @param username the username of the user to add favorite outfit to.
     * @param profileName the profile to add to.
     * @param outfit the outfit to add.
     * @param onFavoriteCallback called after the outfit has been added to favorites.
     */
    public static void addFavoritedOutfit(@NonNull String username, @NonNull String profileName, @NonNull Outfit outfit, Function<Outfit, Void> onFavoriteCallback) {
        getOutfitSnapshotMatching(outfit, new Function<DataSnapshot, Void>() {
            @Override
            public Void apply(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    String outfitKey = dataSnapshot.getKey();
                    DatabaseReference favoritesRef = getProfileReference(username, profileName).child(PROFILE_FAVORITES_KEY);
                    String favoriteId = favoritesRef.push().getKey();

                    Map<String, Object> map = new HashMap<>();
                    map.put(FAVORITE_OUTFIT_REF_KEY, outfitKey);

                    assert favoriteId != null;
                    favoritesRef.child(favoriteId).updateChildren(map);
                    Log.d("Database", "Added outfit to favorites: " + outfitKey);
                    if (onFavoriteCallback != null)
                        onFavoriteCallback.apply(outfit);
                }
                else {
                    Log.d("Database", "Failed to favorite outfit: " + outfit.toString());
                    if (onFavoriteCallback != null)
                        onFavoriteCallback.apply(null);
                }
                return null;
            }
        });
    }


    public static void removeFavoritedOutfit(@NonNull String username, @NonNull String profileName, @NonNull Outfit outfit) {
        removeFavoritedOutfit(username, profileName, outfit, null);
    }
    /**
     * Removes the given outfit from the favorites of the user with the given username.
     * @param username the username of the user to remove outfit from.
     * @param profileName the profile to remove from.
     * @param outfit the outfit to remove.
     * @param onDeleteCallback called after the outfit has been removed from the user.
     */
    public static void removeFavoritedOutfit(@NonNull String username, @NonNull String profileName, @NonNull Outfit outfit, Function<Outfit, Void> onDeleteCallback) {
        getOutfitSnapshotMatching(outfit, new Function<DataSnapshot, Void>() {
            @Override
            public Void apply(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    String outfitKey = dataSnapshot.getKey();
                    assert outfitKey != null;
                    DatabaseReference favoritesRef = getProfileReference(username, profileName).child(PROFILE_FAVORITES_KEY);
                    favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Outfit deletedOutfit = null;
                            if (snapshot.exists()) {
                                for (DataSnapshot favoriteSnapshot : snapshot.getChildren()) {
                                    String checkKey = favoriteSnapshot.child(FAVORITE_OUTFIT_REF_KEY).getValue(String.class);
                                    if (checkKey != null) {
                                        if (outfitKey.equals(checkKey)) {
                                            favoriteSnapshot.getRef().removeValue();
                                            Log.d("Database", "Removed favorited outfit: " + checkKey);
                                            deletedOutfit = outfit;
                                            break;
                                        }
                                    } else {
                                        Log.d("Database", "Failed to check outfit key for removal: " + favoriteSnapshot.toString());
                                    }
                                }
                            }
                            if (onDeleteCallback != null) {
                                onDeleteCallback.apply(deletedOutfit);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle any errors if necessary
                            Log.e("Database", "Firebase data deletion error: " + error.getMessage());
                        }
                    });
                }
                else {
                    Log.d("Database", "Failed to find outfit: " + outfit.toString());
                    if (onDeleteCallback != null)
                        onDeleteCallback.apply(null);
                }
                return null;
            }
        });
    }


    /**
     * Requests outfits from the database.
     * @param getOutfitCallback receives the outfits from the database asynchronously. Called once for each outfit received from the database.
     */
    public static void getOutfits(@NonNull Function<Outfit, Void> getOutfitCallback) {
        DatabaseReference outfitsRef = FirebaseDatabase.getInstance().getReference().child(OUTFITS_KEY);
        outfitsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot outfitSnapshot : snapshot.getChildren()) {
                        Outfit outfit = parseOutfit(outfitSnapshot);
                        if (outfit != null) {
                            Log.d("Database", "Loaded Outfit: " + outfit.toString());
                            getOutfitCallback.apply(outfit);
                        }
                        else {
                            Log.d("Database", "Failed to load outfit: " + outfitSnapshot.toString());
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
     * Requests outfits from the database that contain the given items.
     * @param items the items to match for.
     * @param getOutfitCallback receives the outfits from the database asynchronously. Called once for each outfit received.
     */
    public static void getOutfitsMatching(@NonNull List<Item> items, @NonNull Function<Outfit, Void> getOutfitCallback) {
        DatabaseReference outfitsRef = FirebaseDatabase.getInstance().getReference().child(OUTFITS_KEY);
        outfitsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot outfitSnapshot : snapshot.getChildren()) {
                        Outfit outfit = parseOutfit(outfitSnapshot);
                        if (outfit != null) {
                            if (outfit.containsAll(items)) {
                                Log.d("Database", "Loaded Outfit: " + outfit.toString());
                                getOutfitCallback.apply(outfit);
                            }
                            else {
                                Log.d("Database", "Rejected Outfit: " + outfit.toString());
                            }
                        }
                        else {
                            Log.d("Database", "Failed to load outfit: " + outfitSnapshot.toString());
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
     * Requests outfits from the database that match the given tag mask filter.
     * @param tagMask the tag mask to match against.
     * @param getOutfitCallback receives the outfits from the database asynchronously. Called once for each outfit received.
     */
    public static void getOutfitsMatching(long tagMask, @NonNull Function<Outfit, Void> getOutfitCallback) {
        DatabaseReference outfitsRef = FirebaseDatabase.getInstance().getReference().child(OUTFITS_KEY);
        outfitsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot outfitSnapshot : snapshot.getChildren()) {
                        Outfit outfit = parseOutfit(outfitSnapshot);
                        if (outfit != null) {
                            if (outfit.tagsSatisfyFilter(tagMask)) {
                                Log.d("Database", "Loaded Outfit: " + outfit.toString());
                                getOutfitCallback.apply(outfit);
                            }
                            else {
                                Log.d("Database", "Rejected Outfit: " + outfit.toString());
                            }
                        }
                        else {
                            Log.d("Database", "Failed to load outfit: " + outfitSnapshot.toString());
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
     * Requests outfit with the given key from the database.
     * @param key the key of the outfit to retrieve.
     * @param getOutfitCallback receives outfit from the database asynchronously. Called once when outfit found. Argument will be the outfit. If outfit is not found, argument will be null.
     */
    public static void getOutfitFromKey(String key, @NonNull Function<Outfit, Void> getOutfitCallback) {
        DatabaseReference outfitRef = FirebaseDatabase.getInstance().getReference().child(OUTFITS_KEY).child(key);
        outfitRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Outfit outfit = parseOutfit(snapshot);
                    if (outfit != null) {
                        Log.d("Database", "Loaded Outfit: " + outfit.toString());
                        getOutfitCallback.apply(outfit);
                    }
                    else {
                        Log.d("Database", "Failed to load outfit: " + snapshot.toString());
                        getOutfitCallback.apply(null);
                    }
                } else {
                    Log.d("Database", "Could not find outfit with key: " + key);
                    getOutfitCallback.apply(null);
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
     * Requests the data snapshot of the outfit in the database that is equivalent to the given outfit.
     * @param outfit the outfit to match with.
     * @param getDataSnapshotCallback receives the outfit snapshot that matches the outfit asynchronously. If outfit not found, argument is null.
     */
    private static void getOutfitSnapshotMatching(@NonNull Outfit outfit, @NonNull Function<DataSnapshot, Void> getDataSnapshotCallback) {
        DatabaseReference outfitsRef = FirebaseDatabase.getInstance().getReference().child(OUTFITS_KEY);
        outfitsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot foundOutfitSnapshot = null;
                if (snapshot.exists()) {
                    for (DataSnapshot outfitSnapshot : snapshot.getChildren()) {
                        Outfit check = parseOutfit(outfitSnapshot);
                        if (check != null) {
                            if (outfit.equals(check)) {
                                Log.d("Database", "Matched with outfit: " + check.toString());
                                foundOutfitSnapshot = outfitSnapshot;
                                break;
                            }
                            else {
                                Log.d("Database", "Loaded Outfit but no match: " + check.toString());
                            }

                        }
                        else {
                            Log.d("Database", "Failed to load outfit: " + outfitSnapshot.toString());
                        }
                    }
                } else {
                    // Handle the case when there is no data (snapshot doesn't exist)
                    // For example, you can display a message to the user.
                    Log.d("Database", "No data found in Firebase");
                }
                getDataSnapshotCallback.apply(foundOutfitSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors if necessary
                Log.e("Database", "Firebase data loading error: " + error.getMessage());
            }
        });
    }


    /**
     * Adds the given outfit to the database. (easier than inputting directly into database).
     * @param outfit the outfit to be added.
     */
    public static void addOutfit(@NonNull Outfit outfit) {
        DatabaseReference outfitsRef = FirebaseDatabase.getInstance().getReference().child(OUTFITS_KEY);
        String outfitId = outfitsRef.push().getKey();
        assert outfitId != null;
        outfitsRef.child(outfitId).updateChildren(getOutfitAttributeMap(outfit));
        Log.d("Database", "Added outfit to database: " + outfit.toString());
    }


    private static Account parseAccount(@NonNull DataSnapshot accountSnapshot) {
        Account account = null;

        String username = accountSnapshot.child(USER_USERNAME_KEY).getValue(String.class);
        String password = accountSnapshot.child(USER_PASSWORD_KEY).getValue(String.class);
        String email = accountSnapshot.child(USER_EMAIL_KEY).getValue(String.class);
        String name = accountSnapshot.child(USER_PERSONAL_NAME_KEY).getValue(String.class);

        if (username != null &&
                password != null &&
                email != null &&
                name != null) {
            account = new Account(username, password, email, name);
        }

        return account;
    }

    private static Map<String, Object> getAccountAttributeMap(@NonNull Account account) {
        return new HashMap<String, Object>() { {
            put(USER_USERNAME_KEY, account.getUsername());
            put(USER_PASSWORD_KEY, account.getPassword());
            put(USER_EMAIL_KEY, account.getEmail());
            put(USER_PERSONAL_NAME_KEY, account.getName());
        } };
    }


    /**
     * Parses the given item snapshot into an Item instance.
     * @param itemSnapshot the snapshot of the item to parse.
     * @return An Item that represents the snapshot.
     */
    private static Item parseItem(@NonNull DataSnapshot itemSnapshot) {
        Item item = null;

        // Get data
        Integer type = itemSnapshot.child(ITEM_TYPE_KEY).getValue(Integer.class);
        Integer color = itemSnapshot.child(ITEM_COLOR_KEY).getValue(Integer.class);

        // Check data valid
        if (type != null && color != null) {
            item = new Item(EItemType.fromId(type), EColor.fromInt(color));
        }

        // Return item
        return item;
    }

    /**
     * Parses the given item into an attribute map to be stored in the database.
     * @param item the item to be parsed.
     * @return a hashmap of the attributes to store in the database.
     */
    private static Map<String, Object> getItemAttributeMap(@NonNull Item item) {
        Map<String, Object> map = new HashMap<>();

        map.put(ITEM_TYPE_KEY, item.getType().toId());
        map.put(ITEM_COLOR_KEY, item.getColor().toInt());

        return map;
    }


    /**
     * Parses the given outfit snapshot into an Outfit instance.
     * @param outfitSnapshot the snapshot of the outfit to parse.
     * @return An Outfit that represents the snapshot.
     */
    private static Outfit parseOutfit(@NonNull DataSnapshot outfitSnapshot) {
        Outfit outfit = null;

        // Get data
        String outfitName = outfitSnapshot.child(OUTFIT_NAME_KEY).getValue(String.class);
        String outfitDesc = outfitSnapshot.child(OUTFIT_DESC_KEY).getValue(String.class);
        Integer numberOfItems = outfitSnapshot.child(OUTFIT_NOI_KEY).getValue(Integer.class);  // For error checking
        List<Item> items = null;
        DataSnapshot itemsSnapshot = outfitSnapshot.child(OUTFIT_ITEMS_KEY);
        if (itemsSnapshot.exists()) {
            items = new ArrayList<>();
            for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                Item item = parseItem(itemSnapshot);
                if (item != null)
                    items.add(item);
            }
        }
        Integer tagFlags = outfitSnapshot.child(OUTFIT_TAGS_KEY).getValue(Integer.class);

        // Check data valid
        if (outfitName != null
                && outfitDesc != null
                && numberOfItems != null
                && items != null
                && tagFlags != null) {
            outfit = new Outfit(outfitName, outfitDesc, items.toArray(new Item[] {}), tagFlags);
            if (items.size() != numberOfItems)
                Log.d("Database", "WARNING: Number of outfit items does not match the expected number of items. Expected-" + numberOfItems + " Outfit-" + outfit);
        }

        // Return outfit
        return outfit;
    }

    /**
     * Parses the given outfit into an attribute map to be stored in the database.
     * @param outfit the outfit to be parsed.
     * @return a hashmap of the attributes to store in the database.
     */
    private static Map<String, Object> getOutfitAttributeMap(@NonNull Outfit outfit) {
        Map<String, Object> map = new HashMap<>();

        map.put(OUTFIT_NAME_KEY, outfit.getOutfitName());
        map.put(OUTFIT_DESC_KEY, outfit.getOutfitDesc());
        map.put(OUTFIT_NOI_KEY, outfit.getItems().size());
        int arbitraryIndex = 0;
        Map<String, Object> itemMap = new HashMap<>();
        for (Item item : outfit.getItems()) {
            itemMap.put("item_" + arbitraryIndex++, getItemAttributeMap(item));
        }
        map.put(OUTFIT_ITEMS_KEY, itemMap);
        map.put(OUTFIT_TAGS_KEY, outfit.getTagFlags());

        return map;
    }


    private static DatabaseReference getProfileReference(@NonNull String username, @NonNull String profileName) {
        return FirebaseDatabase.getInstance().getReference().child(USERS_KEY).child(username).child(USER_PROFILES_KEY).child(profileName);
    }


    /**
     * !!! DANGER !!! DO NOT USE !!! Outfits are _supposed_ to be static in the database. Clears and rebuilds the default outfits in the database. (it's much easier to add outfits here than through Firebase GUI).
     */
    public static void rebuildOutfitsInDatabase() {
        // !!! DO NOT USE !!! (unless you really know what you're doing).
        DatabaseReference outfitsRef = FirebaseDatabase.getInstance().getReference().child(OUTFITS_KEY);
        outfitsRef.removeValue();

        // List of default outfits
        LinkedList<Outfit> tempOutfits = new LinkedList<>();
        tempOutfits.add(new Outfit(
                "Average Day",
                "A simple, casual outfit to wear at home, or out with friends.",
                new Item[] {
                        new Item(EItemType.T_SHIRT, EColor.BLACK),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                        new Item(EItemType.SNEAKERS, EColor.BLACK),
                },
                new ETag[] {
                        ETag.GENDER_NEUTRAL,
                        ETag.SEASON_SPRING, ETag.SEASON_SUMMER, ETag.SEASON_FALL,
                        ETag.WEATHER_FAIR, ETag.WEATHER_HOT,
                        ETag.STYLE_CASUAL,
                }));
        tempOutfits.add(new Outfit(
                "Something Different",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.T_SHIRT, EColor.BLACK),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                },
                new ETag[] {
                        // TODO
                }));
        tempOutfits.add(new Outfit(
                "Lounging at Home",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.T_SHIRT, EColor.BLACK),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                },
                new ETag[] {
                        // TODO
                }));
        tempOutfits.add(new Outfit(
                "Fancy Dinner",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.BLOUSE, EColor.WHITE),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                        new Item(EItemType.SHORTS, EColor.DARK_BLUE),
                },
                new ETag[] {
                        // TODO
                }));
        tempOutfits.add(new Outfit(
                "Family Party",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.BLOUSE, EColor.WHITE),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                },
                new ETag[] {
                        // TODO
                }));
        tempOutfits.add(new Outfit(
                "Catch His Eye",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.DRESS, EColor.BEIGE),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                },
                new ETag[] {
                        // TODO
                }));
        tempOutfits.add(new Outfit(
                "Sandy Outing",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.DRESS, EColor.BEIGE),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                },
                new ETag[] {
                        // TODO
                }));
        tempOutfits.add(new Outfit(
                "Irish Fem Fatale",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.HEELS, EColor.GREEN),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                },
                new ETag[] {
                        // TODO
                }));
        tempOutfits.add(new Outfit(
                "Business Day",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.LONG_SLEEVE_SHIRT, EColor.GREY),
                        new Item(EItemType.SHORTS, EColor.BEIGE),
                        new Item(EItemType.LOAFERS, EColor.DARK_BLUE),
                },
                new ETag[] {
                        // TODO
                }));
        tempOutfits.add(new Outfit(
                "Flowery Day",
                "WRITE ME", // TODO
                new Item[] {
                        // TODO
                        new Item(EItemType.T_SHIRT, EColor.PINK),
                        new Item(EItemType.JEANS, EColor.DARK_BLUE),
                        new Item(EItemType.SNEAKERS, EColor.GREY),
                },
                new ETag[] {
                        // TODO
                }));

        tempOutfits.add(new Outfit(
                "Cold Day Outside",
                "WRITE ME", // TODO
                new Item[] {
                        new Item(EItemType.SWEATER, EColor.BLACK),
                        new Item(EItemType.JEANS, EColor.DARK_BLUE),
                        new Item(EItemType.JACKET, EColor.GREY),
                        new Item(EItemType.COAT, EColor.BROWN),
                },
                new ETag[] {
                        ETag.GENDER_FEMININE,
                        ETag.SEASON_WINTER, ETag.SEASON_FALL,
                        ETag.WEATHER_FAIR, ETag.WEATHER_COLD,
                        ETag.STYLE_SMART_CASUAL,
                }));
        tempOutfits.add(new Outfit(
                "Cold Day in the Forrest",
                "WRITE ME", // TODO
                new Item[] {
                        new Item(EItemType.SWEATER, EColor.BLACK),
                        new Item(EItemType.JEANS, EColor.DARK_BLUE),
                        new Item(EItemType.COAT, EColor.BROWN),
                },
                new ETag[] {
                        ETag.GENDER_FEMININE,
                        ETag.SEASON_WINTER, ETag.SEASON_FALL,
                        ETag.WEATHER_FAIR, ETag.WEATHER_COLD,
                        ETag.STYLE_SMART_CASUAL,
                }));
        // TODO: Still need 38 more to meet requirements


        for (Outfit outfit : tempOutfits) {
            Database.addOutfit(outfit);
        }
    }
}
