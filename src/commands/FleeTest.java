package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import groups.Formation;
import groups.Party;

import org.junit.Test;

import actors.Player;

/**
 * FleeTest
 * @author nhydock
 *
 *  JUnit test for flee command
 */
public class FleeTest
{

    /**
     * Tests flee command both against a formation that is escapable
     * and then one that isn't
     */
    @Test
    public void testFleeing()
    {
        Party party = new Party();
        party.add("Jack", "Red Mage");
        Player p = party.get(0);
        p.setLuck(100);             //best luck ever!!!
        
        Formation formation = new Formation();
        formation.add("Gel");
        
        FleeCommand c = new FleeCommand(p, formation.getAliveMembers());
        
        assertTrue(formation.getEscapable());
        
        p.setCommand(c);
        p.setMoving(3);
        
        //should always succeed
        for (int i = 0; i < 100; i++) 
        {
            p.execute();
            assertEquals(1, c.getHits());
        }
    
        formation.setEscapable(false);
        assertFalse(formation.getEscapable());
        
        //now it should always be failure where before it was always success
        for (int i = 0; i < 100; i++)
        {
            p.execute();
            assertEquals(0, c.getHits());
        }
    }
}
