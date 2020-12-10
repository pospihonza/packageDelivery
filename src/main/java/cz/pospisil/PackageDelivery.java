package cz.pospisil;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class PackageDelivery {

    public static void main(String[] args)  throws InterruptedException {

        PackageContainer packageContainer = new PackageContainer();

        // if specified command line argument with filename, load initial package information
        if (args.length == 1) {
            packageContainer.addPackagesFromFile(args[0]);
        }

        // every 60 seconds print summary
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                packageContainer.printActualSummary();
            }
        }, 1000 * 10, 1000 * 10);

        Scanner in = new Scanner(System.in);

        while (true) {
            String inputLine = in.nextLine();

            if ("quit".equalsIgnoreCase(inputLine)) {
                System.exit(0);
            }

            packageContainer.addPackagesFromLine(inputLine);
        }
    }

}
