package com.builtbroken.nvfix;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Applies patches to {@link net.minecraft.client.renderer.EntityRenderer} to change night vision brightness
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/5/2018.
 */
public class TransformerEntityRenderer implements IClassTransformer
{
    public static final String TARGET_CLASS = "net.minecraft.client.renderer.EntityRenderer";
    public static final String TARGET_METHOD = "getNightVisionBrightness";
    public static final String TARGET_METHOD_2 = "func_82830_a";

    public static final String STATIC_VALUE_CLASS = "com/builtbroken/nvfix/NVFixCoreMod";
    public static final String STATIC_VALUE_FIELD = "NV_BRIGHTNESS";

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        if (transformedName.trim().endsWith("EntityRenderer"))
        {
            System.out.println("NVFixMod: Applying patch to class>>" + transformedName);
            ClassNode cnode = createClassNode(bytes);

            System.out.println("NVFixMod: searching for method with name of '" + TARGET_METHOD + "' or ' " + TARGET_METHOD_2 + "'");
            for (MethodNode method : cnode.methods)
            {
                final String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(cnode.name, method.name, method.desc);
                if ((methodName.equalsIgnoreCase(TARGET_METHOD) || methodName.equalsIgnoreCase(TARGET_METHOD_2)) && method.desc.endsWith("F)F"))
                {
                    System.out.println("NVFixMod: Found method, Applying patch to method>> " + method.name + method.desc);
                    //Remove previous code
                    method.instructions.clear();

                    //Insert new code
                    method.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, STATIC_VALUE_CLASS, STATIC_VALUE_FIELD, "F"));
                    method.instructions.add(new InsnNode(Opcodes.FRETURN));

                    return createBytes(cnode);
                }
            }
        }
        return bytes;
    }

    public static ClassNode createClassNode(byte[] bytes)
    {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(cnode, 0); //TODO what is this flag
        return cnode;
    }

    public static byte[] createBytes(ClassNode cnode)
    {
        ClassWriter cw = new ClassWriter(0); //TODO what is this flag
        cnode.accept(cw);
        return cw.toByteArray();
    }
}