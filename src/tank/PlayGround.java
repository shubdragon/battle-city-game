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
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author asmateus
 * PlayGround class has to update its own elements, for that it counts of tree
 * different kind of graphs, all provided by GraphDescriptor Class
 * 
 */
public class PlayGround extends JPanel 
{
    private final GridBagConstraints c = new GridBagConstraints();
    private final List<List<BlockDescriptor>> block_graph;
    
    public PlayGround(GraphDescriptor g_descriptor)
    {
        super(new GridLayout(15, 17));
        super.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        setGBC();
        
        super.setVisible(true);
        
        // Adding Graphs
        block_graph = g_descriptor.block_graph;
    }
    
    private void setGBC()
    {
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 20;
        c.ipady = 0;
        c.ipadx = 0;
    }
    
    public GridBagConstraints getPGConstraints()
    {
        return c;
    }
    
    public void fillPlayGround()
    {
        for(int i = 0; i < block_graph.size(); ++i) {
            for(int j = 0; j < block_graph.get(i).size(); ++j)
                this.add(block_graph.get(i).get(j));
        }
    }
}
