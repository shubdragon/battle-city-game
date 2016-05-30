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
 */
public class ToysArea extends JPanel
{   
    List<Player> players = new ArrayList<>();
    Player local = null;
    Tank tanky;
    
    public ToysArea(int width, int height)
    {
        super.setSize(width, height);
    }
    
    public void addPlayer(Player p)
    {
        players.add(p);
        local = p;
        //Tank player_tank = new Tank(p);
        //player_tank.position = new Point(192, 445);
        tanky = new Tank(local);
        tanky.position = new Point(192, 445);
        //p.subscribeTank(player_tank);
        local.subscribeTank(tanky);
        
        //if(p.party == Player.LOCAL) {
        //    System.out.println("Hello, i am local");
        //    this.addKeyListener(keyEvents(p));
        //}
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
    /*
    @Override
    public void keyPressed(KeyEvent ev)
    {
        switch(ev.getKeyCode()) {
            case KeyEvent.VK_S:
                local.notifyTanks(Tank.DOWN);
                break;
            case KeyEvent.VK_W:
                local.notifyTanks(Tank.UP);
                break;
            case KeyEvent.VK_A:
                local.notifyTanks(Tank.LEFT);
                break;
            case KeyEvent.VK_D:
                local.notifyTanks(Tank.RIGHT);
                break;
        }
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent ev)
    {
        switch(ev.getKeyCode()) {
            case KeyEvent.VK_S:
                local.notifyTanks(Tank.NONE);
                break;
            case KeyEvent.VK_W:
                local.notifyTanks(Tank.NONE);
                break;
            case KeyEvent.VK_A:
                local.notifyTanks(Tank.NONE);
                break;
            case KeyEvent.VK_D:
                local.notifyTanks(Tank.NONE);
                break;
        }
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent ev)
    {
             
    }*/
}
