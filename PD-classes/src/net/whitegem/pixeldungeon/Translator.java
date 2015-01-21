package net.whitegem.pixeldungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Carl-Station on 01/18/15.
 */
public class Translator
{
    private HashMap<String, String> translation;

    public Translator(Language language)
    {
        translation = new HashMap<String, String>();
        if (language != Language.ENGLISH)
        {
            // THIS FILE MUST BE UTF8 WITHOUT BOM
            FileHandle file = Gdx.files.internal("translate/" + language + "/translation.txt");
            BufferedReader reader = new BufferedReader(file.reader());
            List<String> lines = new ArrayList<String>();
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
                translation.put(lines.get(i), lines.get(i + 1));
            }
        }
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
