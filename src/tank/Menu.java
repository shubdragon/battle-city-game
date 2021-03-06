/*
 * Copyright (c) 2016, asmateus
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tank;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

/**
 *
 * @author asmateus
 */
public class Menu extends Subscriber 
{
    private final GameArea container;
    private final GridBagConstraints constraints = new GridBagConstraints();
    
    // Define menu items
    private final MenuOption menu_title;
    private final MenuOption menu_single_player;
    private final MenuOption menu_multi_player;
    private final MenuOption menu_construction;
    private final MenuOption menu_statistics;
    
    private MenuOption current_menu;
    
    public Menu(GameArea container)
    {
        this.container = container;
        
        menu_title = new MenuOption("BATTLE CITY", container);
        menu_single_player = new MenuOption("Single   player", container);
        menu_multi_player = new MenuOption("Multiplayer", container);
        menu_construction = new MenuOption("Construction", container);
        menu_statistics = new MenuOption("Statistics", container);
        
        load();
        current_menu = menu_single_player;
        simulateFocusGain();

        // Subscribe Menu to the Linker
        configureCodes();
        configureLinker();
    }
    
    private void configureCodes()
    {
        super.RESPONSE_CODES.add(3000 + KeyEvent.VK_ENTER);
        super.RESPONSE_CODES.add(2000 + KeyEvent.VK_DOWN);
    }
    
    private void configureLinker()
    {
        this.container.getLinker().addSubscriber(this);
    }
    
    @Override
    public void masterIssuedOrder(int order)
    {
        if(order > 1999 && order < 3000) {
            order -= 2000;
            if(order == KeyEvent.VK_DOWN) {
                this.simulationFocusLost();
                this.current_menu = this.current_menu.next_menu;
                this.simulateFocusGain();
            }
        }
        if(order > 2999) {
            order -= 3000;
            if(order == KeyEvent.VK_ENTER) {
                if(this.current_menu.getText().equals("Single   player")) {
                    this.container.getLinker().removeSubscriber(this);
                    Thread thread = new Thread(
                        new Runnable() {
                            @Override
                            public void run()
                            {
                                GameManager manager = new GameManager(container);
                                manager.startGameSession();
                            }
                        });
                    thread.start();
                }
            }
        }
    }
    
    private void simulateFocusGain()
    {
        this.current_menu.formatFont(30.0f);
    }
    
    private void simulationFocusLost()
    {
        this.current_menu.formatFont(24.0f);
    }
    
    private void load()
    {   
        menu_single_player.next_menu = menu_multi_player;
        menu_multi_player.next_menu = menu_construction;
        menu_construction.next_menu = menu_statistics;
        menu_statistics.next_menu = menu_single_player;
        
        // Game title settings
        menu_title.formatFont("resources/fonts/ufonts.com_m10-battle-cities.ttf", 64.0f);
        menu_title.setForeground(new Color(245, 97, 96));
        
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 200;
        constraints.weightx = 0.0;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        
        container.add(menu_title, constraints);
        
        // Single player menu item settings
        menu_single_player.formatFont("resources/fonts/ARCADECLASSIC.ttf", 24.0f);
        menu_single_player.setForeground(new Color(250, 250, 250));
        menu_single_player.setHorizontalAlignment(SwingConstants.CENTER);
        
        constraints.ipady = 40;
        constraints.gridy = 1;
        
        container.add(menu_single_player, constraints);
        
        // Multiplayer menu item settings
        menu_multi_player.formatFont("resources/fonts/ARCADECLASSIC.ttf", 24.0f);
        menu_multi_player.setForeground(new Color(250, 250, 250));
        menu_multi_player.setHorizontalAlignment(SwingConstants.CENTER);
        
        constraints.gridy = 2;
        
        container.add(menu_multi_player, constraints);
        
        // Construction menu item settings
        menu_construction.formatFont("resources/fonts/ARCADECLASSIC.ttf", 24.0f);
        menu_construction.setForeground(new Color(250, 250, 250));
        menu_construction.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.gridy = 3;
        
        container.add(menu_construction, constraints);
        
        // Statistics menu item settings
        menu_statistics.formatFont("resources/fonts/ARCADECLASSIC.ttf", 24.0f);
        menu_statistics.setForeground(new Color(250, 250, 250));
        menu_statistics.setHorizontalAlignment(SwingConstants.CENTER);
        
        constraints.gridy = 4;
        
        container.add(menu_statistics, constraints);
    }
}
