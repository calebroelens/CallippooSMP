package com.callippoonet.callippoosmp.lore;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnable;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableId;
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
    public String displayName;
    public List<String> description;
    public Map<PlayerLoreState, Map<PotionEffectType, Integer>> passiveEffects;
    public Map<PlayerLoreState, Map<Attribute, Float>> playerAttributes;
    public Map<PlayerLoreRunnableId, PlayerLoreRunnable> playerLoreRunnables;
    public Map<PlayerLoreState, Map<PlayerLoreRunnableId, PlayerLoreRunnable>> activePlayerLoreRunnables;
    public List<String> permissions;
    public List<CraftingRecipe> recipes;

    private PlayerLore(PlayerLoreBuilder builder){
        this.internalName = builder.internalName;
        this.displayName = builder.displayName;
        this.description = builder.description;
        this.passiveEffects = builder.passiveEffects;
        this.playerAttributes = builder.playerAttributes;
        this.playerLoreRunnables = builder.playerLoreRunnables;
        this.activePlayerLoreRunnables = builder.activePlayerLoreRunnable;
        this.permissions = builder.permissions;
        this.recipes = builder.recipes;
    }

    public void applyConfiguration(Player player, PlayerLoreState loreState){
        this.applyPassiveEffects(player, loreState);
        this.applyAttributes(player, loreState);
    }

    public void resetConfiguration(Player player, PlayerLoreState loreState){
        this.removePassiveEffects(player, loreState);
        this.removeAttributes(player, loreState);
    }

    public void resetAllConfigurations(Player player){
        for(PlayerLoreState loreState : PlayerLoreState.values()){
            Bukkit.getLogger().info("Resetting configuration for player " + player.getName() + ": " + loreState);
            this.removePassiveEffects(player, loreState);
            this.removeAttributes(player, loreState);
        }
    }

    public void applyAttributes(Player player, PlayerLoreState loreState){
        if(player == null) return;
        Map<Attribute, Float> attributes = playerAttributes.get(loreState);
        if(attributes == null) return;
        for(Map.Entry<Attribute, Float> entry : attributes.entrySet()){
            Objects.requireNonNull(player.getAttribute(entry.getKey())).setBaseValue(entry.getValue());
        }
    }

    public void applyPassiveEffects(Player player, PlayerLoreState loreState){
        /* Apply the preset configuration */
        if(player == null){return;}
        /* Apply potion effects */
        Map<PotionEffectType, Integer> effects = passiveEffects.get(loreState);
        if(effects == null) return;
        effects.forEach(
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

    public void removeAttributes(Player player, PlayerLoreState loreState){
        /* Remove attributes */
        if(player == null) return;
        Map<Attribute, Float> attributes = playerAttributes.get(loreState);
        if(attributes == null) return;
        for(Map.Entry<Attribute, Float> entry : attributes.entrySet()){
            AttributeInstance attribute = player.getAttribute(entry.getKey());
            if(attribute == null) continue;
            attribute.setBaseValue(attribute.getDefaultValue());
        }
    }

    public void removePassiveEffects(Player player, PlayerLoreState loreState){
        if(player == null) return;
        Map<PotionEffectType, Integer> effects = passiveEffects.get(loreState);
        if(effects == null) return;
        effects.forEach((effectType, effectValue) -> player.removePotionEffect(effectType));
    }

    public boolean hasRecipe(CraftingRecipe findRecipe){
        for(CraftingRecipe recipe: this.recipes){
            if(findRecipe.getKey().equals(recipe.getKey())) return true;
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
        private final String displayName;
        private final List<String> description;
        private final Map<PlayerLoreState, Map<PotionEffectType, Integer>> passiveEffects;
        private final Map<PlayerLoreState, Map<Attribute, Float>> playerAttributes;
        private final Map<PlayerLoreRunnableId, PlayerLoreRunnable> playerLoreRunnables;
        private final Map<PlayerLoreState, Map<PlayerLoreRunnableId, PlayerLoreRunnable>> activePlayerLoreRunnable;
        private final List<String> permissions;
        private final List<CraftingRecipe> recipes;

        public PlayerLoreBuilder(String internalName, String displayName) {
            this.internalName = internalName;
            this.displayName = displayName;
            this.description = new ArrayList<>();
            this.passiveEffects = new HashMap<>();
            this.playerAttributes = new HashMap<>();
            this.playerLoreRunnables = new HashMap<>();
            this.activePlayerLoreRunnable = new HashMap<>();
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

        public PlayerLoreBuilder addPassiveEffect(PlayerLoreState state, PotionEffectType effect, Integer level) {
            passiveEffects.computeIfAbsent(state, k -> new HashMap<>()).merge(
                    effect, level, Integer::sum
            );
            return this;
        }

        public PlayerLoreBuilder addPlayerAttribute(PlayerLoreState state, Attribute attribute, Float value){
            playerAttributes.computeIfAbsent(state, k -> new HashMap<>()).merge(
                    attribute, value, Float::sum
            );
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

        public PlayerLoreBuilder addPlayerRunnable(PlayerLoreRunnableId runnableId, PlayerLoreRunnable runnable){
            /* These runnables only start manually */
            playerLoreRunnables.put(runnableId, runnable);
            return this;
        }

        public PlayerLoreBuilder addActivePlayerLoreRunnable(PlayerLoreState state, PlayerLoreRunnableId runnableId, PlayerLoreRunnable runnable){
            /* These runnables start as soon as the lore is started and run every second */
            activePlayerLoreRunnable.computeIfAbsent(state, k -> new HashMap<>()).put(runnableId, runnable);
            return this;
        }

        public PlayerLore build(){
            return new PlayerLore(this);
        }
    }
}
