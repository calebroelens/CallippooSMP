package com.callippoonet.callippoosmp;
import com.callippoonet.callippoosmp.commands.PlayerLoreCommands;
import com.callippoonet.callippoosmp.commands.tabcompleters.PlayerLoreCommandsTabCompleters;
import com.callippoonet.callippoosmp.events.*;
import com.callippoonet.callippoosmp.lore.LoreItem;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import com.callippoonet.callippoosmp.lore.PlayerLoreState;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableId;
import com.callippoonet.callippoosmp.runnable.SwitchLoreStateRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;



public class Main extends JavaPlugin {

    public PlayerLoreRegister playerLoreRegister;

    @Override
    public void onEnable() {
        /* Default setup */
        this.saveDefaultConfig();
        /* Generate PlayerLoreRegister: Core of the plugin */
        playerLoreRegister = this.generatePlayerLoreRegister();
        /* Register Event listeners */
        this.registerEvents(playerLoreRegister);
        this.registerCommands(playerLoreRegister);
    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info("CallippooSMP has been disabled");
    }

    public void registerEvents(PlayerLoreRegister playerLoreRegister) {
        getServer().getPluginManager().registerEvents(new BedEventsListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(playerLoreRegister), this);
        getServer().getPluginManager().registerEvents(new RespawnEventListener(this), this);
        getServer().getPluginManager().registerEvents(new CraftingEventListener(playerLoreRegister), this);
        getServer().getPluginManager().registerEvents(new CrafterCraftEventListener(playerLoreRegister), this);
        getServer().getPluginManager().registerEvents(new ProjectileEventListener(), this);
        getServer().getPluginManager().registerEvents(new ChatEventListener(this), this);
        getServer().getPluginManager().registerEvents(new SpectralPickaxeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBukkitFillEventListener(this), this);
        getServer().getPluginManager().registerEvents(new InfinityLiquidEvent(), this);
    }

    public void registerCommands(PlayerLoreRegister playerLoreRegister) {
        PluginCommand loreMainCommand = getCommand("lore");
        if(loreMainCommand != null){
            loreMainCommand.setExecutor(new PlayerLoreCommands(playerLoreRegister, this));
            loreMainCommand.setTabCompleter(new PlayerLoreCommandsTabCompleters(playerLoreRegister));
        }
    }

    public PlayerLoreRegister generatePlayerLoreRegister(){
        PlayerLoreRegister playerLoreRegister = new PlayerLoreRegister(this);
        playerLoreRegister.registerLore(generateDwarfPlayerLore());
        playerLoreRegister.registerLore(generateGlibboLore());
        playerLoreRegister.registerLore(generateAureliusLore());
        playerLoreRegister.registerLore(generateZyrothLore());
        playerLoreRegister.registerLore(generateWindchargerLore());
        playerLoreRegister.registerLore(generateUmbraniteLore());
        playerLoreRegister.registerLore(generateFaewynnLoreLore());
        playerLoreRegister.registerLore(generateBurlyBaluf());
        return playerLoreRegister;
    }

    public PlayerLore generateFaewynnLoreLore(){
        SwitchLoreStateRunnable switchLoreStateRunnable = new SwitchLoreStateRunnable(this);
        return new PlayerLore.PlayerLoreBuilder(
                "faewynn",
                "Faewynn"
        )
                .addPassiveEffect(PlayerLoreState.DEFAULT, PotionEffectType.GLOWING, 0)
                .addPassiveEffect(PlayerLoreState.DEFAULT, PotionEffectType.REGENERATION, 0)
                .addPassiveEffect(PlayerLoreState.DEFAULT, PotionEffectType.WEAKNESS, 0)
                .addPassiveEffect(PlayerLoreState.SECONDARY, PotionEffectType.STRENGTH, 1)
                .addPassiveEffect(PlayerLoreState.SECONDARY, PotionEffectType.SPEED, 0)
                .addPlayerAttribute(PlayerLoreState.SECONDARY, Attribute.MAX_HEALTH, 12f)
                .addPlayerRunnable(PlayerLoreRunnableId.SEVEN, switchLoreStateRunnable)
                .build();
    }

    public PlayerLore generateDwarfPlayerLore(){
        CraftingRecipe recipe = this.getSpectralPickaxeRecipe();
        return new PlayerLore.PlayerLoreBuilder(
                "dwarf",
                "Dwarf"
        )
                .addDescription("&cThis little guy fits through every gap, and mines faster than your sight can register.")
                .addPlayerAttribute(PlayerLoreState.DEFAULT, Attribute.SCALE, 0.5f)
                .addPlayerAttribute(PlayerLoreState.DEFAULT, Attribute.BLOCK_BREAK_SPEED, 1.5f)
                .addPlayerAttribute(PlayerLoreState.DEFAULT, Attribute.MAX_HEALTH, 16f)
                .addRecipe(recipe)
                .build();
    }

    public PlayerLore generateGlibboLore(){
        /* Infinite water bucket */
        ItemStack infinityLiquid = new ItemStack(Material.STICK);
        LoreItem loreItem = new LoreItem.LoreItemBuilder(
                this,
                ChatColor.GOLD + "Infinity Liquid",
                "callippoosmp.lore.item.infinity_liquid",
                infinityLiquid
        )
                .setCustomModelDataComponentString("infinity_liquid")
                .addLore(ChatColor.RED + "This magic stick keeps on spawning water. No idea how.")
                .addEnchant(Enchantment.INFINITY, 1)
                .isUnbreakable()
                .build();

        NamespacedKey namespacedKey = new NamespacedKey(this, "infinity_liquid");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, loreItem.itemStack);
        recipe.shape("GSG", "SAS", "GSG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.IRON_INGOT);
        recipe.setIngredient('A', Material.WATER_BUCKET);
        Bukkit.addRecipe(recipe);

        return new PlayerLore.PlayerLoreBuilder(
                "glibbo",
                "Glibbo"
        )
                .addDescription("Glibbo Waterfrats is een wat zonderlinge, maar goedbedoelende watermagiër die zich goed voelt in de diepste,")
                .addDescription("meest vergeten ondergrondse koraalrifgrotten van Minecraftia.")
                .addPassiveEffect(PlayerLoreState.DEFAULT, PotionEffectType.DOLPHINS_GRACE, 0)
                .addPassiveEffect(PlayerLoreState.DEFAULT, PotionEffectType.WATER_BREATHING, 0)
                .addRecipe(recipe)
                .build();
    }

    public PlayerLore generateAureliusLore(){
        /* Aurelius Axe */
        ItemStack aureliusAxe = new ItemStack(Material.GOLDEN_AXE);
        /* Aurelius Axe build */
        LoreItem loreItem = new LoreItem.LoreItemBuilder(
                this,
                ChatColor.GOLD + "Aurelius Axe",
                "callippoosmp.lore.item.aurelius_axe",
                aureliusAxe
        )
                .setCustomModelDataComponentString("aurelius_axe")
                .addLore(ChatColor.RED + "Instantly breaks the hardest logs.")
                .addEnchant(Enchantment.EFFICIENCY, 10)
                .isUnbreakable()
                .build();
        /* Aurelius Axe Recipe */
        NamespacedKey namespacedKey = new NamespacedKey(this, "aurelius_axe");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, loreItem.itemStack);
        recipe.shape("GSG", "SAS", "GSG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.IRON_INGOT);
        recipe.setIngredient('A', Material.GOLDEN_AXE);
        Bukkit.addRecipe(recipe);
        /* Lore builder */
        return new PlayerLore.PlayerLoreBuilder(
                "aurelius",
                "Aurelius"
        )
                .addDescription("Een oude, wijze tovenaar, bekend om zijn rustige aard, zijn liefde voor de natuur,")
                .addDescription("vissen, houthakken en meditatie.")
                .addDescription("Hij geloofde dat magie moest dienen om het leven te verbeteren,")
                .addDescription("nooit om te vernietigen.")
                .addPassiveEffect(PlayerLoreState.DEFAULT, PotionEffectType.LUCK, 0)
                .addPassiveEffect(PlayerLoreState.DEFAULT, PotionEffectType.HERO_OF_THE_VILLAGE, 6)
                .addRecipe(recipe)
                .build();
    }

    public PlayerLore generateZyrothLore(){
        return new PlayerLore.PlayerLoreBuilder(
                "zyroth",
                "Zyroth"
        )
                .addDescription("Een jonge, excentrieke tovenaar die zichzelf The Game Master noemde.")
                .addDescription("Zyroth had een ontembare energie en organiseerde overal magische spellen en toernooien.")
                .build();
    }

    public PlayerLore generateWindchargerLore(){
        ItemStack windCharge = new ItemStack(Material.WIND_CHARGE);
        LoreItem loreItem = new LoreItem.LoreItemBuilder(
                this,
                ChatColor.GOLD  + "Infinity Charge",
                "callippoosmp.lore.item.infinity_charge",
                windCharge
        )
                .setCustomModelDataComponentString("infinity_charge")
                .setCustomModelDataComponentFloat(2f)
                .addLore(ChatColor.GRAY + "Gale Force II")
                .addLore(ChatColor.RED + "This windcharge does not deplete. Somehow.")
                .addEnchant(Enchantment.INFINITY, 1)
                .isUnbreakable()
                .build();
        NamespacedKey namespacedKey = new NamespacedKey(this, "infinity_charge");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, loreItem.itemStack);
        recipe.shape("GSG", "SAS", "GSG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.IRON_INGOT);
        recipe.setIngredient('A', Material.WIND_CHARGE);
        Bukkit.addRecipe(recipe);
        return new PlayerLore.PlayerLoreBuilder(
                "windcharger",
                "Windcharger"
        )
                .addDescription("This annoying guy spams windcharges.")
                .addRecipe(recipe)
                .build();
    }

    public PlayerLore generateUmbraniteLore(){
        return new PlayerLore.PlayerLoreBuilder(
                "umbranite",
                "Umbranite"
        ).build();
    }

    public PlayerLore generateBurlyBaluf(){
        CraftingRecipe recipe = this.getSpectralPickaxeRecipe();
        Bukkit.addRecipe(recipe);
        return new PlayerLore.PlayerLoreBuilder(
                "burly_baluf",
                "Burly Brobdingnian Baluf"
        )
                .addPlayerAttribute(PlayerLoreState.DEFAULT, Attribute.SCALE, 0.5f)
                .addPlayerAttribute(PlayerLoreState.DEFAULT, Attribute.MAX_HEALTH, 16f)
                .addPlayerAttribute(PlayerLoreState.DEFAULT, Attribute.BLOCK_BREAK_SPEED, 1.5f)
                .addRecipe(recipe)
                .build();
    }

    public CraftingRecipe getSpectralPickaxeRecipe(){
        ItemStack loreItemStack = new ItemStack(Material.IRON_PICKAXE);
        LoreItem loreItem = new LoreItem.LoreItemBuilder(
                this, ChatColor.GOLD + "Spectral Pickaxe",
                "", loreItemStack
        )
                .addEnchant(Enchantment.EFFICIENCY, 6)
                .addLore(ChatColor.GRAY + "Spectral Vision I")
                .addLore(ChatColor.RED + "Mining blocks magically repairs this pickaxe,")
                .addLore(ChatColor.RED + "unlocking secret abilities.")
                .setCustomModelDataComponentString("spectral_pickaxe")
                .setDurability(31)
                .setMaxDurability(32)
                .build();
        NamespacedKey namespacedKey = new NamespacedKey(this, "spectral_pickaxe");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, loreItem.itemStack);
        recipe.shape("GSG", "SAS", "GSG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.IRON_INGOT);
        recipe.setIngredient('A', Material.IRON_PICKAXE);
        return recipe;
    }

}
