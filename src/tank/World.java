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
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author asmateus
 * 
 * World has four main things aside from its container:
 *  A PlayGround
 *  A Title
 *  A Status Panel
 *  A Graph Descriptor
 * The World class also need some utility Classes:
 *  A LightHouse
 *
 */

public class World
{
    private final GraphDescriptor graph;
    private final GameArea container;
    private final PlayGround playground;
    
    private final String title;
    
    public World(String title, GraphDescriptor graph, GameArea container)
    {
        this.title = title;
        
        this.container = container;
        this.graph = graph;
        this.playground = new PlayGround(this.graph);
    }
    
    private void setWorldTitle(GridBagConstraints c)
    {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.ipady = 20;
        
        Label world_title = new Label(title);
        world_title.setForeground(new Color(250, 250, 250));
        world_title.setHorizontalAlignment(SwingConstants.CENTER);
        world_title.setFont("resources/fonts/ARCADECLASSIC.ttf", 40.0f);
        
        container.add(world_title, c);
    }
    
    private int getNumByTitle()
    {
        return 1;
    }
    
    public void start()
    {
        GridBagConstraints c = new GridBagConstraints();
        
        // Add world title to game area
        setWorldTitle(c);
        
        // Adding playground to game area
        playground.startPG();
        container.add(playground, playground.getPGConstraints());
        
        // Add enemy counter
        EnemyCounter enemy_counter = new EnemyCounter();
        enemy_counter.startCounter();
        
        c.gridx = 3;
        c.gridy = 1;
        c.gridheight = 10;
        c.insets = new Insets(0, 20, 0, 0);
        container.add(enemy_counter, c);
        
        // Add life counter
        LifeCounter life_counter = new LifeCounter();
        life_counter.startLifeCounter();
        
        c.gridy = 12;
        c.gridheight = 2;
        c.ipadx = 0;
        c.ipady = 0;
        c.insets = new Insets(50, 20, 0, 0);
        container.add(life_counter, c);
        
        // Add World count
        JLabel flag = new JLabel(new ImageIcon("resources/blocks/status/flag.png"));
        
        c.gridy = 18;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        c.insets = new Insets(60, 20, 0, 0);
        container.add(flag, c);
        
        Label world_num = new Label(String.valueOf(getNumByTitle()));
        world_num.setHorizontalAlignment(SwingConstants.RIGHT);
        world_num.setForeground(Color.BLACK);
        world_num.setFont("resources/fonts/ARCADECLASSIC.ttf", 25.0f);
        
        c.gridy = 19;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        c.insets = new Insets(0, 20, 0, 0);
        container.add(world_num, c);
        
        container.updateUI();
        
        // Start game thread
    }
}
