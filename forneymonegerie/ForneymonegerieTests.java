package forneymonegerie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ForneymonegerieTests {

	Forneymonegerie fm;

	@Before
	public void init() {
		fm = new Forneymonegerie();
	}

	@Test
	public void testSize() {
		fm.collect("Dampymon");
		assertEquals(1, fm.size());
		fm.collect("Dampymon");
		assertEquals(2, fm.size());
		fm.collect("Burnymon");
		assertEquals(3, fm.size());
		assertEquals("Dampymon", fm.nth(0));
		assertEquals(2, fm.countType("Dampymon"));
		assertEquals(1, fm.countType("Burnymon"));
		fm.collect("Zappymon");
		assertEquals(4, fm.size());
		fm.collect("Dampymon");
		assertEquals(5, fm.size());

	}

	@Test
	public void testTypeSize() {
		fm.collect("Dampymon");
		fm.collect("Dampymon");
		assertEquals(1, fm.typeSize());
		fm.collect("Burnymon");
		assertEquals(2, fm.typeSize());
		fm.collect("Zappymon");
		fm.collect("Zappymon");
		fm.collect("Zappymon");
		assertEquals(3, fm.typeSize());
		fm.collect("Dampymon");
		assertEquals(3, fm.typeSize());

	}

	@Test
	public void testCollect() {
		fm.collect("Dampymon");
		fm.collect("Dampymon");
		fm.collect("Burnymon");
		assertTrue(fm.contains("Dampymon"));
		assertTrue(fm.contains("Burnymon"));
		assertEquals("Dampymon", fm.nth(0));
		assertEquals("Dampymon", fm.nth(1));
		assertEquals("Burnymon", fm.nth(2));
	}

	@Test
	public void testRelease() {
		fm.collect("Dampymon");
		fm.collect("Dampymon");
		assertEquals(2, fm.size());
		assertEquals(1, fm.typeSize());
		assertEquals("Dampymon", fm.nth(0));
		assertEquals("Dampymon", fm.nth(1));
		fm.release("Dampymon");
		assertEquals(1, fm.size());
		assertEquals(1, fm.typeSize());
		assertEquals("Dampymon", fm.nth(0));
		assertEquals(1, fm.countType("Dampymon"));
		try {
			assertEquals("Dampymon", fm.nth(1));
		} catch (IllegalArgumentException e) {
			assert (true);
		}
		fm.collect("Burnymon");
		fm.collect("Burnymon");
		assertEquals("Burnymon", fm.nth(1));
		assertEquals(3, fm.size());
		assertEquals(2, fm.typeSize());
		fm.release("Burnymon");
		assertEquals(2, fm.size());
		assertEquals(2, fm.typeSize());
		assertEquals(1, fm.countType("Burnymon"));

	}

	@Test
	public void testReleaseType() {
		fm.collect("Dampymon");
		fm.collect("Dampymon");
		fm.collect("Burnymon");
		assertEquals(3, fm.size());
		assertEquals(2, fm.typeSize());
		fm.releaseType("Dampymon");
		assertEquals(1, fm.size());
		assertEquals(1, fm.size());
		assertEquals(1, fm.typeSize());
		fm.releaseType("Burnymon");
		assertEquals(0, fm.size());
		assertEquals(0, fm.typeSize());
		assertTrue(fm.empty());
		fm.releaseType("Zappymon");
		assertEquals(0, fm.size());
		assertEquals(0, fm.typeSize());
	}

	@Test
	public void testCountType() {
		fm.collect("Dampymon");
		fm.collect("Dampymon");
		fm.collect("Burnymon");
		assertEquals(2, fm.countType("Dampymon"));
		assertEquals(1, fm.countType("Burnymon"));
		assertEquals(0, fm.countType("forneymon"));
		fm.releaseType("Dampymon");
		assertEquals(0, fm.countType("Dampymon"));
		fm.release("Burnymon");
		assertEquals(0, fm.countType("Burnymon"));

	}

	@Test
	public void testContains() {
		fm.collect("Dampymon");
		fm.collect("Dampymon");
		fm.collect("Burnymon");
		assertTrue(fm.contains("Dampymon"));
		assertTrue(fm.contains("Burnymon"));
		assertFalse(fm.contains("forneymon"));
		fm.releaseType("Dampymon");
		assertFalse(fm.contains("Dampymon"));
	}

	@Test
	public void testNth() {
		fm.collect("Dampymon");
		fm.collect("Burnymon");
		fm.collect("Zappymon");
		fm.collect("Dampymon");
		assertEquals("Dampymon", fm.nth(0));
		assertEquals("Dampymon", fm.nth(1));
		assertEquals("Burnymon", fm.nth(2));
		assertEquals("Zappymon", fm.nth(3));
		try {
			assertEquals("Zappymon", fm.nth(4));
		} catch (IllegalArgumentException e) {
			assert (true);
		}
	}

	@Test
	public void testRarestType() {
		fm.collect("Dampymon");
		fm.collect("Dampymon");
		fm.collect("Zappymon");
		assertEquals("Zappymon", fm.rarestType());
		fm.collect("Zappymon");
		assertEquals("Zappymon", fm.rarestType());
		fm.collect("Burnymon");
		assertEquals("Burnymon", fm.rarestType());
		fm.release("Dampymon");
		assertEquals("Burnymon", fm.rarestType());
	}

	@Test
	public void testClone() {
		fm.collect("Dampymon");
		fm.collect("Dampymon");
		fm.collect("Burnymon");
		Forneymonegerie dolly = fm.clone();
		assertEquals(dolly.countType("Dampymon"), 2);
		assertEquals(dolly.countType("Burnymon"), 1);
		dolly.collect("Zappymon");
		assertFalse(fm.contains("Zappymon"));
		fm.collect("Leafymon");
		fm.collect("Jumpymon");
		assertFalse(dolly.contains("Leafymon"));
		Forneymonegerie brad = fm.clone();
		assertTrue(brad.contains("Leafymon"));
		assertEquals("Dampymon", brad.nth(0));
		assertEquals(2, brad.countType("Dampymon"));
		assertEquals("Dampymon", brad.nth(1));
		assertEquals(1, brad.countType("Burnymon"));
		assertEquals("Burnymon", brad.nth(2));
		assertEquals("Leafymon", brad.nth(3));
		assertEquals(1, brad.countType("Leafymon"));
		assertEquals("Jumpymon", brad.nth(4));
		assertEquals(1, brad.countType("Jumpymon"));
	}

	@Test
	public void testTrade() {
		Forneymonegerie fm1 = new Forneymonegerie();
		fm1.collect("Dampymon");
		fm1.collect("Dampymon");
		fm1.collect("Burnymon");
		Forneymonegerie fm2 = new Forneymonegerie();
		fm2.collect("Zappymon");
		fm2.collect("Leafymon");
		fm1.trade(fm2);
		assertTrue(fm1.contains("Zappymon"));
		assertTrue(fm1.contains("Leafymon"));
		assertEquals("Zappymon", fm1.nth(0));
		assertEquals("Leafymon", fm1.nth(1));
		assertEquals(2, fm1.typeSize());
		assertEquals(2, fm1.size());
		assertTrue(fm2.contains("Dampymon"));
		assertTrue(fm2.contains("Burnymon"));
		assertFalse(fm1.contains("Dampymon"));
		assertEquals("Dampymon", fm2.nth(0));
		assertEquals(2, fm2.countType("Dampymon"));
		assertEquals("Dampymon", fm2.nth(1));
		assertEquals("Burnymon", fm2.nth(2));
		assertEquals(2, fm2.typeSize());
		assertEquals(3, fm2.size());
		assertTrue(!fm1.contains("Dampymon"));
		fm2.collect("Windymon");
		fm2.trade(fm1);
		assertTrue(fm2.contains("Zappymon"));
		assertTrue(fm2.contains("Leafymon"));
		assertTrue(fm1.contains("Dampymon"));
		assertTrue(fm1.contains("Burnymon"));
		assertTrue(fm1.contains("Windymon"));
		assertFalse(fm.contains("Windymon"));

	}

	@Test
	public void testDiffMon() {
		Forneymonegerie fm1 = new Forneymonegerie();
		fm1.collect("Dampymon");
		fm1.collect("Dampymon");
		fm1.collect("Burnymon");
		Forneymonegerie fm2 = new Forneymonegerie();
		fm2.collect("Dampymon");
		fm2.collect("Zappymon");
		Forneymonegerie fm3 = Forneymonegerie.diffMon(fm1, fm2);
		assertEquals(fm3.countType("Dampymon"), 1);
		assertEquals(fm3.countType("Burnymon"), 1);
		assertFalse(fm3.contains("Zappymon"));
		fm3.collect("Leafymon");
		assertTrue(fm3.contains("Leafymon"));
		assertFalse(fm1.contains("Leafymon"));
		assertFalse(fm2.contains("Leafymon"));
		Forneymonegerie fm4 = fm1.clone();
		fm4.collect("Dampymon");
		fm4.collect("Dampymon");
		fm4.collect("Dampymon");
		fm4.collect("Dampymon");
		Forneymonegerie fm5 = fm2.clone();
		fm5.collect("Dampymon");
		fm5.collect("Dampymon");
		fm5.collect("Burnymon");
		fm5.collect("Burnymon");
		fm5.collect("Zappymon");
		fm5.collect("Zappymon");
		fm5.collect("Zappymon");
		fm5.collect("Zappymon");
		Forneymonegerie fm6 = Forneymonegerie.diffMon(fm4, fm5);
		assertEquals(fm6.countType("Dampymon"), 3);
		assertFalse(fm6.contains("Burnymon"));
		assertFalse(fm6.contains("Zappymon"));
		fm4.collect("Zappymon");
		fm4.collect("Zappymon");
		fm4.collect("Zappymon");
		fm4.collect("Zappymon");
		fm4.collect("Zappymon");
		fm4.collect("Leafymon");
		fm4.collect("Leafymon");
		fm4.collect("Leafymon");
		fm4.collect("Leafymon");
		fm4.collect("Leafymon");
		fm4.collect("Leafymon");
		fm5.collect("Leafymon");
		fm5.collect("Leafymon");
		Forneymonegerie fm7 = Forneymonegerie.diffMon(fm5, fm4);
		assertEquals(fm7.countType("Burnymon"), 1);
		assertFalse(fm7.contains("Dampymon"));
		assertFalse(fm7.contains("Zappymon"));
		assertFalse(fm7.contains("Leafymon"));

	}

	@Test
	public void testSameForneymonegerie() {
		Forneymonegerie fm1 = new Forneymonegerie();
		fm1.collect("Dampymon");
		fm1.collect("Dampymon");
		fm1.collect("Burnymon");
		Forneymonegerie fm2 = new Forneymonegerie();
		fm2.collect("Burnymon");
		fm2.collect("Dampymon");
		fm2.collect("Dampymon");
		assertTrue(Forneymonegerie.sameCollection(fm1, fm2));
		assertTrue(Forneymonegerie.sameCollection(fm2, fm1));
		fm2.collect("Leafymon");
		assertFalse(Forneymonegerie.sameCollection(fm1, fm2));
		fm2.releaseType("Leafymon");
		assertTrue(Forneymonegerie.sameCollection(fm1, fm2));
		fm2.collect("Dampymon");
		assertFalse(Forneymonegerie.sameCollection(fm1, fm2));
	}

	@Test
	public void checkAndGrow() {
		fm.collect("Dampymon");
		fm.collect("Zappymon");
		fm.collect("Raymon");
		fm.collect("Leafymon");
		fm.collect("Burnymon");
		fm.collect("Jumpymon");
		fm.collect("Flowerymon");
		fm.collect("Grassymon");
		fm.collect("Rockymon");
		fm.collect("Waterymon");
		fm.collect("Dirtymon");
		fm.collect("Cloudymon");
		fm.collect("Skymon");
		fm.collect("Bossmon");
		fm.collect("Treemon");
		fm.collect("Godmon");
		fm.collect("Deadmon");
		fm.collect("Zaccariah");
		fm.collect("Coby");
		assertEquals("Zaccariah", fm.nth(17));
		assertEquals("Coby", fm.nth(18));
	}

}
