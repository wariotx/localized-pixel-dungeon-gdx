package net.whitegem.pixeldungeon;

import java.util.HashMap;

/**
 * Created by Carl-Station on 01/22/15.
 */
public class LanguageFactory
{
    public static final LanguageFactory INSTANCE;

    private Translator translator;
    public HashMap<String, String> stored = new HashMap<String, String>();

    public String language;

    static
    {
        INSTANCE = new LanguageFactory();
    }

    private LanguageFactory()
    {
        language = "chs";
        translator = new Translator(language);
    }

    public void setLanguage(String lang)
    {
        language = lang;
    }

    public String translate(String originalText)
    {
        originalText = originalText.replace("\n", "\\n");
        return translator.translate(originalText).replace("\\n", "\n");
    }

    public boolean hasKey(String key)
    {
        key = key.replace("\n", "\\n");
        return translator.hasKey(key);
    }

    public void addFormatTranslation(String formattedText, String format, Object[] args)
    {
        if (!stored.containsKey(formattedText))
        {
            if (hasKey(format))
            {
                for (int i = 0; i < args.length; i++)
                {
                    if (args[i] instanceof String && hasKey(args[i].toString()))
                    {
                        args[i] = translate(args[i].toString());
                    }
                }
                stored.put(formattedText.toLowerCase(), String.format(translate(format), args));
            }
        }
    }
}
