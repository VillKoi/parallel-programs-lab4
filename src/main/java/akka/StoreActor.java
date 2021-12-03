package akka;

import akka.actor.AbstractActor;

public class StoreActor extends AbstractActor {
    @Override
    public Receive createReceive(){
        return receiveBuilder(
        ).match(
                TestInformation.class, tI -> {

                }
        ).match(
                String.class, packageID -> {
                    sender().tell(packageID, self());
                }
        ).build();
    }

    private void SetTestResult(TestInformation testResult) {
      // сохраняем в локальное хранилище
    };

    public String GetTestResults(String packageID) {
        return "";
    }
}
