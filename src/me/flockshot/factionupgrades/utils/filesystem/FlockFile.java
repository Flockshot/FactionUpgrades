package me.flockshot.factionupgrades.utils.filesystem;

import java.io.File;

public interface FlockFile extends FlockConfig
{
    public void setFile(File file);
    public File getFile();

}
