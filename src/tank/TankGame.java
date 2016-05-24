
package tank;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;

/**
 *
 * @author asmateus
 */
public class TankGame 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Get the device of the screen to do active refreshing
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        
        // Create UI
        // There will be only one screen, so load ui in device 0
        UI ui = new UI(devices[0]);
        ui.initComponents(ui.getContentPane());
        
        // Create Game Area
        GameArea game_area = new GameArea(new GridBagLayout());
        
        // Take the user to the menu screen
        Menu menu = new Menu(game_area);
        
        ui.add(game_area, BorderLayout.CENTER);
        ui.begin();
    }
    
}
