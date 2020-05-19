package me.flockshot.factionupgrades.utils.filesystem.files;

import java.io.File;

import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.utils.filesystem.UnknownFile;

public class FactionStorageFile extends UnknownFile
{

    public FactionStorageFile(File file) {
        super(file);
    }
    
    @Override
    public void setDefaults() {
    }

    
    public void writeToFile(FactionStorage fac)
    {
        if(fac!=null)
        {
            final String path = "faction.";
            
            setLine(path+"id", fac.getFactionID());
            
            for(String upgradeName : fac.getUpgrades().keySet())
                setLine(path+"upgrades."+upgradeName, fac.getUpgrades().get(upgradeName));

            save();
        }
    }
    
    


    
    
}
