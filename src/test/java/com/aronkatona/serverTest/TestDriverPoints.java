package com.aronkatona.serverTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.aronkatona.server.DriverPoints;
import com.aronkatona.server.SessionLoader;
import com.aronkatona.server.TeamPoints;

/**
 * Unit test for simple App.
 */
public class TestDriverPoints    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestDriverPoints( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestDriverPoints.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testDriverPoints(){
    	DriverPoints dp0 = new DriverPoints("asd",0);
    	assertEquals(0, dp0.getPoint());
    	DriverPoints dp1 = new DriverPoints("bsd", 1);
    	assertEquals(25, dp1.getPoint());
    	DriverPoints dp10 = new DriverPoints("csd",10);
    	assertEquals(1, dp10.getPoint());
    	DriverPoints dp11 = new DriverPoints("dsd",11);
    	assertEquals(0, dp11.getPoint());
    }
    
    public void testTeamPoints(){
    	TeamPoints tp0 = new TeamPoints("asd",0);
    	assertEquals(0, tp0.getPoint());
    	TeamPoints tp1 = new TeamPoints("asd",1);
    	assertEquals(25, tp1.getPoint());
    	TeamPoints tp10 = new TeamPoints("asd",10);
    	assertEquals(1, tp10.getPoint());
    	TeamPoints tp11 = new TeamPoints("asd",11);
    	assertEquals(0, tp11.getPoint());
    }
    
    
    public void testSingletonSession(){
    	assertEquals(true, SessionLoader.getInstance());
    	assertEquals(false, SessionLoader.getInstance());
    	assertEquals(false, SessionLoader.getInstance());
    	assertEquals(false, SessionLoader.getInstance());
    }

    
    
}
