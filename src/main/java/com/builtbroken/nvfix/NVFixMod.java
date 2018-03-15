package com.builtbroken.nvfix;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/5/2018.
 */
public class NVFixMod extends DummyModContainer
{
    public static final String version = "0.0.1";
    private static final ModMetadata md;

    static
    {
        md = new ModMetadata();
        md.modId = "nvfix";
        md.name = "Night Vision Fix";
        md.description = "Fixes issues with Night Vision brightness and flashing during decay";
        md.version = version;
    }

    public NVFixMod()
    {
        super(md);
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        return true;
    }
}
