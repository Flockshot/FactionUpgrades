package me.flockshot.factionupgrades.utils.filesystem.files;

import java.io.File;

import me.flockshot.factionupgrades.utils.files.identifier.MessageIdentifier;
import me.flockshot.factionupgrades.utils.filesystem.Language;
import me.flockshot.factionupgrades.utils.filesystem.UnknownLanguage;

public class English extends UnknownLanguage
{    
    public English(File file) {
        super(file);
        setLanguage(Language.EN);
    }


    @Override
    public void setDefaults() {

        addDefault(MessageIdentifier.TNT_BANK_FULL, "&4TNT Bank is full");
        addDefault(MessageIdentifier.SIPHON_MAX_LEVEL, "&4You need to upgrade tnt-bank to max level to use siphon");
        addDefault(MessageIdentifier.PLAYER_LIMIT, "&4Player limit reached");
        addDefault(MessageIdentifier.WARP_LIMIT, "&4SetWarps limit reached");
        addDefault(MessageIdentifier.NO_MONEY, "&6 You don't have enough tokens");
        addDefault(MessageIdentifier.MAX_LEVEL, "&4 Reached Max Level");
        addDefault(MessageIdentifier.NO_FACTION, "&6 You are not in a faction");
    }







    
    
    
    
    
}
