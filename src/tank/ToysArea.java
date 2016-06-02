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

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author asmateus
 * 
 * This is the class where dynamic things happens, it can add players and the players
 * can have elements associated to them (tank, bases, bullets), a player needs to
 * subscribe its elements to the collision system. The collision system works like this:
 *  It keeps track of the mobile elements such as tanks and bullets and makes a
 *  collision prediction in the move method of such elements. And implements the react
 *  to collision and notifyAffecteds methods.
 * For collision detection a weighted graph will be used, the tanks will be dynamically added
 * to this graph based on its position inside the ToysArea.
 */
public class ToysArea extends JPanel
{   
    public List<Element> toys = new ArrayList<>();
    public List<Tank> enemies = new ArrayList<>();
    public PlayGround pg;
    public Integer[][] coll_graph;
    public GraphDescriptor gd;
    public CollisionSystem coll_sys;
    
    public Player local = null;
    
    public ToysArea(int width, int height, GraphDescriptor gd, PlayGround pg)
    {
        super.setSize(width, height);
        this.gd = gd;
        this.pg = pg;
        this.coll_graph = gd.weight_graph;
        this.coll_sys = new CollisionSystem(this.coll_graph);
        
    }
    
    public void addLocalPlayer(Player p)
    {
        local = p;
        // Setting up tank of local player
        Tank player_tank = new Tank(local, this, Tank.NORMAL);
        player_tank.position = new Point(192, 420);
        
        // Adding tank to local player
        local.subscribeTank(player_tank);
        
        // Setting up collision system
        coll_sys.addSubscriber(player_tank);
        
        // Adding collision system to tank
        player_tank.addCollisionSystem(coll_sys);
        
        // Add tank to array of elements to be redrawn
        this.toys.add(player_tank);
        
        this.repaint();
        this.startPlayerManager();
    }
    
    /**
     * Starts thread that spawns enemy tanks according to certain conditions
     */
    public void startPlayerManager()
    {
        PlayerManager manager = new PlayerManager(this);
        manager.start();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        // Repaint Elements
        for(int i = 0; i < toys.size(); ++i)
            g.drawImage(toys.get(i).image.getImage(), toys.get(i).position.x, toys.get(i).position.y, null);
    }
}
