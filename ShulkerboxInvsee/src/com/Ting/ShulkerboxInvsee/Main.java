package com.Ting.ShulkerboxInvsee;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.block.ShulkerBox;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.meowj.langutils.lang.LanguageHelper;

public class Main extends JavaPlugin {
    static Main instance;
    static FileConfiguration dataconfig;
    static File datac;
    static ProtocolManager protocolManager;

    @Override
    public void onEnable(){
        instance = this;
        this.loadConfig();
        this.createdataConfig();
        save();
        protocolManager = ProtocolLibrary.getProtocolManager();
        this.fakelore();
        this.getLogger().info("SulkerboxInvsee loaded");

    }

    public static Main getInstance() {
        return instance;
    }

    public static ProtocolManager getprotocolManager() {
        return protocolManager;
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
    }

    private void createdataConfig() {
        datac = new File(this.getDataFolder(), "data.yml");
        if (!datac.exists()) {
            datac.getParentFile().mkdirs();
            this.saveResource("data.yml", false);
        }

        dataconfig = new YamlConfiguration();

        try {
            dataconfig.load(datac);
        } catch (IOException | InvalidConfigurationException var2) {
            var2.printStackTrace();
        }

    }

    public static void save() {
        try {
            dataconfig.save(datac);
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }

    public void fakelore() {
        protocolManager.addPacketListener(new PacketAdapter(getInstance(), ListenerPriority.NORMAL, new PacketType[]{Server.SET_SLOT}) {
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == Server.SET_SLOT) {
                    PacketContainer packet = event.getPacket().deepClone();
                    StructureModifier<ItemStack> sm = packet.getItemModifier();

                    for(int j = 0; j < sm.size(); ++j) {
                        if (sm.getValues().get(j) != null) {
                            ItemStack item = (ItemStack)sm.getValues().get(j);
                            List<String> oldLore = new ArrayList();
                            if (item.hasItemMeta()) {
                                oldLore = item.getItemMeta().getLore();
                            }

                            List<String> lorelist = new ArrayList();
                            if (item.getType().name().toLowerCase().contains("shulker_box") && item.getItemMeta() instanceof BlockStateMeta) {
                                BlockStateMeta im = (BlockStateMeta)item.getItemMeta();
                                if (Main.this.getConfig().getBoolean("shulkerbox-invsee")) {
                                    if (im.getBlockState() instanceof ShulkerBox) {
                                        ShulkerBox shulker = (ShulkerBox)im.getBlockState();
                                        int counter = 0;

                                        int ii;
                                        for(ii = 0; ii <= shulker.getInventory().getSize() - 1; ++ii) {
                                            if (shulker.getInventory().getItem(ii) != null && !shulker.getInventory().getItem(ii).getType().equals(Material.AIR)) {
                                                ++counter;
                                                if (counter > 5) {
                                                    String key;
                                                    if (!shulker.getInventory().getItem(ii).getItemMeta().getDisplayName().equals("")) {
                                                        key = LanguageHelper.getItemDisplayName(shulker.getInventory().getItem(ii), "zh_tw") + " x" + shulker.getInventory().getItem(ii).getAmount();
                                                    } else if (!shulker.getInventory().getItem(ii).getItemMeta().getLocalizedName().equals("")) {
                                                        key = LanguageHelper.getItemName(shulker.getInventory().getItem(ii), "zh_tw") + " x" + shulker.getInventory().getItem(ii).getAmount();
                                                    } else {
                                                        shulker.getInventory().getItem(ii).getItemMeta().setLocalizedName(shulker.getInventory().getItem(ii).getType().name());
                                                        key = LanguageHelper.getItemName(shulker.getInventory().getItem(ii), "zh_tw") + " x" + shulker.getInventory().getItem(ii).getAmount();
                                                    }

                                                    if (counter <= 11) {
                                                        lorelist.add(key);
                                                    } else if (counter <= 17) {
                                                        lorelist.set(counter - 12, (String)lorelist.get(counter - 12) + " " + key);
                                                    } else if (counter <= 22) {
                                                        lorelist.set(counter - 18, (String)lorelist.get(counter - 18) + " " + key);
                                                    } else {
                                                        lorelist.set(counter - 23, (String)lorelist.get(counter - 23) + " " + key);
                                                    }
                                                }
                                            }
                                        }

                                        if (oldLore != null) {
                                            for(ii = ((List)oldLore).size() - 1; ii >= 0; --ii) {
                                                if (((String)((List)oldLore).get(ii)).contains(" x")) {
                                                    ((List)oldLore).remove(ii);
                                                }
                                            }
                                        }
                                    }

                                    if (oldLore != null) {
                                        if (((List)oldLore).size() != 0) {
                                            ((List)oldLore).addAll(lorelist);
                                            im.setLore((List)oldLore);
                                        }
                                    } else {
                                        im.setLore(lorelist);
                                    }

                                    item.setItemMeta(im);
                                } else if (oldLore != null) {
                                    for(int io = ((List)oldLore).size() - 1; io >= 0; --io) {
                                        if (((String)((List)oldLore).get(io)).contains(" x")) {
                                            ((List)oldLore).remove(io);
                                        }
                                    }

                                    im.setLore((List)oldLore);
                                    item.setItemMeta(im);
                                }
                            }
                        }
                    }

                    event.setPacket(packet);
                }

            }
        });
    }

}
