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
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
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
    
    private final String WORLD_PATH = "data/worlds/";
    private final List<String> LEVEL_NAMES = new ArrayList<>();
    
    private int game_mode = 0;
    
    public GameManager(GameArea area)
    {
        this.area = area;
        setLevelNames();
    }
    
    private void setLevelNames()
    {
        LEVEL_NAMES.add("null");
        LEVEL_NAMES.add("Noob land");
        LEVEL_NAMES.add("The enemies' fortress");
        LEVEL_NAMES.add("BadLands");
        LEVEL_NAMES.add("The creeper");
        LEVEL_NAMES.add("Silent River");
        LEVEL_NAMES.add("Cliffs");
        LEVEL_NAMES.add("Metal Fever");
        LEVEL_NAMES.add("The Swamp");
        LEVEL_NAMES.add("Monoliths");
        LEVEL_NAMES.add("Mischievous forest");
        LEVEL_NAMES.add("Evil's Mouth");
        LEVEL_NAMES.add("Iron Cage");
        LEVEL_NAMES.add("Blood Chain");
        LEVEL_NAMES.add("Sordid Moorland");
        LEVEL_NAMES.add("True Master");
        LEVEL_NAMES.add("Final Stage");
    }
    
    public void startGameSession()
    {
        // Repaint Game Area
        resetGameArea();
        
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
        player.setParty(party);
        players.add(player);
    }
    
    private void resetGameArea()
    {
        area.requestFocus();
        area.removeAll();
        this.area.resetKeyboardActions();
        area.setBackground(Color.GRAY);
        area.repaint();
    }
    
    private boolean lookForPlayerWorlds(String player_name)
    {
        /* 
         * Check if there is a database file in the user's worlds directory,
         * no need to check inside the file, as this is created just when
         * there is a finalized world construction.
        */
        File temp = new File("data/worlds/" + player_name + ".db");
        if(temp.isFile())
            return true;
        else
            return false;
    }
    
    private int getCurrentLevel()
    {
        return 1;
    }
    
    private void getWorldMatrix(String[][] matrix, int level)
    {
        // Stablish a connection with the SQLite db at the given path
        String db_path = "jdbc:sqlite:" + WORLD_PATH + "levels.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_path);
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        // Construct connection string
        String command = "SELECT * FROM [" + level + ":" + LEVEL_NAMES.get(level) + "]";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(command);
            int i = 0;
            while(rs.next()) {
                for(int j = 1; j < rs.getMetaData().getColumnCount() + 1; ++j)
                    matrix[i][j - 1] = rs.getString(j);
                ++i;
            }
        } 
        catch (SQLException e) {
            System.out.println(e);
        }
        
    }
    
    private void singlePlayerScheme()
    {
        if(players.isEmpty())
            addPlayerToParty(0, new Player(LOCAL));
        if(lookForPlayerWorlds(players.get(0).getName()))
            new WorldSelector(area).loadWorldOptions(game_mode);
        else {
            // Get the world map, in the form of a matrix
            String[][] world_matrix = new String[15][17];
            getWorldMatrix(world_matrix, this.getCurrentLevel());

            // Generate a GraphDescriptor object with the elements of the matrix
            GraphDescriptor graph = new GraphDescriptor(world_matrix);
            graph.translate(null);
            
            //Create World with graph information
            World world = new World(LEVEL_NAMES.get(this.getCurrentLevel()), graph, area);
            world.start();
        }    
    }
    
    private void multiPlayerScheme()
    {
        
    }
}
