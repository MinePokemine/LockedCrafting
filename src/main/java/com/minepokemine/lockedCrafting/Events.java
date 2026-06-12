package com.minepokemine.lockedCrafting;

import net.kyori.adventure.text.Component;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Events implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    void onCraft(PrepareItemCraftEvent event) {
        Recipe rec = event.getRecipe();
        NamespacedKey key;
        if (!(rec instanceof Keyed)) { /*LockedCrafting.instance.getLogger().info("Could not get recipe key");*/ return;}

        key = ((Keyed)rec).getKey();
        LockedCrafting.instance.getLogger().info("Crafting recipe " + key.asString());

        List<String> reqs = new ArrayList<>();

        if (key.namespace().equals("minecraft")) {
            reqs.addAll(LockedCrafting.instance.getConfig().getStringList("recipes." + key.getKey() + ".itemReqs"));
        }
        reqs.addAll(LockedCrafting.instance.getConfig().getStringList("recipes." + key.getNamespace() + "." + key.getKey() + ".itemReqs"));

        List<HumanEntity> plrs = event.getViewers();

        List<String> reqsContained = new ArrayList<>();
        List<String> reqsNotContained = new ArrayList<>();
        for (String req : reqs) {
            boolean reqContained = false;
            for (HumanEntity plr : plrs) {
                try {
                    if (plr.getInventory().contains(ItemStack.of(Registry.MATERIAL.get(new NamespacedKey(req.split(":")[0], req.split(":")[1]))))) {
                        reqContained = true;
                    }
                }
                catch (NullPointerException e) {
                    LockedCrafting.instance.getLogger().warning("Could not find item " + req + " (From the recipe " + key.toString() + ")");
                    reqContained = true;
                    break;
                }
            }
            if (reqContained) reqsContained.add(req);
            else reqsNotContained.add(req);
        }

        if (!reqsNotContained.isEmpty()) {
            for (HumanEntity plr : plrs) {
                plr.sendMessage(LockedCrafting.instance.getConfig().getString("messages.missingReqs.pre"));
                if (LockedCrafting.instance.getConfig().getBoolean("messages.missingReqs.showMissing")) {
                    for (String req : reqsNotContained) {
                        Material mat = Registry.MATERIAL.get(new NamespacedKey(req.split(":")[0], req.split(":")[1]));
                        plr.sendMessage(Component.translatable(mat.translationKey()));
                    }
                }
                plr.sendMessage(LockedCrafting.instance.getConfig().getString("messages.missingReqs.between"));
                if (LockedCrafting.instance.getConfig().getBoolean("messages.missingReqs.showThere")) {
                    for (String req : reqsContained) {
                        try {
                            Material mat = Registry.MATERIAL.get(new NamespacedKey(req.split(":")[0], req.split(":")[1]));
                            plr.sendMessage(Component.translatable(mat.translationKey()));
                        }
                        catch (NullPointerException e) {
                            plr.sendMessage(req);
                        }
                    }
                }
                event.getInventory().setResult(new ItemStack(Material.AIR));
            }
            LockedCrafting.instance.getLogger().info("Recipe success");
        }
    }

    @EventHandler
    void onCrafter(CrafterCraftEvent event) {
        CraftingRecipe rec = event.getRecipe();
        NamespacedKey key;
        key = rec.getKey();
        LockedCrafting.instance.getLogger().info("Crafter crafting recipe " + key.asString());

        List<String> reqs = new ArrayList<>();

        if (key.namespace().equals("minecraft")) {
            reqs.addAll(LockedCrafting.instance.getConfig().getStringList("recipes." + key.getKey() + ".itemReqs"));
        }
        reqs.addAll(LockedCrafting.instance.getConfig().getStringList("recipes." + key.getNamespace() + "." + key.getKey() + ".itemReqs"));

        if (reqs.size() != 0) {
            event.setCancelled(true);
        }
    }
}
