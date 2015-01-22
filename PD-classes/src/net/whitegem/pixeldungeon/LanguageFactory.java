package net.whitegem.pixeldungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.HashMap;

/**
 * Created by Carl-Station on 01/18/15.
 */
public class LanguageFactory
{
    public static final LanguageFactory INSTANCE;
    private HashMap<String, BitmapFont> fonts;
    public static final ShaderProgram shaderPixel;
    public static final ShaderProgram shaderAA;
    private Translator translator;
    public HashMap<String, String> stored = new HashMap<String, String>();

    private Language language;

    static
    {
        INSTANCE = new LanguageFactory();
        shaderPixel = new ShaderProgram(Gdx.files.internal("translate/shader/outline.vert"), Gdx.files.internal("translate/shader/outline.frag"));
        shaderAA = new ShaderProgram(Gdx.files.internal("translate/shader/outlineAA.vert"), Gdx.files.internal("translate/shader/outlineAA.frag"));
    }

    private LanguageFactory()
    {
        translator = new Translator(Language.ENGLISH);
        fonts = new HashMap<String, BitmapFont>();
        setLanguage(Language.ENGLISH);
    }

    public void setLanguage(Language lang)
    {
        language = lang;
        translator = new Translator(language);
        fonts.clear();
        if (language != Language.ENGLISH)
        {
            BitmapFont font1x = FontFactory.generate("translate/" + language + "/font/1x");
            font1x.setMarkupEnabled(true);
            BitmapFont font15x = FontFactory.generate("translate/" + language + "/font/15x");
            font15x.setMarkupEnabled(true);
            BitmapFont font2x = FontFactory.generate("translate/" + language + "/font/2x");
            font2x.setMarkupEnabled(true);
            BitmapFont font25x = FontFactory.generate("translate/" + language + "/font/25x");
            font25x.setMarkupEnabled(true);
            BitmapFont font3x = FontFactory.generate("translate/" + language + "/font/3x");
            font3x.setMarkupEnabled(true);
            fonts.put("1x", font1x);
            fonts.put("15x", font15x);
            fonts.put("2x", font2x);
            fonts.put("25x", font25x);
            fonts.put("3x", font3x);
        }
    }

    public BitmapFont getFont(String zoom)
    {
        if (fonts.containsKey(zoom))
            return fonts.get(zoom);
        return fonts.get("1x");
    }

    public String translate(String originalText)
    {
        originalText = originalText.replace("\n", "\\n");
        if (language == Language.ENGLISH)
            return originalText;
        return translator.translate(originalText).replace("\\n", "\n");
    }

    public boolean hasKey(String key)
    {
        key = key.replace("\n", "\\n");
        if (language == Language.ENGLISH)
            return false;
        return translator.hasKey(key);
    }

    public void addFormatTranslation(String formattedText, String format, Object[] args)
    {
        if (!stored.containsKey(formattedText))
        {
            if (hasKey(format))
            {
                stored.put(formattedText, String.format(translate(format), args));
            }
        }
    }
}
