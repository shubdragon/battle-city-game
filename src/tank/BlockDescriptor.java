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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author asmateus
 */
public class BlockDescriptor extends JPanel implements Descriptor
{
    public List<String> imgs;
    public String code = "";
    public int block_weight = 0;
    
    public BlockDescriptor()
    {
        super(new GridBagLayout());
        super.setBackground(Color.BLACK);
        this.imgs = new ArrayList<>();
        
    }
    
    @Override
    public void translate(Object o)
    {
        code = (String) o;
        GridBagConstraints c = new GridBagConstraints();
        
        // Convert codes to urls
        for(char ch: code.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                imgs.add(decisor(ch));
                break;
            }
            if(ch == ' ')
                imgs.add(decisor('v'));
            else
                imgs.add(decisor(ch));
        }
        
        // Convert url into paintable block object
        c.ipadx = 0;
        c.ipady = 0;
        if(imgs.size() == 1) {
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 2;
            this.add(new JLabel(new ImageIcon(imgs.get(0))), c);
        }
        else {
            c.gridwidth = 1;
            c.gridheight = 1;
            for(int i = 0, j = 0; i < imgs.size(); ++i) {
                if(i > 1) {
                    c.gridx = j;
                    c.gridy = 1;
                    ++j;
                }
                else {
                    c.gridx = i;
                    c.gridy = 0;
                }
                this.add(new JLabel(new ImageIcon(imgs.get(i))), c);
            }
        }
    }
    
    public String decisor(char code)
    {
        String uri = "resources/blocks/terrain/";
        switch(code) {
            case 'I':
                uri += "brick.png";
                this.block_weight = 4;
                break;
            case 'J':
                uri += "steel.png";
                this.block_weight = 400;
                break;
            case 'K':
                uri += "iron.png";
                this.block_weight = 300;
                break;
            case 'L':
                uri += "forest.png";
                this.block_weight = 0;
                break;
            case 'M':
                uri += "sea.png";
                this.block_weight = Integer.MAX_VALUE;
                break;
            case 'O':
                uri += "deepsea.png";
                this.block_weight = Integer.MAX_VALUE;
                break;
            case 'V':
                uri += "void.png";
                this.block_weight = 0;
                break;
            case 'X':
                uri += "eagle.png";
                this.block_weight = 1;
                break;
            case 'Z':
                uri += "deadflag.png";
                this.block_weight = Integer.MAX_VALUE;
                break;
            
            case 'i':
                uri += "small_brick.png";
                this.block_weight += 1;
                break;
            case 'j':
                uri += "small_steel.png";
                this.block_weight += 100;
                break;
            case 'k':
                uri += "small_iron.png";
                this.block_weight += 75;
                break;
            case 'l':
                uri += "small_forest.png";
                this.block_weight += 0;
                break;
            case 'm':
                uri += "small_sea.png";
                this.block_weight = Integer.MAX_VALUE;
                break;
            case 'o':
                uri += "small_deepsea.png";
                this.block_weight = Integer.MAX_VALUE;
                break;
            case 'v':
                uri += "small_void.png";
                this.block_weight += 0;
                break;
                
            default:
                uri = "";
                break;
        }
        
        return uri;
    }
}
