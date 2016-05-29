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
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author asmateus
 */
public class LifeCounter extends JPanel 
{
    private int counter;
    
    public LifeCounter()
    {
        super(new GridLayout(2, 2));
        super.setBackground(Color.GRAY);
    }
    
    public void startLifeCounter()
    {
        this.reset();
        Label cell_1 = new Label("I");
        cell_1.setForeground(Color.BLACK);
        cell_1.setFont("resources/fonts/ARCADECLASSIC.ttf", 25.0f);
        cell_1.setHorizontalAlignment(SwingConstants.CENTER);
        
        Label cell_2 = new Label("P");
        cell_2.setForeground(Color.BLACK);
        cell_2.setFont("resources/fonts/ARCADECLASSIC.ttf", 25.0f);
        cell_2.setHorizontalAlignment(SwingConstants.CENTER);
        
        Label cell_4 = new Label(String.valueOf(counter));
        cell_4.setForeground(Color.BLACK);
        cell_4.setFont("resources/fonts/ARCADECLASSIC.ttf", 25.0f);
        cell_4.setHorizontalAlignment(SwingConstants.CENTER);
        
        this.add(cell_1);
        this.add(cell_2);
        this.add(new JLabel(new ImageIcon("resources/blocks/status/life.png")));
        this.add(cell_4);
    }
    
    private int getLifeCounter()
    {
        return 2;
    }
    
    private void reset()
    {
        counter = this.getLifeCounter();
    }
}
