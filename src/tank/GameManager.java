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
 * This class manages the game play, making it independent from the rest of the
 * application, requiring only the Game Area.
 * 
 */
public class GameManager
{
    private final GameArea area;
    private final List<Player> players = new ArrayList<>();
    
    // Local player code
    private final int LOCAL = 1000;
    private final int FOREIGN = 1001;
    
    private int game_mode = 0;
    
    public GameManager(GameArea area)
    {
        this.area = area;
    }
    
    public void startGameSession()
    {
        // Repaint Game Area
        resetGameArea();
        
        System.out.println("Game Manager Started in mode: " + game_mode);
        if(game_mode == 0) {
            singlePlayerScheme();
        }
        else if (game_mode == 1) {
            multiPlayerScheme();
        }
    }
    
    public void setGameMode(int game_mode)
    {
        this.game_mode = game_mode;
    }
    
    public void addPlayerToParty(int party, Player player)
    {
        players.add(player);
    }
    
    private void resetGameArea()
    {
        area.removeAll();
        area.repaint();
    }
    
    private boolean lookForPlayerWorlds()
    {
        return false;
    }
    
    private int getCurrentLevel()
    {
        return 1;
    }
    
    private void getWorldMatrix(int[][] matrix, int level)
    {
        
    }
    
    private void singlePlayerScheme()
    {
        if(players.isEmpty())
            addPlayerToParty(0, new Player(LOCAL));
        if(lookForPlayerWorlds())
            System.out.println("fdsk");
        else {
            
            // Get the world map, in the form of a matrix
            int[][] world_matrix = null;
            getWorldMatrix(world_matrix, getCurrentLevel());
            
            // Generate a GraphDescriptor object with the elements of the matrix
            GraphDescriptor graph = new GraphDescriptor(world_matrix);
            
            //Create World with graph information
            World world = new World(graph);
            world.start();
        }    
    }
    
    private void multiPlayerScheme()
    {
        
    }
}
