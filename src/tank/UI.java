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

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author asmateus
 */

public class UI extends JFrame
{
    private boolean waiting = false;
    private JLabel currentDM = new JLabel();
    private boolean isFullScreen = false;
 
    public static final int INDEX_WIDTH = 0;
    public static final int INDEX_HEIGHT = 1;
    public static final int INDEX_BITDEPTH = 2;
    public static final int INDEX_REFRESHRATE = 3;
    
    private final DisplayMode DISPLAY_MODE;
    private final GraphicsDevice DEVICE;
    
    public static final int[] COLUMN_WIDTHS = new int[] {
        100, 100, 100, 100
    };
    public static final String[] COLUMN_NAMES = new String[] {
        "Width", "Height", "Bit Depth", "Refresh Rate"
    };
 
    public UI(GraphicsDevice device)
    {
        super(device.getDefaultConfiguration());
        this.DEVICE = device;
        DISPLAY_MODE = device.getDisplayMode();
    }
 
    protected void initComponents(Container c)
    {
        // Set Stuffs of the UI
        setTitle("Tank Game");
        //setDMLabel(DISPLAY_MODE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        c.setLayout(new BorderLayout());
        c.setBackground(Color.BLACK);
        
        setContentPane(c);
        
         // Set Stuffs of fancy panel, uncomment for screen information
        //JPanel fancy_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //fancy_panel.setBackground(Color.BLACK);
        //c.add(fancy_panel, BorderLayout.NORTH);
        //JLabel current = new JLabel("Current Display Mode : ");
        //current.setForeground(Color.WHITE);
        //currentDM.setForeground(Color.WHITE);
        //fancy_panel.add(current);
        //fancy_panel.add(currentDM);
        
        // JPanel where the game will be displayed
    }
 
    public void setDMLabel(DisplayMode newMode)
    {
        int bitDepth = newMode.getBitDepth();
        int refreshRate = newMode.getRefreshRate();
        String bd, rr;
        if (bitDepth == DisplayMode.BIT_DEPTH_MULTI) {
            bd = "Multi";
        } else {
            bd = Integer.toString(bitDepth);
        }
        if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
            rr = "Unknown";
        } else {
            rr = Integer.toString(refreshRate);
        }
        currentDM.setText(
            COLUMN_NAMES[INDEX_WIDTH] + ": " + newMode.getWidth() + " "
            + COLUMN_NAMES[INDEX_HEIGHT] + ": " + newMode.getHeight() + " "
            + COLUMN_NAMES[INDEX_BITDEPTH] + ": " + bd + " "
            + COLUMN_NAMES[INDEX_REFRESHRATE] + ": " + rr
            );
    }
 
    public void begin()
    {
        isFullScreen = DEVICE.isFullScreenSupported();
        setUndecorated(isFullScreen);
        setResizable(!isFullScreen);
        if (isFullScreen) {
            // Full-screen mode
            DEVICE.setFullScreenWindow(this);
            validate();
        } else {
            // Windowed mode
            pack();
            setVisible(true);
        }
    }
}