package org.fp;
import org.fp.service.creation.RandomAquariumCreate;
import org.fp.service.managment.AquariumController;
import org.fp.model.Aquarium;
import org.fp.service.managment.AquariumFill;
import org.fp.service.managment.AquariumRunner;
import org.fp.service.statistics.FishStatistics;

public class Main {
    static final String LINE_SEPARATOR ="*************************************************************";
    public static void main(String[] args) {

        Aquarium aquarium1 = RandomAquariumCreate.create();
        System.out.println("Aquarium created = "+aquarium1);
        DependencyContainer.addDependency("aquariumController1", new AquariumController(aquarium1));
        DependencyContainer.addDependency("FishLifeStatistics1",new FishStatistics());
        AquariumFill.fillAquarium(aquarium1);
        System.out.println(LINE_SEPARATOR);
        AquariumRunner aquariumRunner = new AquariumRunner(aquarium1);
        aquariumRunner.run();

        AquariumController aquariumController = (AquariumController) DependencyContainer.getDependency("aquariumController1");
        FishStatistics fishStatistics = (FishStatistics) DependencyContainer.getDependency("FishLifeStatistics1");
        while (aquarium1.isWorking()) {

        }

        String status = aquariumController.isAquariumFull() ? "Full" : "Empty";
        System.out.println(LINE_SEPARATOR);
        System.out.println("Aquarium stopped. It is " + status + "! \n" + fishStatistics.getStatistic());
        System.out.println(LINE_SEPARATOR);
    }
}