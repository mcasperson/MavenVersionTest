package org.apache.maven.artifact.versioning;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * https://octopus.com/blog/maven-versioning-explained
 */
public class VersionTest {

    private static final ComparableVersion[] VERSIONS = new ComparableVersion[]{
            new ComparableVersion("NotAVersionSting"),
            new ComparableVersion("1.0-alpha"),
            new ComparableVersion("1.0a1-SNAPSHOT"),
            new ComparableVersion("1.0-alpha1"),
            new ComparableVersion("1.0beta1-SNAPSHOT"),
            new ComparableVersion("1.0-b2"),
            new ComparableVersion("1.0-beta3.SNAPSHOT"),
            new ComparableVersion("1.0-beta3"),
            new ComparableVersion("1.0-milestone1-SNAPSHOT"),
            new ComparableVersion("1.0-m2"),
            new ComparableVersion("1.0-rc1-SNAPSHOT"),
            new ComparableVersion("1.0-cr1"),
            new ComparableVersion("1.0-SNAPSHOT"),
            new ComparableVersion("1.0"),
            new ComparableVersion("1.0-RELEASE"),
            new ComparableVersion("1.0-sp"),
            new ComparableVersion("1.0-a"),
            new ComparableVersion("1.0-whatever"),
            new ComparableVersion("1.0.z"),
            new ComparableVersion("1.0.1"),
            new ComparableVersion("1.0.1.0.0.0.0.0.0.0.0.0.0.0.1")

    };

    @Test
    public void ensureArrayInOrder() {
        ComparableVersion[] sortedArray = VERSIONS.clone();
        Arrays.sort(sortedArray);
        assertArrayEquals(VERSIONS, sortedArray);
    }

    @Test
    public void testAliases() {
        assertEquals(new ComparableVersion("1.0-alpha1"), new ComparableVersion("1.0-a1"));
        assertEquals(new ComparableVersion("1.0-beta1"), new ComparableVersion("1.0-b1"));
        assertEquals(new ComparableVersion("1.0-milestone1"), new ComparableVersion("1.0-m1"));
        assertEquals(new ComparableVersion("1.0-rc1"), new ComparableVersion("1.0-cr1"));
    }

    @Test
    public void testDifferentFinalReleases() {
        assertEquals(new ComparableVersion("1.0-ga"), new ComparableVersion("1.0"));
        assertEquals(new ComparableVersion("1.0-final"), new ComparableVersion("1.0"));
    }

    @Test
    public void testQualifierOnly() {
        assertTrue(new ComparableVersion("SomeRandomVersionOne").compareTo(
                new ComparableVersion("SOMERANDOMVERSIONTWO")) < 0);
        assertTrue(new ComparableVersion("SomeRandomVersionThree").compareTo(
                new ComparableVersion("SOMERANDOMVERSIONTWO")) < 0);
    }

    @Test
    public void testSeparators() {
        assertEquals(new ComparableVersion("1.0alpha1"), new ComparableVersion("1.0-a1"));
        assertEquals(new ComparableVersion("1.0alpha-1"), new ComparableVersion("1.0-a1"));
        assertEquals(new ComparableVersion("1.0beta1"), new ComparableVersion("1.0-b1"));
        assertEquals(new ComparableVersion("1.0beta-1"), new ComparableVersion("1.0-b1"));
        assertEquals(new ComparableVersion("1.0milestone1"), new ComparableVersion("1.0-m1"));
        assertEquals(new ComparableVersion("1.0milestone-1"), new ComparableVersion("1.0-m1"));
        assertEquals(new ComparableVersion("1.0rc1"), new ComparableVersion("1.0-cr1"));
        assertEquals(new ComparableVersion("1.0rc-1"), new ComparableVersion("1.0-cr1"));
        assertEquals(new ComparableVersion("1.0ga"), new ComparableVersion("1.0"));
    }

    @Test
    public void testUnequalSeparators() {
        assertNotEquals(new ComparableVersion("1.0alpha.1"), new ComparableVersion("1.0-a1"));
    }

    @Test
    public void testCase() {
        assertEquals(new ComparableVersion("1.0ALPHA1"), new ComparableVersion("1.0-a1"));
        assertEquals(new ComparableVersion("1.0Alpha1"), new ComparableVersion("1.0-a1"));
        assertEquals(new ComparableVersion("1.0AlphA1"), new ComparableVersion("1.0-a1"));
        assertEquals(new ComparableVersion("1.0BETA1"), new ComparableVersion("1.0-b1"));
        assertEquals(new ComparableVersion("1.0MILESTONE1"), new ComparableVersion("1.0-m1"));
        assertEquals(new ComparableVersion("1.0RC1"), new ComparableVersion("1.0-cr1"));
        assertEquals(new ComparableVersion("1.0GA"), new ComparableVersion("1.0"));
        assertEquals(new ComparableVersion("1.0FINAL"), new ComparableVersion("1.0"));
        assertEquals(new ComparableVersion("1.0-SNAPSHOT"), new ComparableVersion("1-snapshot"));
    }

    @Test
    public void testLongVersions() {
        assertEquals(new ComparableVersion("1.0.0.0.0.0.0"), new ComparableVersion("1"));
        assertEquals(new ComparableVersion("1.0.0.0.0.0.0x"), new ComparableVersion("1x"));
    }

    @Test
    public void testDashAndPeriod() {
        assertEquals(new ComparableVersion("1-0.ga"), new ComparableVersion("1.0"));
        assertEquals(new ComparableVersion("1.0-final"), new ComparableVersion("1.0"));
        assertEquals(new ComparableVersion("1-0-ga"), new ComparableVersion("1.0"));
        assertEquals(new ComparableVersion("1-0-final"), new ComparableVersion("1-0"));
        assertEquals(new ComparableVersion("1-0"), new ComparableVersion("1.0"));
    }

    /**
     * Testing various scenarios where feature branches are embedded into the version. These
     * don't produce the results you might expect.
     */
    @Test
    public void testFeatureBranches() {
        assertTrue(new ComparableVersion("1.2.1-ga").compareTo(
                new ComparableVersion("1.2.1.myfeature-ga")) < 0);

        assertTrue(new ComparableVersion("1.2.1").compareTo(
                new ComparableVersion("1.2.1.myfeature")) < 0);

        assertTrue(new ComparableVersion("1.2.1").compareTo(
                new ComparableVersion("1.2.1-myfeature")) < 0);

        assertTrue(new ComparableVersion("1.2.1-ga").compareTo(
                new ComparableVersion("1.2.1-zyfeature")) < 0);
    }
}