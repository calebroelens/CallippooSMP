package com.callippoonet.callippoosmp.lore;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@SuppressWarnings("UnstableApiUsage")
public class LoreItem {
    public String itemName;
    public String permissionName;
    public ItemStack itemStack;

    private LoreItem(LoreItemBuilder builder){
        this.itemName = builder.itemName;
        this.permissionName = builder.permissionName;
        this.itemStack = builder.itemStack;
    }

    public static class LoreItemBuilder {
        private final String itemName;
        private final String permissionName;
        public List<String> lore;
        private final ItemStack itemStack;
        public ItemMeta itemMeta;

        public LoreItemBuilder(JavaPlugin plugin, String itemName, String permissionName, ItemStack itemStack) {
            this.permissionName = permissionName;
            this.itemStack = itemStack;
            this.itemName = itemName;
            this.itemMeta = this.getItemMeta();
            this.setItemName(itemName);
            this.lore = new ArrayList<>();
        }

        private void setItemName(String itemName){
            itemMeta.setItemName(itemName);
        }

        public LoreItemBuilder addLore(String lore) {
            this.lore.add(lore);
            itemMeta.setLore(this.lore);
            return this;
        }

        public LoreItemBuilder addLoreStrings(List<String> lore) {
            for(String loreString: lore){ this.addLore(loreString);}
            return this;
        }

        public LoreItemBuilder setCustomModelDataComponent(List<String> customModelDataComponentStrings) {
            CustomModelDataComponent dataComponent = itemMeta.getCustomModelDataComponent();
            dataComponent.setStrings(customModelDataComponentStrings);
            itemMeta.setCustomModelDataComponent(dataComponent);
            return this;
        }

        public LoreItemBuilder setCustomModelDataComponentString(String customModelDataComponentString){
            CustomModelDataComponent dataComponent = itemMeta.getCustomModelDataComponent();
            List<String> customModelDataComponentStrings = new ArrayList<>();
            customModelDataComponentStrings.add(customModelDataComponentString);
            dataComponent.setStrings(customModelDataComponentStrings);
            itemMeta.setCustomModelDataComponent(dataComponent);
            return this;
        }

        public LoreItemBuilder addEnchant(Enchantment enchantment, int level) {
            itemMeta.addEnchant(enchantment, level, true);
            return this;
        }

        public LoreItemBuilder isUnbreakable() {
            itemMeta.setUnbreakable(true);
            return this;
        }

        /* Util & build */

        private ItemMeta getItemMeta(){
            return Objects.requireNonNull(this.itemStack.getItemMeta());
        }

        public LoreItem build(){
            this.itemStack.setItemMeta(this.itemMeta);
            return new LoreItem(this);
        }

    }
}
