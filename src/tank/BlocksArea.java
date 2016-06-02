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
import java.awt.Graphics;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author asmateus
 */
public class BlocksArea extends JPanel 
{
    private final List<List<BlockDescriptor>> bg;
    
    public BlocksArea(List<List<BlockDescriptor>> bg)
    {
        this.setBackground(Color.BLACK);
        this.bg = bg;
    }
    
    @Override
    public synchronized void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // Repaint Elements
        System.out.println(this.bg.size());
        System.out.println(this.bg.get(0).size());
        for(int i = 0; i < 15; ++i) {
            for(int j = 0; j < 17; ++j) {
                if(this.bg.get(i).get(j).imgs.size() > 1) {
                    g.drawImage(new ImageIcon(this.bg.get(i).get(j).imgs.get(0)).getImage(), j*32, i*32, null);
                    g.drawImage(new ImageIcon(this.bg.get(i).get(j).imgs.get(1)).getImage(), j*32 + 16, i*32, null);
                    g.drawImage(new ImageIcon(this.bg.get(i).get(j).imgs.get(2)).getImage(), j*32, i*32 + 16, null);
                    g.drawImage(new ImageIcon(this.bg.get(i).get(j).imgs.get(3)).getImage(), j*32 + 16, i*32 + 16, null);
                }
                else{
                    if(!this.bg.get(i).get(j).code.equals("L   "))
                        g.drawImage(new ImageIcon(this.bg.get(i).get(j).imgs.get(0)).getImage(), j*32, i*32, null);
                    else {
                        g.drawImage(new ImageIcon("resources/blocks/terrain/void.png").getImage(), j*32, i*32, null);
                    }
                }
            }
        }
    }
}
