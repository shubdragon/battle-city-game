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


/**
 *
 * @author asmateus
 * The GraphDescriptor Class is a utility entity that provides 2 types of Graphs:
 *  A block graph, for holding the elements to be drawn in the PlayGround
 *  A weigh graph, for constructing optimum path in the AI of the Enemies
 * 
 */
public class GraphDescriptor implements Descriptor
{
    public String[][] im = new String[15][17];
    public List<List<BlockDescriptor>> block_graph = new ArrayList<>();
            
    public GraphDescriptor(String[][] input_matrix)
    {
        im = input_matrix;
    }
    
    @Override
    public void translate(Object o)
    {
        BlockDescriptor bck;
        for(int i = 0; i < 15; ++i) {
            block_graph.add(new ArrayList<>());
            for(int j = 0; j < 17; ++j) {
                bck = new BlockDescriptor();
                bck.translate(im[i][j]);
                block_graph.get(i).add(bck);
            }
        }
    }
}
