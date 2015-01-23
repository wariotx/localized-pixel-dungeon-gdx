package net.whitegem.pixeldungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * Created by Carl-Station on 01/22/15.
 */
public class Translator
{
    private String language;
    private LinkedHashMap<String, String> translation;

    public Translator(String language)
    {
        this.language = language;
        translation = new LinkedHashMap<String, String>();
        // THIS FILE MUST BE UTF8 WITHOUT BOM
        FileHandle file = Gdx.files.internal("translation/" + language + ".txt");
        BufferedReader reader = new BufferedReader(file.reader());
        ArrayList<String> lines = new ArrayList<String>();
        try
        {
            String line = reader.readLine();
            while (line != null)
            {
                if (!line.trim().equals("") && !line.trim().startsWith("#"))
                {
                    lines.add(line.trim());
                }
                line = reader.readLine();
            }
        } catch (IOException ioe)
        {
        }

        if (lines.size() % 2 != 0)
        {
            lines.add("error: please check translation.txt");
        }

        for (int i = 0; i < lines.size(); i += 2)
        {
            String trans = lines.get(i + 1);
            translation.put(lines.get(i), trans);
        }
    }

    public String fixWrap(String trans)
    {

        ArrayList<String> transAfter = new ArrayList<String>();
        int start = 0;
        int end = 1;
        while (start != trans.length())
        {
            String str = trans.substring(start, end);
            if (str.length() == 1 && (isChinese(str.charAt(0)) || str.equals(" ")))
            {
                transAfter.add(str);
                start++;
                end++;
            }
            else
            {
                if (end == trans.length())
                {
                    transAfter.add(str.trim());
                    start = end;
                    end++;
                }
                else
                {
                    String endStr = trans.substring(end - 1, end);
                    String endStrNext = trans.substring(end, end + 1);
                    if (endStr.equals(" ") || (endStr.matches("\\p{P}") && !endStrNext.matches("[0-9]")) || (endStr.matches("[0-9]") && !endStrNext.matches("[0-9]")))
                    {
                        transAfter.add(str.trim());
                        start = end;
                    }
                    end++;
                }
            }
        }
        int c = 0;
        while (c < transAfter.size() - 1)
        {
            String next = transAfter.get(c + 1);
            String current = transAfter.get(c);
            String result = current;

            if (current.length() == 1 && isChinese(current.charAt(0))) // chinese
            {
                if (!isChinese(next.charAt(0)) && ! next.matches("\\p{P}"))
                {
                    result += " ";
                }
            }
            else if (current.matches("\\p{P}"))
            {

                if (current.matches("[,.!?;:，。！？；：]"))
                {
                    result += " ";
                }
            }
            else
            {
                result += " ";
            }

            transAfter.set(c, result);
            c++;
        }
        String finalTrans = "";
        for (String s : transAfter)
            finalTrans += s;

        return finalTrans;
    }

    private static boolean isChinese(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
        {
            return true;
        }
        return false;
    }

    public String[] splitWords(String paragraph)
    {
        if (language.equals("en"))
        {
            String[] pre = Pattern.compile("\\s+").split(paragraph);
            ArrayList<String> tmp = new ArrayList<String>();
            for (String s : pre)
            {
                tmp.add(s);
                tmp.add(" ");
            }
            return tmp.toArray(new String[tmp.size()]);
        }

        ArrayList<String> words = new ArrayList<String>();
        int start = 0;
        int end = 1;
        while (start != paragraph.length())
        {
            String str = paragraph.substring(start, end);
            if (str.length() == 1 && (isChinese(str.charAt(0)) || str.equals(" ")))
            {
                words.add(str);
                start++;
                end++;
            }
            else
            {
                if (end == paragraph.length())
                {
                    words.add(str.trim());
                    start = end;
                    end++;
                }
                else
                {
                    String endStr = paragraph.substring(end - 1, end);
                    String endStrNext = paragraph.substring(end, end + 1);
                    if (endStr.equals(" ") || (endStr.matches("\\p{P}") && !endStrNext.matches("[0-9]")) || (endStr.matches("[0-9]") && !endStrNext.matches("[0-9]")))
                    {
                        words.add(str);
                        start = end;
                    }
                    end++;
                }
            }
        }
        return words.toArray(new String[words.size()]);
        // return Pattern.compile("\\s+").split(paragraph);
    }

    public boolean hasKey(String key)
    {
        return translation.containsKey(key);
    }

    public String translate(String originalText)
    {
        if (hasKey(originalText))
        {
            return translation.get(originalText);
        }
        return originalText;
    }
}
