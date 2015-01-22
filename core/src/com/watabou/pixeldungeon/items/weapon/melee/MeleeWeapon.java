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
		final String mw_text_0 = "This ";
		final String mw_text_1 = " is ";
		final String mw_text_2 = "upgraded";
		final String mw_text_3 = "degraded";
		final String mw_text_4 = " tier-";
		final String mw_text_5 = " melee weapon. ";
		final String mw_text_6 = "Its average damage is ";
		final String mw_text_7 = " points per hit. ";
		final String mw_text_8 = "Its typical average damage is ";
		final String mw_text_9 = " points per hit. ";
		final String mw_text_10 = "Its typical average damage is ";
		final String mw_text_11 = "and usually it requires ";
		final String mw_text_12 = " points of strength. ";
		final String mw_text_13 = "Probably this weapon is too heavy for you. ";
		final String mw_text_14 = "This is a rather ";
		final String mw_text_15 = "fast";
		final String mw_text_16 = "slow";
		final String mw_text_17 = " and ";
		final String mw_text_18 = " but ";
		final String mw_text_19 = "accurate";
		final String mw_text_20 = "inaccurate";
		final String mw_text_21 = " weapon. ";
		final String mw_text_22 = "This is a rather ";
		final String mw_text_23 = "It was balanced to make it faster. ";
		final String mw_text_24 = "It was balanced to make it more accurate. ";
		final String mw_text_25 = "It is enchanted.";
		final String mw_text_26 = "Because of your inadequate strength the accuracy and speed of your attack with this weapon is decreased.";
		final String mw_text_27 = "Because of your excess strength the damage of your attack with this weapon is increased.";
		final String mw_text_28 = "You hold the weapon at the ready";
		final String mw_text_29 = ", and because it is cursed, you are powerless to let go.";
		final String mw_text_30 = "You can feel a malevolent magic lurking within this weapon.";
		
		StringBuilder info = new StringBuilder( desc() );
		
		String quality = levelKnown && level != 0 ? (level > 0 ? mw_text_2 : mw_text_3) : "";
		info.append( p );
		info.append( mw_text_0 + name + mw_text_1 + Utils.indefinite( quality ) );
		info.append( mw_text_4 + tier + mw_text_5 );
		
		if (levelKnown) {
			info.append( mw_text_6 + (MIN + (MAX - MIN) / 2) + mw_text_7 );
		} else {
			info.append( 
				mw_text_10 + (min() + (max() - min()) / 2) + mw_text_7 +
				mw_text_11 + typicalSTR() + mw_text_12 );
			if (typicalSTR() > Dungeon.hero.STR()) {
				info.append( mw_text_13 );
			}
		}
		
		if (DLY != 1f) {
			info.append( mw_text_14 + (DLY < 1f ? mw_text_15 : mw_text_16) );
			if (ACU != 1f) {
				if ((ACU > 1f) == (DLY < 1f)) {
					info.append( mw_text_17);
				} else {
					info.append( mw_text_18);
				}
				info.append( ACU > 1f ? mw_text_19 : mw_text_20 );
		20
			info.append( mw_text_21);
		} else if (ACU != 1f) {
			info.append( mw_text_22 + (ACU > 1f ? mw_text_19 : mw_text_20) + mw_text_21 );
		}
		switch (imbue) {
		case SPEED:
			info.append( mw_text_23 );
			break;
		case ACCURACY:
			info.append( mw_text_24 );
			break;
		case NONE:
		}
		
		if (enchantment != null) {
			info.append( mw_text_25 );
		}
		
		if (levelKnown && Dungeon.hero.belongings.backpack.items.contains( this )) {
			if (STR > Dungeon.hero.STR()) {
				info.append( p );
				info.append( 
					mw_text_26 );
			}
			if (STR < Dungeon.hero.STR()) {
				info.append( p );
				info.append( 
					mw_text_27 );
			}
		}
		
		if (isEquipped( Dungeon.hero )) {
			info.append( p );
			info.append( mw_text_28 + 
				(cursed ? mw_text_29 : ".") ); 
		} else {
			if (cursedKnown && cursed) {
				info.append( p );
				info.append( mw_text_30 );
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
