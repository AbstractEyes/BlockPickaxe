package com.abstractphil.customtools.util;

import com.google.gson.JsonObject;
import com.redmancometh.reditems.RedItems;
import com.redmancometh.reditems.storage.SimpleContainer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public class AbsPhilJsonUtil {

    private static boolean hasInternalAmount(JsonObject json, String key) {
         return json.get(key).getAsJsonObject().has("amount");
    }

    public static int getInternalIntAmountWithKey(JsonObject json, String key) {
        if(!json.has(key) || !hasInternalAmount(json, key)) return 0;
        return json.get(key).getAsJsonObject().get("amount").getAsInt();
    }
    public static JsonObject setInternalIntAmountWithKey(JsonObject json, String key, int val) {
        json.get(key).getAsJsonObject().addProperty("amount", val);
        return json;
    }
    public static JsonObject addInternalIntAmountWithKey(JsonObject json, String key, int val) {
        setInternalIntAmountWithKey(json, key, getInternalIntAmountWithKey(json, key) + val);
        return json;
    }
    @Nullable
    public static JsonObject setInternalIntAmountWithKey(ItemStack item, String key, int val) {
        if(RedItems.getInstance().getEnchantManager().isRedItem(item)) {

            if(getContainer(item) != null) {
                getContainer(item).setInt(key, val);
                return getContainer(item).getData();
            }
        }
        return null;
    }

    @Nullable
    private static SimpleContainer getContainer(ItemStack item) {
        Optional<SimpleContainer> container = RedItems.getInstance().getEnchantManager().getData(item);
        return container.orElse(null);
    }

    public static int getPumpkinsBroken(JsonObject json) {
        if(!json.has("pumpkinsbroken")) return 0;
            return json.get("pumpkinsbroken").getAsInt();
    };
    public static JsonObject setPumpkinsBroken(JsonObject json, int amount) {
        json.addProperty("pumpkinsbroken", amount);
        return json;
    }
    public static JsonObject addPumpkinsBroken(JsonObject json, int amount) {
        setPumpkinsBroken(json, getPumpkinsBroken(json) + amount);
        return json;
    }

    public static boolean getArbitraryBool(JsonObject json) {
        if(!json.has("arbitrary_bool")) return false;
            return json.get("arbitrary_bool").getAsBoolean();
    }
    public static JsonObject setArbitraryBool(JsonObject json, boolean bool) {
        json.addProperty("arbitrarybool", bool);
        return json;
    }

    public static String getOwnerId(JsonObject json) { return json.get("ownerid").getAsString(); };
    public static JsonObject setOwnerId(JsonObject json, String idIn) {
        json.addProperty("ownerid", idIn);
        return json;
    }

    // Manages the internal effects levels. Prototype for something larger later.
    public static int getEffectLevel(JsonObject json, String effectName) {
        if(json == null || json.isJsonNull()) return 0;
        if(!json.has(effectName) || !json.getAsJsonObject(effectName).has("effectlevel")) return 0;
            return json.getAsJsonObject(effectName).get("effectlevel").getAsInt();
    };
    public static JsonObject setEffectLevel(JsonObject json, String effectName, int level) {
        if(!json.has(effectName)) json.add(effectName, new JsonObject());
            json.getAsJsonObject(effectName).addProperty("effectlevel", level);
        return json;
    }

}
