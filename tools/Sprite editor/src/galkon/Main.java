package galkon;

import java.awt.*;


/**
 *Creates the data compressor
 *
 *@author Galkon
 */

public class Main {

	/**
	 *@param arguments the arguments passed from the command line console.
	 */
	public static void main(java.lang.String[] arguments) {
		/**
		 * Build the list of sprite files in the sprites folder.
		 */
		FileController.buildSpriteList();

		/**
		 * Load the sprites from the sprite cache and the sprites folder.
		 */
		SpriteLoader.load();

		/**
		 * Open the UI.
		 */
		EventQueue.invokeLater(() -> new UserInterface());
	}

	/**
	 * Logs text to the console.
	 */
	public static void log(String text) {
		System.out.println(text);
	}

}