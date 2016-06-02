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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import java.lang.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asmateus
 * 
 * The Tanks are part of the elements of the player, so it needs to listen to it 
 * via Controller interface.
 * 
 */
public class Tank extends Element implements Controller, Mechanics
{
    public final static int NONE  = 0;
    public final static int UP    = 1;
    public final static int DOWN  = 2;
    public final static int RIGHT = 3;
    public final static int LEFT  = 4;
    public final static int DELTA = 2;
    
    public final static int RAPID      = 1;
    public final static int NORMAL     = 2;
    public final static int SMALL      = 3;
    public final static int BALISTIC   = 4;
    public final static int SURVEY     = 5;
    public final static int ACUATIC    = 6;
    public final static int CONTINUOUS = 7;
    public final static int FORTE      = 8;
    
    private String resource;
    private String bonus;
    private String player_type;
    
    private final Player player;
    private final ToysArea area;
    
    private long time_starter;
    private long time_finalizer = 0;
    
    public Tank(Player player, ToysArea area, int type)
    {
        this.player = player;
        this.area = area;
        this.type = type;
        this.resource = this.getPathFromType();
        this.player_type = getPlayerType();
        this.bonus = "";
        this.image = new ImageIcon("resources/blocks/tanks/" + resource + player_type + bonus + "_up.png");
    }
    
    private String getPlayerType()
    {
        if(player.party == Player.LOCAL)
            return "_player1";
        else if(player.party == Player.MACHINE)
            return "_machine";
        else
            return "_player2";
    }
    
    private String getPathFromType()
    {
        switch(this.type)
        {
            case Tank.RAPID:
                return "tank_rapid";
            case Tank.NORMAL:
                return "tank";
            case Tank.SMALL:
                return "tank_small";
            case Tank.BALISTIC:
                return "tank_balistic";
            case Tank.SURVEY:
                return "tank_survey";
            case Tank.ACUATIC:
                return "tank_acuatic";
            case Tank.CONTINUOUS:
                return "tank_continuous";
            case Tank.FORTE:
                return "tank_forte";
        }
        return "tank";
    }
    
    public void setType(int t)
    {
        this.type = t;
        this.resource = this.getPathFromType();
    }
    
    public void setBonus(int b)
    {
        if(b == 1)
            this.bonus = "_b";
        else
            this.bonus = "";
    }
    
    public void addCollisionSystem(CollisionSystem c)
    {
        this.coll_sys = c;
    }
    
    @Override
    public void masterIssuedOrder(int order)
    {
        order -= 2000;
        switch(order) {
            case KeyEvent.VK_UP:
                move(Tank.UP);
                break;
            case KeyEvent.VK_DOWN:
                move(Tank.DOWN);
                break;
            case KeyEvent.VK_RIGHT:
                move(Tank.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                move(Tank.LEFT);
                break;
            case KeyEvent.VK_SPACE:
                shoot();
                break;
        }
    }
    
    public ImageIcon getImageIcon()
    {
        return this.image;
    }
    
    @Override
    public void move(int direction) 
    {
        repainted = false;
        switch(direction) {
            case Tank.NONE:
                break;
            case Tank.UP:
                if(orientation != direction) {
                    orientation = direction;
                    image = new ImageIcon("resources/blocks/tanks/" + resource + player_type + bonus + "_up.png");
                }
                if(!coll_sys.predictCollision(new Point(position.x, position.y - Tank.DELTA))) {
                    position.y -= Tank.DELTA;
                }
                break;
            case Tank.DOWN:
                if(orientation != direction) {
                    orientation = direction;
                    image = new ImageIcon("resources/blocks/tanks/" + resource + player_type + bonus + "_down.png");
                }
                if(!coll_sys.predictCollision(new Point(position.x, position.y + Tank.DELTA))) {
                    position.y += Tank.DELTA;
                }
                break;
            case Tank.RIGHT:
                if(orientation != direction) {
                    orientation = direction;
                    image = new ImageIcon("resources/blocks/tanks/" + resource + player_type + bonus + "_right.png");
                }
                if(!coll_sys.predictCollision(new Point(position.x  + Tank.DELTA, position.y))) {
                    position.x += Tank.DELTA;
                }
                break;
            case Tank.LEFT:
                if(orientation != direction) {
                    orientation = direction;
                    image = new ImageIcon("resources/blocks/tanks/" + resource + player_type + bonus + "_left.png");
                }
                if(!coll_sys.predictCollision(new Point(position.x  - Tank.DELTA, position.y))) {
                    position.x -= Tank.DELTA;
                }
                break;
        }
    }
    
    public void shoot()
    {
        // Handle bullet timeout
        if(this.time_finalizer == 0) {
            this.time_finalizer = 1;
            // Creating and configuring bullet
            Bullet bullet = new Bullet(this.player.party, area);
            bullet.orientation = this.orientation;
            switch(this.orientation) {
                case Tank.UP:
                    bullet.position = new Point(this.position.x + 16, this.position.y);
                    bullet.image = new ImageIcon("resources/blocks/dynamic/bullet_up.png");
                    break;
                case Tank.DOWN:
                    bullet.position = new Point(this.position.x + 16, this.position.y + 32);
                    bullet.image = new ImageIcon("resources/blocks/dynamic/bullet_down.png");
                    break;
                case Tank.LEFT:
                    bullet.position = new Point(this.position.x, this.position.y + 16);
                    bullet.image = new ImageIcon("resources/blocks/dynamic/bullet_left.png");
                    break;
                case Tank.RIGHT:
                    bullet.position = new Point(this.position.x + 32, this.position.y + 16);
                    bullet.image = new ImageIcon("resources/blocks/dynamic/bullet_right.png");
                    break;
            }
            bullet.addCollisionSystem(coll_sys);
            bullet.repainted = false;
            this.area.toys.add(bullet);
            
            bullet.move(bullet.orientation);
            
            Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run()
                    {
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        time_finalizer = 0;
                    }
                }
            );
            thread.start();
        }
    }
    
    @Override
    public void reactToCollision()
    {
        
    }
    
    @Override
    public void notifyInvolved()
    {
        
    }
    
}
