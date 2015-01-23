package net.whitegem.pixeldungeon;

import com.watabou.pixeldungeon.scenes.TitleScene;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Carl-Station on 01/23/15.
 */
public class HardTranslator
{
    public static void run()
    {
        System.out.println("T");
        try
        {
            set(TitleScene.class.getDeclaredField("TXT_PLAY"), "=_=");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        new TitleScene();
    }

    private static void set(Field field, Object newValue) throws Exception
    {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}
