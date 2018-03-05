package com.builtbroken.nvfix;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

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
        if (transformedName.equals(TARGET_CLASS))
        {
            ClassNode cnode = createClassNode(bytes);

            for (MethodNode method : cnode.methods)
            {
                if (method.name.equalsIgnoreCase(TARGET_METHOD) || method.name.equalsIgnoreCase(TARGET_METHOD_2))
                {
                    //Debug
                    System.out.println("Before:");
                    for (AbstractInsnNode insnNode : method.instructions.toArray())
                    {
                        System.out.println("\t" + insnNode.getOpcode() + "  ->>  " + insnNode);
                    }

                    //Remove previous code
                    method.instructions.clear();

                    //Insert new code
                    method.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, STATIC_VALUE_CLASS, STATIC_VALUE_FIELD, "F"));
                    method.instructions.add(new InsnNode(Opcodes.FRETURN));

                    //Debug
                    System.out.println("After:");
                    for (AbstractInsnNode insnNode : method.instructions.toArray())
                    {
                        System.out.println("\t" + insnNode.getOpcode() + "  ->>  " + insnNode);
                    }
                }
            }
            return createBytes(cnode);
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