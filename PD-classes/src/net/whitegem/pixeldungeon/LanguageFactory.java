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
        System.out.println("Translating >>> " + originalText);
        String textTemp = originalText.replace("\n", "\\n").trim().toLowerCase();
        if (translator.hasKey(textTemp))
        {
            return translator.translate(textTemp).replace("\\n", "\n");
        }
        if (translator.hasKey(textTemp.replaceAll("\\.$", "")))
        {
            return translator.translate(textTemp.replaceAll("\\.$", "")).replace("\\n", "\n") + ".";
        }
        return originalText;
    }

    public boolean hasKey(String key)
    {
        key = key.trim().replace("\n", "\\n");
        return translator.hasKey(key) || translator.hasKey(key.replaceAll("\\.$", ""));
    }

    public String fixWrap(String inText)
    {
        return translator.fixWrap(inText);
    }

    public void addFormatTranslation(String formattedText, String format, Object...args)
    {
        System.out.println("Utils.format >>> " + formattedText + " ||| " + format + " ||| " + args);
        for (int i = 0; i < args.length; i++)
        {
            if (args[i] instanceof String)
            {
                System.out.println(args[i]);
                if (stored.contains(args[i].toString().toLowerCase()))
                {
                    args[i] = stored.get(args[i].toString().toLowerCase());
                }
                else if (stored.contains(args[i].toString().toLowerCase().replaceAll("\\.$", "")))
                {
                    args[i] = stored.get(args[i].toString().toLowerCase().replaceAll("\\.$", ""));
                }
                else if (hasKey(args[i].toString().trim().toLowerCase()))
                {
                    System.out.println(translate(args[i].toString()));
                    args[i] = translate(args[i].toString().trim());
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

    public static String getTranslation(String text)
    {
        System.out.println("Getting translation for >>> " + text);
        if (!INSTANCE.language.equals("en"))
        {
            String s = null;
            if (text != null && INSTANCE.stored.contains(text.toLowerCase()))
            {
                s = INSTANCE.stored.get(text.toLowerCase());
            }
            else if (text != null && INSTANCE.stored.contains(text.toLowerCase().replaceAll("\\.$", "")))
            {
                s = INSTANCE.stored.get(text.toLowerCase().replaceAll("\\.$", "")) + ".";
            }
            if (text != null)
            {
                text = (s == null) ? INSTANCE.translate(text) : s;
                text = INSTANCE.fixWrap(text);
            }
        }
        return text;
    }
}
