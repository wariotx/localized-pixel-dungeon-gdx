package net.whitegem.pixeldungeon;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Carl-Station on 01/18/15.
 */
public class LanguageFactory
{
    public static final LanguageFactory INSTANCE;
    public BitmapFont font1x;

    private Language language;

    static
    {
        INSTANCE = new LanguageFactory();
    }

    private LanguageFactory()
    {
        setLanguage(Language.ENGLISH);
    }

    public void setLanguage(Language lang)
    {
        language = lang;
        font1x = FontFactory.generate("translate/chs/font/6");
        font1x.setMarkupEnabled(true);
    }

    public String translate(String originalText)
    {
        return Translator.translate(originalText, language);
    }

    public boolean hasKey(String key)
    {
        return Translator.hasKey(key, language);
    }
}
