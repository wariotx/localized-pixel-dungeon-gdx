package net.whitegem.pixeldungeon;

/**
 * Created by Carl-Station on 01/18/15.
 */
public class Translator
{
    private Translator(){}

    public static boolean hasKey(String key, Language language)
    {
        if (key.equals("Play") || key.equals("Rankings"))
        {
            return true;
        }
        return false;
    }

    public static String translate(String originalText, Language language)
    {
        if (originalText.equals("Play"))
        {
            return "玩耍";
        }
        if (originalText.equals("Rankings"))
        {
            return "統計";
        }
        return originalText;
    }
}
