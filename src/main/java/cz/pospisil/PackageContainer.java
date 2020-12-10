package cz.pospisil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class PackageContainer {

    // Thread-safe ArrayList
    private final CopyOnWriteArrayList<Package> packages;

    public PackageContainer() {
        this.packages = new CopyOnWriteArrayList<>();
    }

    private Package getPackageFromInputLine(String line) {
        Scanner info = new Scanner(line);

        if (info.hasNext()) {
            // Weight
            String weight = info.next();
            // Postal Code
            if (info.hasNext()) {
                String postalCode = info.next();
                // must be 5-digit
                if (postalCode.length() != 5 || !postalCode.chars().allMatch(Character::isDigit)) {
                    printErrorUnrecognized("postal code", postalCode);
                    return null;
                }

                if (!info.hasNext()) {  // third element on input line is not allowed
                    try {
                        float fWeight = Float.parseFloat(weight);

                        return new Package(fWeight, postalCode);  // OK - store package info from input line

                    } catch (NumberFormatException e) {
                        printErrorUnrecognized("weight", weight);
                        return null;
                    }
                }
            }
        }
        printErrorUnrecognized("line", line);
        return null;
    }

    private void printErrorUnrecognized(String reason, String value) {
        System.err.println("Error: Unrecognized "+ reason +":");
        System.err.println("\"" + value + "\"");
        System.err.println("Record Ignored!");
    }

    public void addPackagesFromLine(String line) {
        Package pckg = getPackageFromInputLine(line);

        if (pckg != null) {
            this.packages.add(pckg);
        }
    }

    public void addPackagesFromFile(String fileName) {
        try (Scanner lines = new Scanner(new FileReader(fileName))) {

            while (lines.hasNext()) {
                String line = lines.nextLine();
                addPackagesFromLine(line);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: File with initial package information not found.");
            System.exit(1);
        }
    }

    public void printActualSummary() {
        if (packages.isEmpty()) {
            System.out.println("\nSummary: there is no package information added yet.\n");
            return;
        }

        System.out.println("\nSummary:");

        // Map<String, List<Package>>
        packages
                .stream()
                .collect(Collectors.groupingBy(Package::getPostalCode))
                .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(s -> ((Map.Entry<String, List<Package>>) s).getValue()
                        .stream()
                        .mapToDouble(Package::getWeight)
                        .sum()).reversed())
                        .forEach(m -> System.out.println(m.getKey() + " " + String.format(Locale.ROOT, "%4.3f", m.getValue()
                            .stream()
                            .mapToDouble(Package::getWeight)
                            .sum())));

        System.out.println();
    }

    public CopyOnWriteArrayList<Package> getPackages() {
        return packages;
    }
}
