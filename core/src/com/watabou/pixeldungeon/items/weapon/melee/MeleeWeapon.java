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

public class MeleeWeapon extends Weapon {

	private int tier;

	private static final String PARA = "\n\n";
	private static final String MW_TEXT_0 = "This ";
	private static final String MW_TEXT_1 = " is ";
	private static final String MW_TEXT_2 = "upgraded";
	private static final String MW_TEXT_3 = "degraded";
	private static final String MW_TEXT_4 = " tier-";
	private static final String MW_TEXT_5 = " melee weapon. ";
	private static final String MW_TEXT_6 = "Its average damage is ";
	private static final String MW_TEXT_7 = " points per hit. ";
	// private static final String MW_TEXT_8 = "Its typical average damage is ";
	// private static final String MW_TEXT_9 = " points per hit. ";
	private static final String MW_TEXT_10 = "Its typical average damage is ";
	private static final String MW_TEXT_11 = "and usually it requires ";
	private static final String MW_TEXT_12 = " points of strength. ";
	private static final String MW_TEXT_13 = "Probably this weapon is too heavy for you. ";
	private static final String MW_TEXT_14 = "This is a rather ";
	private static final String MW_TEXT_15 = "fast";
	private static final String MW_TEXT_16 = "slow";
	private static final String MW_TEXT_17 = " and ";
	private static final String MW_TEXT_18 = " but ";
	private static final String MW_TEXT_19 = "accurate";
	private static final String MW_TEXT_20 = "inaccurate";
	private static final String MW_TEXT_21 = " weapon. ";
	private static final String MW_TEXT_22 = "This is a rather ";
	private static final String MW_TEXT_23 = "It was balanced to make it faster. ";
	private static final String MW_TEXT_24 = "It was balanced to make it more accurate. ";
	private static final String MW_TEXT_25 = "It is enchanted.";
	private static final String MW_TEXT_26 = "Because of your inadequate strength the accuracy and speed of your attack with this weapon is decreased.";
	private static final String MW_TEXT_27 = "Because of your excess strength the damage of your attack with this weapon is increased.";
	private static final String MW_TEXT_28 = "You hold the weapon at the ready";
	private static final String MW_TEXT_29 = ", and because it is cursed, you are powerless to let go.";
	private static final String MW_TEXT_30 = "You can feel a malevolent magic lurking within this weapon.";

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

		StringBuilder info = new StringBuilder( desc() );

		String quality = levelKnown && level != 0 ? (level > 0 ? MW_TEXT_2 : MW_TEXT_3) : "";
		info.append(PARA);
		info.append( MW_TEXT_0 + name + MW_TEXT_1 + Utils.indefinite( quality ) );
		info.append( MW_TEXT_4 + tier + MW_TEXT_5);

		if (levelKnown) {
			info.append( MW_TEXT_6 + (MIN + (MAX - MIN) / 2) + MW_TEXT_7);
		} else {
			info.append(
					MW_TEXT_10 + (min() + (max() - min()) / 2) + MW_TEXT_7 +
							MW_TEXT_11 + typicalSTR() + MW_TEXT_12);
			if (typicalSTR() > Dungeon.hero.STR()) {
				info.append(MW_TEXT_13);
			}
		}

		if (DLY != 1f) {
				info.append( MW_TEXT_14 + (DLY < 1f ? MW_TEXT_15 : MW_TEXT_16) );
				if (ACU != 1f)
				{
					if ((ACU > 1f) == (DLY < 1f))
					{
						info.append(MW_TEXT_17);
					} else
					{
						info.append(MW_TEXT_18);
					}
					info.append(ACU > 1f ? MW_TEXT_19 : MW_TEXT_20);
				}
				info.append(MW_TEXT_21);
			} else if (ACU != 1f) {
				info.append( MW_TEXT_22 + (ACU > 1f ? MW_TEXT_19 : MW_TEXT_20) + MW_TEXT_21);
			}
			switch (imbue) {
				case SPEED:
					info.append(MW_TEXT_23);
					break;
				case ACCURACY:
					info.append(MW_TEXT_24);
					break;
				case NONE:
			}

			if (enchantment != null) {
				info.append(MW_TEXT_25);
			}

			if (levelKnown && Dungeon.hero.belongings.backpack.items.contains( this )) {
				if (STR > Dungeon.hero.STR()) {
					info.append(PARA);
					info.append(
							MW_TEXT_26);
				}
				if (STR < Dungeon.hero.STR()) {
					info.append(PARA);
					info.append(
							MW_TEXT_27);
				}
			}

			if (isEquipped( Dungeon.hero )) {
				info.append(PARA);
				info.append( MW_TEXT_28 +
						(cursed ? MW_TEXT_29 : ".") );
			} else {
				if (cursedKnown && cursed) {
					info.append(PARA);
					info.append(MW_TEXT_30);
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