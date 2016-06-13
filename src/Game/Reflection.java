/**
 *	rscplus
 *
 *	This file is part of rscplus.
 *
 *	rscplus is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	rscplus is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with rscplus.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Authors: see <https://github.com/OrN/rscplus>
 */

package Game;

import Client.JClassLoader;
import Client.Launcher;
import Client.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {
	public static void Load() {
		try {
			JClassLoader classLoader = Launcher.getInstance().getClassLoader();

			// Client
			Class<?> c = classLoader.loadClass("client");
			Method[] methods = c.getDeclaredMethods();
			for (Method method : methods) {
				if (method.toGenericString().equals(DISPLAYMESSAGE)) {
					displayMessage = method;
					Logger.Info("Found displayMessage");
				} else if (method.toGenericString().equals(SETLOGINTEXT)) {
					setLoginText = method;
					Logger.Info("Found setLoginText");
				}
			}

			// Region X and Region Y
			c.getDeclaredField("Qg").setAccessible(true);
			c.getDeclaredField("zg").setAccessible(true);
			// Maximum inventory (30)
			maxInventory = c.getDeclaredField("cl");
			if (maxInventory != null)
				maxInventory.setAccessible(true);

			// Camera
			c = classLoader.loadClass("lb");
			methods = c.getDeclaredMethods();
			for (Method method : methods) {
				if (method.toGenericString().equals(SETCAMERASIZE)) {
					setCameraSize = method;
					Logger.Info("Found setCameraSize");
				}
			}

			// Renderer
			c = classLoader.loadClass("ua");
			methods = c.getDeclaredMethods();
			for (Method method : methods) {
				if (method.toGenericString().equals(SETGAMEBOUNDS)) {
					setGameBounds = method;
					Logger.Info("Found setGameBounds");
				}
			}

			// Character
			c = classLoader.loadClass("ta");
			characterName = c.getDeclaredField("C");
			characterWaypointX = c.getDeclaredField("i");
			characterWaypointY = c.getDeclaredField("K");
			if (characterName != null)
				characterName.setAccessible(true);
			if (characterWaypointX != null)
				characterWaypointX.setAccessible(true);
			if (characterWaypointY != null)
				characterWaypointY.setAccessible(true);

			// Menu
			c = classLoader.loadClass("qa");
			menuX = c.getDeclaredField("kb");
			menuY = c.getDeclaredField("B");
			menuScroll = c.getDeclaredField("j");
			menuWidth = c.getDeclaredField("ob");
			// this menu height for chats I believe
			menuHeight = c.getDeclaredField("O");
			// this menu array I'm not sure whats for
			menuUknown = c.getDeclaredField("sb");

			// Set all accessible
			if (menuX != null)
				menuX.setAccessible(true);
			if (menuY != null)
				menuY.setAccessible(true);
			if (menuScroll != null)
				menuScroll.setAccessible(true);
			if (menuWidth != null)
				menuWidth.setAccessible(true);
			if (menuHeight != null)
				menuHeight.setAccessible(true);
			if (menuUknown != null)
				menuUknown.setAccessible(true);
			if (displayMessage != null)
				displayMessage.setAccessible(true);
			if (setCameraSize != null)
				setCameraSize.setAccessible(true);
			if (setGameBounds != null)
				setGameBounds.setAccessible(true);
			if (setLoginText != null)
				setLoginText.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Field characterName = null;
	public static Field characterWaypointX = null;
	public static Field characterWaypointY = null;

	public static Field maxInventory = null;

	public static Field menuX = null;
	public static Field menuY = null;
	public static Field menuScroll = null;
	public static Field menuWidth = null;
	public static Field menuHeight = null;
	public static Field menuUknown = null;

	public static Method displayMessage = null;
	public static Method setCameraSize = null;
	public static Method setGameBounds = null;
	public static Method setLoginText = null;

	// Method descriptions
	private static final String DISPLAYMESSAGE = "private final void client.a(boolean,java.lang.String,int,java.lang.String,int,int,java.lang.String,java.lang.String)";
	private static final String SETCAMERASIZE = "final void lb.a(int,boolean,int,int,int,int,int)";
	private static final String SETGAMEBOUNDS = "final void ua.a(int,int,int,int,byte)";
	private static final String SETLOGINTEXT = "private final void client.b(byte,java.lang.String,java.lang.String)";
}
