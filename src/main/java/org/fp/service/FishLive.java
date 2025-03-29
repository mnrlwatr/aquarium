package org.fp.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.DependencyContainer;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.fish.AbstractFish;
import org.fp.model.fish.ClownFish;
import org.fp.service.creation.fish.FishFactory;
import org.fp.service.util.RandomPosition;
import org.fp.service.managment.AquariumController;
import org.fp.service.statistics.FishStatistics;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FishLive implements Runnable {
    AquariumController aquariumController = (AquariumController) DependencyContainer.getDependency("aquariumController1");
    FishStatistics fishStatistics = (FishStatistics) DependencyContainer.getDependency("FishLifeStatistics1");
    FishFactory fishFactory = (FishFactory) DependencyContainer.getDependency("FishFactory1");
    AbstractFish abstractFish;
    Movement movement; // композиция (двигаться можешь только если есть жизнь)

    public FishLive(AbstractFish abstractFish) {
        this.abstractFish = abstractFish;
        movement = new Movement();
        new Thread(this).start(); // Каждая рыба должна быть в отдельном потоке.(Thread)
    }

    @Override
    public void run() {
        while (aquariumController.isAquariumWorking()) {
            if (abstractFish.getLifetime() > 0) {
                try {
                    movement.randomMove();
                } catch (AquariumIsNotWorkingException e) {
                    break;
                }
                abstractFish.setLifetime(abstractFish.getLifetime() - 1);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                aquariumController.releasePosition(abstractFish.getPosition());
                fishStatistics.incrementTotalDied();
                System.out.println(abstractFish + " is Dead"); // Отчет о каждом процессе должен отображаться в консоли.
                break;
            }
        }
    }

    private class Movement {
        public void randomMove() throws AquariumIsNotWorkingException {
            Object positionToMove = abstractFish.calculateRandomPositionToMove(aquariumController.getAquariumLength(), aquariumController.getAquariumHeight());
            ClownFish f2 = (ClownFish) aquariumController.moveIfPositionFree(positionToMove, fish);
            if (f2 == null) {
                aquariumController.releasePosition(fish.getPosition());
                fish.setPosition(positionToMove);
                fishStatistics.incrementTotalMovements();
            } else if (!abstractFish.getGender().equals(f2.getGender())) { //Если самцы и самки встречаются, они должны размножаться.
                bornRandomFish();
            }
        }

        // todo процесс рождения, размещения, оживления новорожденной рыбы надо вынести в утилитный класс
        private void bornRandomFish() throws AquariumIsNotWorkingException {
            AbstractFish newBornFish = fishFactory.create("ClownFish");

            // пытаемся разместить рыбу на свободное место в аквариуме
            while ((aquariumController.moveIfPositionFree(newBornFish.getPosition(), newBornFish)) != null) {
                newBornFish.setPosition(RandomPosition.getPosition());
            }
            fishStatistics.incrementTotalBorn();
            System.out.println("A new fish was born = " + newBornFish); // Отчет о каждом процессе должен отображаться в консоли.
            new FishLive(newBornFish);
        }

    }
}
