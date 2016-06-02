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
    public Integer[][] weight_graph = new Integer[34][38];
            
    public GraphDescriptor(String[][] input_matrix)
    {
        im = input_matrix;
        initializeWeightGraph();
    }
    
    private void initializeWeightGraph()
    {
        for(int i = 0; i < 34; ++i)
            for(int j = 0; j < 38; ++j)
                this.weight_graph[i][j] = 0;
    }
    
    public void createWeightGraph()
    {
        // Adding normal cells
        char[] code = new char[4];
        for(int i = 0, k = 2; i < block_graph.size(); ++i, k += 2) {
            for(int j = 0, h = 2; j < block_graph.get(i).size(); ++j, h += 2) {
                code = this.block_graph.get(i).get(j).code.toCharArray();
                if(Character.isUpperCase(code[0])) {
                    this.weight_graph[k][h] = weightDecisor(Character.toLowerCase(code[0]));
                    this.weight_graph[k][h + 1] = weightDecisor(Character.toLowerCase(code[0]));
                    this.weight_graph[k + 1][h] = weightDecisor(Character.toLowerCase(code[0]));
                    this.weight_graph[k + 1][h + 1] = weightDecisor(Character.toLowerCase(code[0]));
                }
                else {
                    this.weight_graph[k][h] = weightDecisor(code[0]);
                    this.weight_graph[k][h + 1] = weightDecisor(code[1]);
                }
            }
            for(int j = 0, h = 2; j < block_graph.get(i).size(); ++j, h += 2) {
                code = this.block_graph.get(i).get(j).code.toCharArray();
                if(!Character.isUpperCase(code[0])) {
                    this.weight_graph[k + 1][h] = weightDecisor(code[2]);
                    this.weight_graph[k + 1][h + 1] = weightDecisor(code[3]);
                }
            }
        }
        
        // Adding bedrock
        for(int j = 0; j < 38; ++j) {
            this.weight_graph[0][j] = Integer.MAX_VALUE;
            this.weight_graph[33][j] = Integer.MAX_VALUE;
            this.weight_graph[1][j] = Integer.MAX_VALUE;
            this.weight_graph[32][j] = Integer.MAX_VALUE;
        }
        for(int i = 0; i < 34; ++i) {
            this.weight_graph[i][0] = Integer.MAX_VALUE;
            this.weight_graph[i][37] = Integer.MAX_VALUE;
            this.weight_graph[i][1] = Integer.MAX_VALUE;
            this.weight_graph[i][36] = Integer.MAX_VALUE;
        }
    }
    
    private int weightDecisor(char c)
    {
        switch(c) {
            case 'x':
                return 2;
            case 'z':
                return -2;
            case 'i':
                return 1;
            case 'j':
                return 5;
            case 'k':
                return 4;
            case 'l':
                return 0;
            case 'm':
                return 3;        
            case 'o':
                return 3;
            case 'v':
                return 0;
        }
        
        return 0;
    }
    
    @Override
    public void translate(Object o)
    {
        block_graph.clear();
        BlockDescriptor bck;
        for(int i = 0; i < 15; ++i) {
            block_graph.add(new ArrayList<>());
            for(int j = 0; j < 17; ++j) {
                bck = new BlockDescriptor();
                bck.translate(im[i][j]);
                block_graph.get(i).add(bck);
            }
        }
        this.createWeightGraph();
    }
}
