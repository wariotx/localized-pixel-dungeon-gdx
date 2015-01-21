package net.whitegem.pixeldungeon;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;

/**
 * Created by Carl-Station on 01/18/15.
 */
public class FontFactory
{
    public static BitmapFont generate(String fontPath)
    {
        FileHandle textureDir = Gdx.files.internal(fontPath);
        TextureRegion[] textureRegions = new TextureRegion[textureDir.list().length - 1];
        int textureCounter = 0;
        for (FileHandle file : textureDir.list())
        {
            if (file.extension().equals("png"))
            {
                Texture fontTexture = new Texture(file, true);
                //fontTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
                fontTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
                textureRegions[textureCounter] = new TextureRegion(fontTexture);
                textureCounter++;
            }
        }
        FileHandle fontFile = Gdx.files.internal(fontPath + "/font.fnt");
        return new BitmapFont(new BitmapFontData(fontFile, false), textureRegions, false);
    }
}
