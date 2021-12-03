package akka;

import akka.actor.AbstractActor;

public class StoreActor extends AbstractActor {
    @Override
    public Receive createReceive(){
        return receiveBuilder().match(
                
        ).build();
    }

    public void SetTestResult(String testResult, String packageID) {
      // сохраняем в локальное хранилище
    };

    public String GetTestResults(String packageID) {
        return "";
    }
}
