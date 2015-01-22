package net.whitegem.pixeldungeon;

import com.watabou.noosa.BitmapText;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.utils.BitmapCache;

/**
 * Created by Carl-Station on 01/22/15.
 */
public class LanguageUtil
{
    public static void setLanguage(String code)
    {
        LanguageFactory.INSTANCE.setLanguage(code);

        // 3x5 (6)
        PixelScene.font1x = BitmapText.Font.colorMarked(
                BitmapCache.get("font1xc.png"), 0x00000000, BitmapText.Font.LATIN_FULL, "font1xc.fnt");
        PixelScene.font1x.baseLine = 7;
        PixelScene.font1x.tracking = -1;

        // 5x8 (10)
        PixelScene.font15x = BitmapText.Font.colorMarked(
                BitmapCache.get("font15xc.png"), 0x00000000, BitmapText.Font.LATIN_FULL, "font15xc.fnt");
        PixelScene.font15x.baseLine = 12;
        PixelScene.font15x.tracking = -1;

        // 6x10 (12)
        PixelScene.font2x = BitmapText.Font.colorMarked(
                BitmapCache.get("font2xc.png"), 0x00000000, BitmapText.Font.LATIN_FULL, "font2xc.fnt");
        PixelScene.font2x.baseLine = 15;
        PixelScene.font2x.tracking = -1;

        // 7x12 (15)
        PixelScene.font25x = BitmapText.Font.colorMarked(
                BitmapCache.get("font25xc.png"), 0x00000000, BitmapText.Font.LATIN_FULL, "font25xc.fnt");
        PixelScene.font25x.baseLine = 18;
        PixelScene.font25x.tracking = -1;

        // 9x15 (18)
        PixelScene.font3x = BitmapText.Font.colorMarked(
                BitmapCache.get("font3xc.png"), 0x00000000, BitmapText.Font.LATIN_FULL, "font3xc.fnt");
        PixelScene.font3x.baseLine = 22;
        PixelScene.font3x.tracking = -2;
    }
}
