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

import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author asmateus
 */
public class PlayerManager extends Thread
{
    
    private final ToysArea area;
    private int run = 1;
    
    public PlayerManager(ToysArea area)
    {
        this.area = area;
    }
    
    public void spawnPlayer()
    {
        Player p = new Player(Player.MACHINE);
        p.addToysArea(area);
        Tank t = new Tank(p, area, Tank.CONTINUOUS);
        t.addCollisionSystem(area.coll_sys);
        t.position = new Point(512, 0);
        p.subscribeTank(t);
        
        area.coll_sys.addSubscriber(t);
        area.enemies.add(t);
        area.toys.add(t);
        area.repaint();
        
        // Init AI
        p.personality = AI.CRAZY;
        p.initAI();
    }
    
    @Override
    public void run()
    {
        // Check if a new tank can be created
        while(run == 1) {
            if(this.area.enemies.size() < 4) {
                if(this.area.pg.world.getEnemyCounter().getLocalCount() > 0) {
                    spawnPlayer();
                }
            }
            Tank t;
            for(int i = 0; i < area.enemies.size(); ++i) {
                t = area.enemies.get(i);
                if(area.enemies.get(i).live_points <= 0) {
                    
                    // Remove tank from everywere
                    t.player.deus.cancel();
                    area.coll_sys.removeSubscriber(t);
                    area.toys.remove(t);
                    area.enemies.remove(t);
                    area.pg.world.getEnemyCounter().counter -= 1;
                    area.pg.world.getEnemyCounter().labels.get(area.pg.world.getEnemyCounter().counter).setText("");
                } 
            }
            t = area.local.getTanks().get(0);
            if(t.live_points <= 0) {
                // Remove local player from everywere
                t.live_points = 1;
                t.lives -= 1;
                if(t.lives < 0) {
                    area.coll_sys.removeSubscriber(t);
                    area.toys.remove(t);
                }
                else{
                    t.position = new Point(192, 420);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayerManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
