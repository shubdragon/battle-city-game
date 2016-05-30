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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author asmateus
 * Handles the logic and look of menu items
 */
public class MenuOption extends JLabel 
{
    private String font_path = "";
    
    public MenuOption next_menu;
    
    public MenuOption(String name, GameArea area)
    {
        super(name);
    }
    
    public void formatFont(String path, float size)
    {
        if (font_path.equals(""))
            font_path = path;
        try {
            // Font imported from ufonts.com
            Font fonty = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            this.setFont(fonty.deriveFont(size));
        } catch (FontFormatException ex) {
            Logger.getLogger(GameArea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void formatFont(float size)
    {
        try {
            // Font imported from ufonts.com
            Font fonty = Font.createFont(Font.TRUETYPE_FONT, new File(font_path));
            this.setFont(fonty.deriveFont(size));
        } catch (FontFormatException ex) {
            Logger.getLogger(GameArea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
