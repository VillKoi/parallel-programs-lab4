package akka;

import akka.actor.AbstractActor;

import java.util.HashMap;
import java.util.Map;

public class StoreActor extends AbstractActor {
    private Map<String, Map<String, TestInformation>> storage = new HashMap<>();

    @Override
    public Receive createReceive(){
        return receiveBuilder(
        ).match(
                TestInformation.class, testInformation -> {
                    SetTestResult(testInformation);
                }
        ).match(
                String.class, packageID -> {
                    sender().tell(packageID, self());
                }
        ).build();
    }

    private void SetTestResult(TestInformation testResult) {
        if (!storage.containsKey(testResult.getPackageID())) {

        }

        storage.get(testResult.getPackageID()).put(testResult.getFunctionName(), testResult);
    };
}
