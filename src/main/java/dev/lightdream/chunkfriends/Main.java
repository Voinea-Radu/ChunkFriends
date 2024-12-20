package dev.lightdream.chunkfriends;

import dev.lightdream.api.API;
import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.config.SQLConfig;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.chunkfriends.commands.BaseCommand;
import dev.lightdream.chunkfriends.files.config.Config;
import dev.lightdream.chunkfriends.files.config.Lang;
import dev.lightdream.chunkfriends.files.dto.FriendsGUIConfig;
import dev.lightdream.chunkfriends.managers.DatabaseManager;
import dev.lightdream.chunkfriends.managers.FriendsManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class Main extends LightDreamPlugin {


    public static Main instance;

    //Settings
    public Config config;
    public Lang lang;
    public FriendsGUIConfig friendsGUIConfig;

    //Managers
    public FriendsManager friendsManager;
public DatabaseManager databaseManager;


    @Override
    public void onEnable() {
        init("ChunkFriends", "cf", "1.0");
        instance = this;
        databaseManager = new DatabaseManager(this);
    }


    @Override
    public @NotNull String parsePapi(OfflinePlayer offlinePlayer, String s) {
        return "";
    }

    @Override
    public void loadConfigs() {
        sqlConfig = fileManager.load(SQLConfig.class);
        config = fileManager.load(Config.class);
        baseConfig = config;
        lang = fileManager.load(Lang.class, fileManager.getFile(baseConfig.baseLang));
        baseLang = lang;
        friendsGUIConfig = fileManager.load(FriendsGUIConfig.class);
    }

    @Override
    public void disable() {

    }

    @Override
    public void registerFileManagerModules() {

    }

    @Override
    public void loadBaseCommands() {
        baseCommands.add(new BaseCommand(this));
    }

    @Override
    public MessageManager instantiateMessageManager() {
        return new MessageManager(this, Main.class);
    }

    @Override
    public void registerLangManager() {
        API.instance.langManager.register(Main.class, getLangs());
    }

    @Override
    public HashMap<String, Object> getLangs() {
        HashMap<String, Object> langs = new HashMap<>();

        baseConfig.langs.forEach(lang -> {
            Lang l = fileManager.load(Lang.class, fileManager.getFile(lang));
            langs.put(lang, l);
        });

        return langs;
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void setLang(Player player, String s) {
        User user = databaseManager.getUser(player);
        user.setLang(s);
        databaseManager.save(user);
    }


}
