package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;

public class AkkaApp {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: FlightApp <1: input path FlightMapper> <2: input path AirportMapper> <output path>");
            System.exit(-1);
        }

        ActorSystem system = ActorSystem.create("test");
        ActorRef storeActor = system.actorOf(Props.create(RouteActor.class));
        ActorRef testActor = system.actorOf(Props.create(TestRunnerActor.class));

        storeActor.tell(new StoreActor.StoreMessage("test", "test"), ActorRef.noSender());

       router =  new RoundRobinPool(3);

    }
}
