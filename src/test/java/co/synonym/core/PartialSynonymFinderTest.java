package co.synonym.core;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PartialSynonymFinderTest {
	static String[] dict = { "bad telephone number", "bad telephone", "bad number", "error type", "ws context",
			"ws enterprise logging", "fault url", "forcecomplete process", "edge forcecomplete process", "number",
			"jobdetail process", "service code info", "tip to ground resistance4", "price plan info",
			"price plan attributes", "company owned ip", "employer name", "combined billing indicator",
			"circuit id", "technology type", "single user code", "default apn indicator", "billed sms summary",
			"billed summary 2 month previous", "ring to ground resistance", "service type name",
			"unit of measure" };;
	
	@BeforeClass
	public static void setUp() {
	}
	
	@Before
	public void setUpEach() {
		
	}
	
	
	@Test
	public void findPartialDist() {
		String input = "telephone";
		int dist = SynonymFactory.getFinder("partial").phraseDistance(input, "bad telephone number");
		assertEquals(2, dist);
	}
	
	@AfterClass
	public static void destoryClass() {
		dict = null;
	}

}
