package co.synonym.core;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;

public class SysonymFactoryTest {

	@Test
	public void testSynonymFinderComplete() {
		String type = "complete";
		SynonymFinder finder = SynonymFactory.getFinder(type);
		assertThat(finder, instanceOf(CompleteSynonymFinder.class));
	}
	
	@Test
	public void testSynonymFinderPartial() {
		String type = "partial";
		SynonymFinder finder = SynonymFactory.getFinder(type);
		assertThat(finder, instanceOf(PartialSynonymFinderTest.class));
	}
	
	@Test
	public void testSynonymFinderPartialEtc() {
		String type = "abc";
		SynonymFinder finder = SynonymFactory.getFinder(type);
		assertThat(finder, instanceOf(PartialSynonymFinderTest.class));
	}
}
