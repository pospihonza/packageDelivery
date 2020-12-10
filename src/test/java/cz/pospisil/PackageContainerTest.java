package cz.pospisil;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PackageContainerTest {

    @Test
    void addPackagesFromLine() {
        PackageContainer packageContainer = new PackageContainer();
        String inputLine = "2.6 12345";

        packageContainer.addPackagesFromLine(inputLine);

        assertEquals(1, packageContainer.getPackages().size());
        assertEquals(2.6f, packageContainer.getPackages().get(0).getWeight());
        assertEquals("12345", packageContainer.getPackages().get(0).getPostalCode());
    }
}