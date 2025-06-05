package com.callippoonet.callippoosmp;
import com.callippoonet.callippoosmp.commands.PlayerLoreCommands;
import com.callippoonet.callippoosmp.commands.tabcompleters.PlayerLoreCommandsTabCompleters;
import com.callippoonet.callippoosmp.events.*;
import com.callippoonet.callippoosmp.lore.LoreItem;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;



public class Main extends JavaPlugin {

    PlayerLoreRegister playerLoreRegister;

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
        getServer().getPluginManager().registerEvents(new RespawnEventListener(playerLoreRegister), this);
        getServer().getPluginManager().registerEvents(new CraftingEventListener(playerLoreRegister), this);
        getServer().getPluginManager().registerEvents(new CrafterCraftEventListener(playerLoreRegister), this);
        getServer().getPluginManager().registerEvents(new ProjectileEventListener(), this);
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
        return playerLoreRegister;
    }

    public PlayerLore generateDwarfPlayerLore(){
        return new PlayerLore.PlayerLoreBuilder(
                "dwarf",
                "callippoosmp.lore.dwarf",
                "Dwarf"
        )
                .addDescription("&cThis little guy fits through every gap, and mines faster than your sight can register.")
                .addPlayerAttribute(Attribute.SCALE, 0.5f)
                .addPlayerAttribute(Attribute.BLOCK_BREAK_SPEED, 1.5f)
                .addPlayerAttribute(Attribute.MAX_HEALTH, 16f)
                .build();
    }

    public PlayerLore generateGlibboLore(){
        return new PlayerLore.PlayerLoreBuilder(
                "glibbo",
                "callippoosmp.lore.glibbo",
                "Glibbo"
        )
                .addDescription("Glibbo Waterfrats is een wat zonderlinge, maar goedbedoelende watermagiër die zich goed voelt in de diepste,")
                .addDescription("meest vergeten ondergrondse koraalrifgrotten van Minecraftia.")
                .addPassiveEffect(PotionEffectType.DOLPHINS_GRACE, 1)
                .addPassiveEffect(PotionEffectType.WATER_BREATHING, 1)
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
                "callippoosmp.lore.aurelius",
                "Aurelius"
        )
                .addDescription("Een oude, wijze tovenaar, bekend om zijn rustige aard, zijn liefde voor de natuur,")
                .addDescription("vissen, houthakken en meditatie.")
                .addDescription("Hij geloofde dat magie moest dienen om het leven te verbeteren,")
                .addDescription("nooit om te vernietigen.")
                .addPassiveEffect(PotionEffectType.LUCK, 1)
                .addPassiveEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 7)
                .addRecipe(recipe)
                .build();
    }

    public PlayerLore generateZyrothLore(){
        return new PlayerLore.PlayerLoreBuilder(
                "zyroth",
                "callippoosmp.lore.zyroth",
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
                "callippoosmp.lore.windcharger",
                "Windcharger"
        )
                .addDescription("This annoying guy spams windcharges.")
                .build();
    }

    public PlayerLore generateUmbraniteLore(){
        return new PlayerLore.PlayerLoreBuilder(
                "umbranite",
                "callippoosmp.lore.umbranite",
                "Umbranite"
        ).build();
    }
}
