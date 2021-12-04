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
                TestInformation.class, this::SetTestResult
        ).match(
                String.class, packageID -> {
                    sender().tell(getResult(packageID), self());
                }
        ).build();
    }

    private void setTestResult(TestInformation testResult) {
        if (!storage.containsKey(testResult.getPackageID())) {
            storage.put(testResult.getPackageID(), new HashMap<>());
        }

        storage.get(testResult.getPackageID()).put(testResult.getFunctionName(), testResult);
    };

    private Map<String, TestInformation> getResult(String packageID) {

    }
}
