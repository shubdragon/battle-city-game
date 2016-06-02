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
    
    public Bullet(int party, ToysArea area)
    {
        this.type = 6;
        this.area = area;
        this.bull = this;
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
                    while(!is_collided) {
                        switch(direction)
                        {
                            case Tank.UP:
                                if(!coll_sys.predictCollision(position.x, position.y - 2)) {
                                    position.y -= 2;
                                }
                                else {
                                    is_collided = true;
                                }
                                break;
                            case Tank.DOWN:
                                if(!coll_sys.predictCollision(position.x, position.y + 2)) {
                                    position.y += 2;
                                }
                                else {
                                    is_collided = true;
                                }
                                break;
                            case Tank.LEFT:
                                if(!coll_sys.predictCollision(position.x - 2, position.y)) {
                                    position.x -= 2;
                                }
                                else {
                                    is_collided = true;
                                }
                                break;
                            case Tank.RIGHT:
                                if(!coll_sys.predictCollision(position.x + 2, position.y)) {
                                    position.x += 2;
                                }
                                else {
                                    is_collided = true;
                                }
                                break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(is_collided) {
                            area.toys.remove(bull);
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
                            //System.out.println((cell.row-2) + ", " + (cell.col-2));
                            area.gd.im[(cell.row - 2)/2][(cell.col - 2)/2] = 
                                damage(area.gd.im[(cell.row - 2)/2][(cell.col - 2)/2], direction);
                            area.gd.translate(null);
                            
                        }
                        area.repaint();
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
