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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author asmateus
 * This Linker class creates event-listeners that will trigger different kind
 * of actions.
 */
public class Linker extends JComponent implements KeyListener
{
    private final GameArea container;
    private final List<Subscriber> subscribers;
    private final List<Subscriber> unsubscribed  = new ArrayList<>();
    
    public Linker(GameArea container)
    {
        this.container = container;
        this.subscribers = new ArrayList<>();
    }
    
    public void addSubscriber(Subscriber subscriber)
    {
        this.subscribers.add(subscriber);
    }
    
    public void removeSubscriber(Subscriber subscriber)
    {
        this.unsubscribed.add(subscriber);
    }
    
    private void refactorSubscribers()
    {
        this.unsubscribed.stream().forEach((subs) -> {
            this.subscribers.remove(subs);
        });
    }
    
    @Override
    public void keyTyped(KeyEvent e) 
    {
        subscribers.stream().forEach((subscriber) -> {
            subscriber.RESPONSE_CODES.stream().forEach((code) -> {
                if(code == 1000 + e.getKeyCode())
                    subscriber.masterIssuedOrder(1000 + e.getKeyCode());
            });
        });
    }
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        
        subscribers.stream().forEach((subscriber) -> {
            subscriber.RESPONSE_CODES.stream().forEach((code) -> {
                if(code == 2000 + e.getKeyCode())
                    subscriber.masterIssuedOrder(2000 + e.getKeyCode());
            });
        });
    }
    
    @Override
    public void keyReleased(KeyEvent e) 
    {
        Iterator<Subscriber> iter = subscribers.iterator();
        while(iter.hasNext()) {
            Subscriber elem = iter.next();
            elem.RESPONSE_CODES.stream().forEach((code) -> {
                if(code == 3000 + e.getKeyCode())
                    elem.masterIssuedOrder(3000 + e.getKeyCode());
            });
        }
        if(!this.unsubscribed.isEmpty()) {
            this.refactorSubscribers();
            this.unsubscribed.clear();
        }
    }
}
