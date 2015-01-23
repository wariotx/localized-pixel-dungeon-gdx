package net.whitegem.pixeldungeon;

/**
 * Created by Carl-Station on 01/22/15.
 */
public class LanguageFactory
{
    public static final LanguageFactory INSTANCE;

    private Translator translator;
    public StoredFormat stored = new StoredFormat();

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

    public String fixWrap(String inText)
    {
        return translator.fixWrap(inText);
    }

    public void addFormatTranslation(String formattedText, String format, Object...args)
    {
        for (int i = 0; i < args.length; i++)
        {
            if (args[i] instanceof String)
            {
                if (stored.contains(args[i].toString().toLowerCase()))
                {
                    args[i] = stored.get(args[i].toString().toLowerCase());
                }
                else if (hasKey(args[i].toString()))
                {
                    args[i] = translate(args[i].toString());
                }
            }
        }
        if (hasKey(format))
        {
            stored.put(formattedText.toLowerCase(), String.format(translate(format), args));
        }
        else
        {
            stored.put(formattedText.toLowerCase(), String.format(format, args));
        }
    }

    public String[] splitWords(String paragraph)
    {
        return translator.splitWords(paragraph);
    }
}
