package ADS.Semester2.Lab10;

import java.util.ArrayList;
import java.util.List;

public class EggDropSolver {

    public int minAttempts(int floors) {
        int attempts = 0;
        int coveredFloors = 0;

        while (coveredFloors < floors) {
            attempts++;
            coveredFloors += attempts;
        }

        return attempts;
    }

    public List<Integer> buildStrategy(int floors) {
        List<Integer> strategy = new ArrayList<>();

        int currentFloor = 0;
        int step = minAttempts(floors);

        while (currentFloor < floors && step > 0) {
            currentFloor = Math.min(floors, currentFloor + step);
            strategy.add(currentFloor);
            step--;
        }

        return strategy;
    }

    public SearchResult findCriticalFloor(int floors, int criticalFloor, boolean verbose) {
        if (floors <= 0) {
            throw new IllegalArgumentException("Количество этажей должно быть положительным.");
        }

        if (criticalFloor < 1 || criticalFloor > floors) {
            throw new IllegalArgumentException("N должно быть в диапазоне от 1 до " + floors);
        }

        int step = minAttempts(floors);
        int previousSafeFloor = 0;
        int currentFloor = 0;
        int throwsCount = 0;
        StringBuilder log = new StringBuilder();

        while (step > 0 && currentFloor < floors) {
            currentFloor = Math.min(floors, currentFloor + step);
            throwsCount++;

            if (verbose) {
                log.append("Бросок ").append(throwsCount)
                        .append(": 1-е яйцо с ").append(currentFloor)
                        .append("-го этажа -> ");
            }

            if (currentFloor >= criticalFloor) {
                if (verbose) {
                    log.append("разбилось\n");
                }

                for (int floor = previousSafeFloor + 1; floor < currentFloor; floor++) {
                    throwsCount++;

                    if (verbose) {
                        log.append("Бросок ").append(throwsCount)
                                .append(": 2-е яйцо с ").append(floor)
                                .append("-го этажа -> ");
                    }

                    if (floor >= criticalFloor) {
                        if (verbose) {
                            log.append("разбилось\n");
                        }
                        return new SearchResult(floor, throwsCount, log.toString());
                    } else {
                        if (verbose) {
                            log.append("не разбилось\n");
                        }
                    }
                }

                return new SearchResult(currentFloor, throwsCount, log.toString());
            } else {
                if (verbose) {
                    log.append("не разбилось\n");
                }

                previousSafeFloor = currentFloor;
                step--;
            }
        }

        return new SearchResult(floors, throwsCount, log.toString());
    }
}