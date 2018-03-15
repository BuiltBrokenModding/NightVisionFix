package com.builtbroken.nvfix;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/5/2018.
 */
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.Name("NVFixCoreMod")
@IFMLLoadingPlugin.TransformerExclusions("com.builtbroken.nvfix")
public class NVFixCoreMod implements IFMLLoadingPlugin
{
    /** Controls default value for NV brightness, injected into MC via ASM so do not move or change */
    public static float NV_BRIGHTNESS = 1f;

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{"com.builtbroken.nvfix.TransformerEntityRenderer"};
    }

    @Override
    public String getModContainerClass()
    {
        return "com.builtbroken.nvfix.NVFixMod";
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
