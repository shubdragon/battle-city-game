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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asmateus
 */
public class CollisionSystem
{
    private final Integer[][] matrix;
    private final List<Element> subscribers = new ArrayList<>();
    
    public CollisionSystem(Integer[][] matrix)
    {
        this.matrix = matrix;
    }
    
    public void addSubscriber(Element o)
    {
        this.subscribers.add(o);
    }
    
    public void removeSubscriber(Element o)
    {
        this.subscribers.remove(o);
    }
    
    public Cell[] guessSubscriberPosition(Element o)
    {
        Cell[] cell = new Cell[4];
        cell[0] = getCellFromPos(o.position);
        cell[1] = new Cell(cell[0].row, cell[0].col + 1);
        cell[2] = new Cell(cell[0].row + 1, cell[0].col);
        cell[3] = new Cell(cell[0].row + 1, cell[0].col + 1);
        
        return cell;
    }
    
    public boolean predictCollision(Point future_position)
    {
        boolean t = false;
        Cell cell = getCellFromPos(future_position);
        
        for(int i = 0; i < 3; ++i) {
            if(i == 2)
                if((float)future_position.y/16 <= cell.row)
                    break;
            for(int j = 0; j < 3; ++j) {
                if(j == 2)
                    if((float)future_position.x/16 <= cell.col)
                        break;
                if(this.matrix[cell.row + i + 2][cell.col + j + 2] > 0)
                    t = true;
            }
        }
        if(future_position.x < 0 || future_position.y < 0)
            t = true;
        return t;
    }
    
    public boolean predictCollision(int x, int y)
    {
        boolean t = false;
        int row = y/16, col = x/16;
        if(this.matrix[row + 2][col + 2] > 0 && this.matrix[row + 2][col + 2] != 3)
            t = true;
        return t;
    }
    
    public List<Cell> getCollisionCells(int x, int y)
    {
        List<Cell> cells = new ArrayList<>();
        int row = y/16, col = x/16;
        if(this.matrix[row + 2][col + 2] > 0 && this.matrix[row + 2][col + 2] != 3)
            cells.add(new Cell(row + 2, col + 2));
        if((float)((y + 8)/16) > (y + 16)/16) {
            if(this.matrix[row + 3][col + 2] > 0 && this.matrix[row + 3][col + 2] != 3)
                cells.add(new Cell(row + 3, col + 2));
        }
        if((float)((x + 8)/16) > (x + 16)/16) {
            if(this.matrix[row + 2][col + 3] > 0 && this.matrix[row + 2][col + 3] != 3)
                cells.add(new Cell(row + 2, col + 3));
        }
        return cells;
    }
    
    private Cell getCellFromPos(Point p)
    {
        int a = p.y/16, b = p.x/16;
        return new Cell(a, b);
    }
}
