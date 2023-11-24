import io.xlorey.FluxLoader.utils.VersionChecker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VersionCheckerTest {

    @Test
    void testIsVersionCompatibleEqual() {
        assertTrue(VersionChecker.isVersionCompatible(">=1.0.0", "1.0.0"));
        assertTrue(VersionChecker.isVersionCompatible(">=1.0.0", "1.0.0"));
        assertTrue(VersionChecker.isVersionCompatible("=1.0.0", "1.0.0"));
    }

    @Test
    void testIsVersionCompatibleHigher() {
        assertTrue(VersionChecker.isVersionCompatible(">=1.0.0", "1.0.1"));
        assertFalse(VersionChecker.isVersionCompatible(">=1.0.0", "0.0.1"));
        assertFalse(VersionChecker.isVersionCompatible(">1.0.0", "0.9.9"));
        assertTrue(VersionChecker.isVersionCompatible(">1.0.0", "1.1.1"));
    }

    @Test
    void testIsVersionCompatibleLower() {
        assertTrue(VersionChecker.isVersionCompatible("<=1.0.0", "0.9.9"));
        assertFalse(VersionChecker.isVersionCompatible("<=1.0.0", "1.0.1"));
        assertFalse(VersionChecker.isVersionCompatible("<1.0.0", "1.0.1"));
        assertTrue(VersionChecker.isVersionCompatible("<1.0.0", "0.1.1"));
    }

    @Test
    void testIsVersionCompatibleComplex() {
        assertTrue(VersionChecker.isVersionCompatible(">=1.2.3", "2.1.4"));
        assertFalse(VersionChecker.isVersionCompatible(">=2.2.3", "2.2.2"));
        assertTrue(VersionChecker.isVersionCompatible("<=2.2.3", "2.2.2"));
        assertFalse(VersionChecker.isVersionCompatible("<=2.2.3", "2.5.2"));
    }

    @Test
    void testIsVersionCompatibleInvalidFormat() {
        assertThrows(NumberFormatException.class, () -> {
            VersionChecker.isVersionCompatible(">=1..0", "1.0.0");
        });
    }
}
