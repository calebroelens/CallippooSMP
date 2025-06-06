package com.callippoonet.callippoosmp.lore;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PlayerLore {
    public String internalName;
    public String permissionName;
    public String displayName;
    public List<String> description;
    public Map<PotionEffectType, Integer> passiveEffects;
    public Map<Attribute, Float> playerAttributes;
    public List<String> permissions;
    public List<CraftingRecipe> recipes;

    private PlayerLore(PlayerLoreBuilder builder){
        this.internalName = builder.internalName;
        this.permissionName = builder.permissionName;
        this.displayName = builder.displayName;
        this.description = builder.description;
        this.passiveEffects = builder.passiveEffects;
        this.playerAttributes = builder.playerAttributes;
        this.permissions = builder.permissions;
        this.recipes = builder.recipes;
    }

    public void applyConfiguration(Player player){
        this.applyPassiveEffects(player);
        this.applyAttributes(player);
    }

    public void resetConfiguration(Player player){
        this.removePassiveEffects(player);
        this.removeAttributes(player);
    }

    public void applyAttributes(Player player){
        if(player == null) return;
        for(Map.Entry<Attribute, Float> entry : this.playerAttributes.entrySet()){
            Objects.requireNonNull(player.getAttribute(entry.getKey())).setBaseValue(entry.getValue());
        }
    }

    public void applyPassiveEffects(Player player){
        /* Apply the preset configuration */
        if(player == null){return;}
        /* Apply potion effects */
        this.passiveEffects.forEach(
                (effectType, effectValue) -> {
                    PotionEffect potionEffect = new PotionEffect(
                            effectType,
                            PotionEffect.INFINITE_DURATION,
                            effectValue,
                            false,
                            false
                    );
                    player.addPotionEffect(potionEffect);
                }
        );
    }

    public void removeAttributes(Player player){
        if(player == null) return;
        for(Map.Entry<Attribute, Float> entry : this.playerAttributes.entrySet()){
            AttributeInstance attribute = player.getAttribute(entry.getKey());
            if(attribute == null) continue;
            attribute.setBaseValue(attribute.getDefaultValue());
        }
    }

    public void removePassiveEffects(Player player){
        if(player == null) return;
        this.passiveEffects.forEach(
                (effectType, effectValue) -> {
                    player.removePotionEffect(effectType);
                }
        );
    }

    public boolean hasRecipe(CraftingRecipe findRecipe){
        for(CraftingRecipe recipe: this.recipes){
            if(recipe.equals(findRecipe)) return true;
        }
        return false;
    }

    public List<NamespacedKey> recipeNameSpaceKeys(){
        List<NamespacedKey> keys = new ArrayList<>();
        for(CraftingRecipe recipe: this.recipes){
            Bukkit.getLogger().info("Found recipe " + recipe.getKey());
            keys.add(recipe.getKey());
        }
        return keys;
    }

    public static class PlayerLoreBuilder {
        private final String internalName;
        private final String permissionName;
        private final String displayName;
        private final List<String> description;
        private final Map<PotionEffectType, Integer> passiveEffects;
        private final Map<Attribute, Float> playerAttributes;
        private final List<String> permissions;
        private final List<CraftingRecipe> recipes;

        public PlayerLoreBuilder(String internalName, String permissionName, String displayName) {
            this.internalName = internalName;
            this.permissionName = permissionName;
            this.displayName = displayName;
            this.description = new ArrayList<>();
            this.passiveEffects = new HashMap<>();
            this.playerAttributes = new HashMap<>();
            this.permissions = new ArrayList<>();
            this.recipes = new ArrayList<>();
        }

        public PlayerLoreBuilder addDescription(String description) {
            this.description.add(description);
            return this;
        }

        public PlayerLoreBuilder addDescriptions(List<String> descriptions) {
            this.description.addAll(descriptions);
            return this;
        }

        public PlayerLoreBuilder addPassiveEffects(Map<PotionEffectType, Integer> effects) {
            this.passiveEffects.putAll(effects);
            return this;
        }

        public PlayerLoreBuilder addPassiveEffect(PotionEffectType effect, Integer level) {
            this.passiveEffects.put(effect, level);
            return this;
        }

        public PlayerLoreBuilder addPlayerAttribute(Attribute attribute, Float value){
            this.playerAttributes.put(attribute, value);
            return this;
        }

        public PlayerLoreBuilder addPlayerAttributes(Map<Attribute, Float> playerAttributes) {
            this.playerAttributes.putAll(playerAttributes);
            return this;
        }

        public PlayerLoreBuilder addPermission(String permission) {
            this.permissions.add(permission);
            return this;
        }

        public PlayerLoreBuilder addPermissions(List<String> permissions) {
            this.permissions.addAll(permissions);
            return this;
        }

        public PlayerLoreBuilder addRecipe(CraftingRecipe recipe) {
            this.recipes.add(recipe);
            return this;
        }

        public PlayerLore build(){
            return new PlayerLore(this);
        }
    }
}
