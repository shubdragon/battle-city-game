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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asmateus
 */
public class Bullet extends Element 
{
    private final ToysArea area;
    private CollisionSystem coll_sys;
    private Cell coll_cell;
    private boolean is_collided = false;
    private Bullet bull;
    
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
                            List<Cell> cells = new ArrayList<>();
                            switch(direction)
                            {
                                case Tank.UP:
                                    cells = coll_sys.getCollisionCells(position.x, position.y - 2);
                                    break;
                                case Tank.DOWN:
                                    cells = coll_sys.getCollisionCells(position.x, position.y + 2);
                                    break;
                                case Tank.LEFT:
                                    cells = coll_sys.getCollisionCells(position.x - 2, position.y);
                                    break;
                                case Tank.RIGHT:
                                    cells = coll_sys.getCollisionCells(position.x + 2, position.y);
                                    break;
                            }
                            for(int i = 0; i < cells.size(); ++i) {
                                damage(area.gd.block_graph.get((cells.get(i).col - 2)/2).get((cells.get(i).row - 2)/2), direction);
                            }
                        }
                        area.repaint();
                    }
                }
            }
        );
        thread.start();
    }
    
    private void damage(BlockDescriptor b, int direction)
    {
        switch(b.code) {
            case "I   ":
                b.code = "iiii";
                break;
            case "J   ":
                b.code = "jjjj";
                break;
            case "K   ":
                b.code = "kkkk";
                break;
        }
        
        switch(direction) {
            case Tank.UP:
                break;
            case Tank.DOWN:
                break;
            case Tank.LEFT:
                break;
            case Tank.RIGHT:
                break;
        }
    }
}
