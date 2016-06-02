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
    List<Player> players = new ArrayList<>();
    Integer[][] coll_graph;
    Player local = null;
    Tank tanky;
    
    public ToysArea(int width, int height, Integer[][] coll_graph)
    {
        super.setSize(width, height);
        this.coll_graph = coll_graph;
    }
    
    public void addPlayer(Player p)
    {
        players.add(p);
        local = p;
        //Tank player_tank = new Tank(p);
        //player_tank.position = new Point(192, 445);
        tanky = new Tank(local);
        tanky.position = new Point(192, 420);
        CollisionSystem coll_sys = new CollisionSystem(this.coll_graph);
        coll_sys.addSubscriber(tanky);
        tanky.addCollisionSystem(coll_sys);
        coll_sys.guessSubscriberPosition(tanky);
        //p.subscribeTank(player_tank);
        local.subscribeTank(tanky);
        
        
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        // Repaint Tanks
        //for(int i = 0; i < players.size(); ++i)
        //    for(int j = 0; j < players.get(i).getTanks().size(); ++i)
        //        if(players.get(i).getTanks().get(j).repainted == false) {
        //            players.get(i).getTanks().get(j).repainted = true;
        //            g.drawImage(players.get(i).getTanks().get(j).getImageIcon().getImage(),
        //                players.get(i).getTanks().get(j).position.x, 
        //                players.get(i).getTanks().get(j).position.y, null);
        //        }
        //g.drawImage(new ImageIcon("resources/blocks/tanks/tank_player1_up.png").getImage(), 192, 445, null);
        g.drawImage(tanky.getImageIcon().getImage(), tanky.position.x, tanky.position.y, null);
    }
}
