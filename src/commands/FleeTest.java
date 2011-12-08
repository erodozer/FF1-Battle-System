package commands;

import static org.junit.Assert.*;
import engine.Engine;
import groups.Formation;
import groups.Party;

import org.junit.Test;

import actors.Player;
import scenes.BattleScene.BattleScene;
import scenes.BattleScene.BattleSystem.*;

/**
 * FleeTest
 * @author nhydock
 *
 *  JUnit test for flee command
 */
public class FleeTest
{

    /**
     * Tests to make sure that elemental settings actually
     * affect damage output.
     * In this case, the enemy will be strong against fire,
     * weak against elec, and neutral against frez
     */
    @Test
    public void testFleeingAgainstInEscapableFormation()
    {
        Engine e = Engine.getInstance();
        Party party = new Party();
        party.add("Jack", "Red Mage");
        Player p = party.get(0);
        p.setLuck(100);             //best luck ever!!!
        Flee c = new Flee(p);
        
        e.setParty(party);
        
        Formation formation = new Formation();
        formation.add("Gel");
        
        e.changeToBattle(formation);
        
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
        assertFalse(((BattleSystem)((BattleScene)e.getCurrentScene()).getSystem()).getFormation().getEscapable());
        
        //now it should always be failure where before it was always success
        for (int i = 0; i < 100; i++)
        {
            p.execute();
            assertEquals(0, c.getHits());
        }
    }
}
