/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.items.weapon.melee;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.weapon.Weapon;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.utils.Random;
import net.whitegem.pixeldungeon.LanguageFactory;

public class MeleeWeapon extends Weapon {

	private int tier;

	public MeleeWeapon( int tier, float acu, float dly ) {
		super();

		this.tier = tier;

		ACU = acu;
		DLY = dly;

		STR = typicalSTR();

		MIN = min();
		MAX = max();
	}

	private int min() {
		return tier;
	}

	private int max() {
		return (int)((tier * tier - tier + 10) / ACU * DLY);
	}

	@Override
	public Item upgrade() {
		return upgrade( false );
	}

	public Item upgrade( boolean enchant ) {
		STR--;
		MIN++;
		MAX += tier;

		return super.upgrade( enchant );
	}

	public Item safeUpgrade() {
		return upgrade( enchantment != null );
	}

	@Override
	public Item degrade() {
		STR++;
		MIN--;
		MAX -= tier;
		return super.degrade();
	}

	public int typicalSTR() {
		return 8 + tier * 2;
	}

	@Override
	public String info() {

		final String p = "\n\n";

		StringBuilder info = new StringBuilder(LanguageFactory.getTranslation(desc()) );

		String quality = levelKnown && level != 0 ? (level > 0 ? "upgraded" : "degraded") : "";
		info.append( p );
		info.append( Utils.format("This %s is %s tier-%d melee weapon. ", name, Utils.indefinite( quality ), tier) );

		if (levelKnown) {
			info.append( Utils.format("Its average damage is %d points per hit. ", (MIN + (MAX - MIN) / 2)) );
		} else {
			info.append(
					Utils.format("Its typical average damage is %d points per hit " +
							"and usually it requires %d points of strength. ", (min() + (max() - min()) / 2), typicalSTR()) );
			if (typicalSTR() > Dungeon.hero.STR()) {
				info.append( LanguageFactory.getTranslation("Probably this weapon is too heavy for you. ") );
			}
		}

		if (DLY != 1f) {
			if (ACU != 1f) {
				if (DLY < 1f)
				{
					if (ACU > 1f)
					{
						info.append(LanguageFactory.getTranslation("This is a rather fast and accurate weapon. "));
					}
					else
					{
						info.append(LanguageFactory.getTranslation("This is a rather fast but inaccurate weapon. "));
					}
				}
				else
				{
					if (ACU > 1f)
					{
						info.append(LanguageFactory.getTranslation("This is a rather slow but accurate weapon. "));
					}
					else
					{
						info.append(LanguageFactory.getTranslation("This is a rather slow and inaccurate weapon. "));
					}
				}
			}
			else
			{
				if (DLY < 1f)
				{
					info.append(LanguageFactory.getTranslation("This is a rather fast weapon. "));
				}
				else
				{
					info.append(LanguageFactory.getTranslation("This is a rather slow weapon. "));
				}
			}
		} else if (ACU != 1f) {
			info.append( LanguageFactory.getTranslation("This is a rather " + (ACU > 1f ? "accurate" : "inaccurate") + " weapon. ") );
		}
		switch (imbue) {
			case SPEED:
				info.append( LanguageFactory.getTranslation("It was balanced to make it faster. ") );
				break;
			case ACCURACY:
				info.append( LanguageFactory.getTranslation("It was balanced to make it more accurate. ") );
				break;
			case NONE:
		}

		if (enchantment != null) {
			info.append( LanguageFactory.getTranslation("It is enchanted.") );
		}

		if (levelKnown && Dungeon.hero.belongings.backpack.items.contains( this )) {
			if (STR > Dungeon.hero.STR()) {
				info.append( p );
				info.append(
						Utils.format("Because of your inadequate strength the accuracy and speed " +
								"of your attack with this %s is decreased.", name) );
			}
			if (STR < Dungeon.hero.STR()) {
				info.append( p );
				info.append(
						Utils.format("Because of your excess strength the damage " +
								"of your attack with this %s is increased.", name) );
			}
		}

		if (isEquipped( Dungeon.hero )) {
			info.append( p );
			if (cursed)
			{
				info.append( Utils.format("You hold the %s at the ready, and because it is cursed, you are powerless to let go.", name));
			}
			else
			{
				info.append( Utils.format("You hold the %s at the ready.", name));
			}
		} else {
			if (cursedKnown && cursed) {
				info.append( p );
				info.append( Utils.format("You can feel a malevolent magic lurking within %s.", name) );
			}
		}

		return info.toString();
	}

	@Override
	public int price() {
		int price = 20 * (1 << (tier - 1));
		if (enchantment != null) {
			price *= 1.5;
		}
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level > 0) {
				price *= (level + 1);
			} else if (level < 0) {
				price /= (1 - level);
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

	@Override
	public Item random() {
		super.random();

		if (Random.Int( 10 + level ) == 0) {
			enchant( Enchantment.random() );
		}

		return this;
	}
}