package scenes.ShopScene.System;

import static org.junit.Assert.*;

import java.io.File;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.junit.Test;

import scenes.WorldScene.WorldSystem.Event;

public class ShopTest {

	@Test
	public void test() {
		String path = "maps/test/";
		Preferences pref = null;
		try {
			pref = new IniPreferences(new Ini(new File("data/" + path + "map.ini")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Shop s = new Shop(pref.node("Event@4,4"));
		
		assertEquals("muffins...", s.getGreeting());
		assertEquals(4, s.getItems().length);
		assertEquals("mffin", s.getItems()[0].getName());
	}

}
