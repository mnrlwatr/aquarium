package org.fp.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.DependencyContainer;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Position;
import org.fp.model.fish.Fish;
import org.fp.service.creation.RandomDirection;
import org.fp.service.creation.RandomFishReproduce;
import org.fp.service.creation.RandomPosition;
import org.fp.service.managment.AquariumController;
import org.fp.service.statistics.FishStatistics;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FishLive implements Runnable {
    static AquariumController aquariumController = (AquariumController) DependencyContainer.getDependency("aquariumController1");
    static FishStatistics fishStatistics = (FishStatistics) DependencyContainer.getDependency("FishLifeStatistics1");
    Fish fish;
    Movement movement; // композиция (двигаться можешь только если есть жизнь)

    public FishLive(Fish fish) {
        this.fish = fish;
        movement = new Movement();
        new Thread(this).start(); // Каждая рыба должна быть в отдельном потоке.(Thread)
    }

    @Override
    public void run() {
        while (aquariumController.isAquariumWorking()) {
            if (fish.getLifetime() > 0) {
                try {
                    movement.randomMove();
                } catch (AquariumIsNotWorkingException e) {
                    break;
                }
                fish.setLifetime(fish.getLifetime() - 1);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                aquariumController.releasePosition(fish.getPosition());
                fishStatistics.incrementTotalDied();
                System.out.println(fish + " is Dead"); // Отчет о каждом процессе должен отображаться в консоли.
                break;
            }
        }
    }

    private class Movement {
        public void randomMove() throws AquariumIsNotWorkingException {
            Position positionToMove = getPositionToMove();
            Fish f2 = aquariumController.moveIfPositionFree(positionToMove, fish);
            if (f2 == null) {
                aquariumController.releasePosition(fish.getPosition());
                fish.setPosition(positionToMove);
                fishStatistics.incrementTotalMovements();
            } else if (!checkGenderEquals(f2)) { //Если самцы и самки встречаются, они должны размножаться.
                bornRandomFish();
            }
        }

        private boolean checkGenderEquals(Fish anotherFish) {
            return anotherFish.getGender().equals(fish.getGender());
        }

        // todo процесс рождения, размещения, оживления новорожденной рыбы надо вынести в AquariumFill
        private void bornRandomFish() throws AquariumIsNotWorkingException {
            Fish newBornFish = RandomFishReproduce.reproduce();

            // пытаемся разместить рыбу на свободное место в аквариуме
            while ((aquariumController.moveIfPositionFree(newBornFish.getPosition(), newBornFish)) != null) {
                newBornFish.setPosition(RandomPosition.getPosition());
            }
            fishStatistics.incrementTotalBorn();
            System.out.println("A new fish was born = " + newBornFish); // Отчет о каждом процессе должен отображаться в консоли.
            new FishLive(newBornFish);
        }

        private Position getPositionToMove() {
            while (true){
                Position direction = RandomDirection.getDirection();
                int newX = fish.getPosition().getX() + direction.getX();
                int newY = fish.getPosition().getY() + direction.getY();
                // новое позиция для перемещения не должно выходит за рамки аквариума
                boolean x = (newX>=0) && (newX<=aquariumController.getAquariumLength());
                boolean y = (newY>=0) && (newY<=aquariumController.getAquariumHeight());
                if(x&&y){
                    return new Position(newX, newY);
                }
            }
        }
    }
}
