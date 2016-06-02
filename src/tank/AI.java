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
import java.util.Random;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asmateus
 */
public class AI extends TimerTask
{
    public static final int CRAZY = 0;
    public static final int STUBBORN = 1;
    public static final int SNEAKY = 2;
    public static final int SUICIDE = 3;
    
    private Player subscriber;
    
    public AI(Player p)
    {
       this.subscriber = p;
    }
    
    @Override
    public void run()
    {
        decide(subscriber);
    }
    
    public void decide(Player subscriber)
    {
        switch(subscriber.personality) {
            case AI.CRAZY:
                decideCrazy(subscriber);
                break;
            case AI.SNEAKY:
                break;
            case AI.STUBBORN:
                break;
            case AI.SUICIDE:
                break;
        }
    }
    
    private void decideCrazy(Player subscriber)
    {
        Random rand = new Random();
        int  n = rand.nextInt(6) + 1;
        for(int i = 0; i < 50; ++i) {
            subscriber.masterIssuedOrder(n);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }           
    }
    
    private void decideSneaky()
    {
        
    }
    
    private void decideStubborn()
    {
        
    }
    
    private void decideSuicide()
    {
        
    }
}
