package akka;

import akka.actor.AbstractActor;

public class StoreActor extends AbstractActor {
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

    };
}
