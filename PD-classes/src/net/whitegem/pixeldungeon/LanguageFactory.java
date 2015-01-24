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
        System.out.println("t: " + originalText);
        String textTemp = originalText.replace("\n", "\\n");
        if (translator.hasKey(textTemp))
        {
            System.out.println("t haskey");
            return translator.translate(textTemp).replace("\\n", "\n");
        }
        if (translator.hasKey(textTemp.replaceAll("\\.$", "")))
        {
            System.out.println("t haskey.");
            return translator.translate(textTemp.replaceAll("\\.$", "")).replace("\\n", "\n") + ".";
        }
        return originalText;
    }

    public boolean hasKey(String key)
    {
        key = key.replace("\n", "\\n");
        return translator.hasKey(key) || translator.hasKey(key.replaceAll("\\.$", ""));
    }

    public String fixWrap(String inText)
    {
        return translator.fixWrap(inText);
    }

    public void addFormatTranslation(String formattedText, String format, Object...args)
    {
        System.out.println("Util.format >>> " + formattedText + " ||| " + format);
        for (int i = 0; i < args.length; i++)
        {
            if (args[i] instanceof String)
            {
                System.out.println(args[i]);
                if (stored.contains(args[i].toString().toLowerCase()))
                {
                    System.out.println("in stored");
                    args[i] = stored.get(args[i].toString().toLowerCase());
                }
                else if (stored.contains(args[i].toString().toLowerCase().replaceAll("\\.$", "")))
                {
                    System.out.println("in stored.");
                    args[i] = stored.get(args[i].toString().toLowerCase().replaceAll("\\.$", ""));
                }
                else if (hasKey(args[i].toString().toLowerCase()))
                {
                    System.out.println("haskey");
                    System.out.println(translate(args[i].toString()));
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
            System.out.println("store original");
            stored.put(formattedText.toLowerCase(), String.format(format, args));
        }
    }

    public String[] splitWords(String paragraph)
    {
        return translator.splitWords(paragraph);
    }
}
