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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asmateus
 */
public class Bullet extends Element 
{
    private final ToysArea area;
    private boolean is_collided = false;
    private final Bullet bull;
    public final Tank tank;
    
    public Bullet(int party, ToysArea area, Tank tank)
    {
        this.type = -1;
        this.area = area;
        this.bull = this;
        this.tank = tank;
    }
    
    public void addCollisionSystem(CollisionSystem coll_sys)
    {
        this.coll_sys = coll_sys;
    }
    
    @Override
    public void move(int direction)
    {
        Thread thread = new Thread(
            new Runnable() {
                @Override
                public void run()
                {
                    boolean dynamic = false;
                    int c;
                    while(!is_collided) {
                        // Check collision with dynamic and static elements
                        switch(direction)
                        {
                            case Tank.UP:
                                c = coll_sys.predictCollision(position.x, position.y - 2); 
                                if(c == 0) {
                                    if(!coll_sys.predictCollisionSubscriber(position.x, position.y - 2, bull, tank))
                                        position.y -= 2;
                                    else {
                                        dynamic = true; is_collided = true;
                                    }
                                }
                                else {
                                    if(c == 2) {
                                        System.out.println("Reducing life");
                                        bull.area.local.getTanks().get(0).live_points = 0;
                                        bull.area.local.getTanks().get(0).lives = -1;
                                    }
                                    is_collided = true;
                                }
                                break;
                            case Tank.DOWN:
                                c = coll_sys.predictCollision(position.x, position.y + 2); 
                                if(c == 0) {
                                    if(!coll_sys.predictCollisionSubscriber(position.x, position.y + 2, bull, tank))
                                        position.y += 2;
                                    else {
                                        dynamic = true; is_collided = true;
                                    }
                                }
                                else {
                                    if(c == 2) {
                                        System.out.println("Reducing life");
                                        bull.area.local.getTanks().get(0).live_points = 0;
                                        bull.area.local.getTanks().get(0).lives = -1;
                                    }
                                    is_collided = true;
                                }
                                break;
                            case Tank.LEFT:
                                c = coll_sys.predictCollision(position.x - 2, position.y); 
                                if(c == 0) {
                                    if(!coll_sys.predictCollisionSubscriber(position.x - 2, position.y, bull, tank))
                                        position.x -= 2;
                                    else {
                                        dynamic = true; is_collided = true;
                                    }
                                }
                                else {
                                    if(c == 2) {
                                        System.out.println("Reducing life");
                                        bull.area.local.getTanks().get(0).live_points = 0;
                                        bull.area.local.getTanks().get(0).lives = -1;
                                    }
                                    is_collided = true;
                                }
                                break;
                            case Tank.RIGHT:
                                c = coll_sys.predictCollision(position.x + 2, position.y); 
                                if(c == 0) {
                                    if(!coll_sys.predictCollisionSubscriber(position.x + 2, position.y, bull, tank))
                                        position.x += 2;
                                    else {
                                        dynamic = true; is_collided = true;
                                    }
                                }
                                else {
                                    if(c == 2) {
                                        System.out.println("Reducing life");
                                        bull.area.local.getTanks().get(0).live_points = 0;
                                        bull.area.local.getTanks().get(0).lives = -1;
                                    }
                                    is_collided = true;
                                }
                                break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(!dynamic) {
                            if(is_collided) {
                                area.toys.remove(bull);
                                coll_sys.removeSubscriber(bull);
                                Cell cell = new Cell(0,0);
                                switch(direction)
                                {
                                    case Tank.UP:
                                        cell = coll_sys.getCollisionCell(position.x, position.y - 2);
                                        break;
                                    case Tank.DOWN:
                                        cell = coll_sys.getCollisionCell(position.x, position.y + 2);
                                        break;
                                    case Tank.LEFT:
                                        cell = coll_sys.getCollisionCell(position.x - 2, position.y);
                                        break;
                                    case Tank.RIGHT:
                                        cell = coll_sys.getCollisionCell(position.x + 2, position.y);
                                        break;
                                }
                                if((cell.row - 2)/2 != 15 && (cell.col - 2)/2 != 17) {
                                    String code = damage(area.gd.im[(cell.row - 2)/2][(cell.col - 2)/2], direction);
                                    if(code.equals("xxxx")) {
                                        System.out.println("HELLO SUCIDE");
                                        area.gd.im[(cell.row - 2)/2][(cell.col - 2)/2] = "Z   ";
                                        area.gd.need_redraw = 1;
                                        tank.lives = 0;
                                    }
                                    else {
                                        area.gd.im[(cell.row - 2)/2][(cell.col - 2)/2] = code;
                                        area.gd.need_redraw = 1;
                                    }
                                }
                            }
                        }
                        else {
                            area.toys.remove(bull);
                            coll_sys.removeSubscriber(bull);
                        }
                    }
                }
            }
        );
        thread.start();
    }
    
    private String damage(String code, int direction)
    {
        switch(code) {
            case "I   ":
                code = "iiii";
                break;
            case "J   ":
                code = "jjjj";
                break;
            case "K   ":
                code = "kkkk";
                break;
            case "X":
                code = "xxxx";
                return code;
        }
        char[] c = code.toCharArray();
        switch(direction) {
            case Tank.UP:
                if(c[2] == ' ' && c[3] == ' ') {
                    c[0] = reduceByCode(c[0]);
                    c[1] = reduceByCode(c[1]);
                }
                else {
                    c[2] = reduceByCode(c[2]);
                    c[3] = reduceByCode(c[3]);
                }
                break;
            case Tank.DOWN:
                if(c[0] == ' ' && c[1] == ' ') {
                    c[2] = reduceByCode(c[2]);
                    c[3] = reduceByCode(c[3]);
                }
                else {
                    c[0] = reduceByCode(c[0]);
                    c[1] = reduceByCode(c[1]);
                }
                break;
            case Tank.LEFT:
                if(c[1] == ' ' && c[3] == ' ') {
                    c[0] = reduceByCode(c[0]);
                    c[2] = reduceByCode(c[2]);
                }
                else {
                    c[1] = reduceByCode(c[1]);
                    c[3] = reduceByCode(c[3]);
                }
                break;
            case Tank.RIGHT:
                if(c[2] == ' ' && c[0] == ' ') {
                    c[1] = reduceByCode(c[1]);
                    c[3] = reduceByCode(c[3]);
                }
                else {
                    c[2] = reduceByCode(c[2]);
                    c[0] = reduceByCode(c[0]);
                }
                break;
        }
        code = "";
        code += c[0];
        code += c[1];
        code += c[2];
        code += c[3];
        return code;
    }
    
    private char reduceByCode(char c)
    {
        if(c == 'i')
            c = ' ';
        return c;
    }
}
