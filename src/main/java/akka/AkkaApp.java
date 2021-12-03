package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.*;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import javax.annotation.processing.Completion;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class AkkaApp {
    private final static String HOST = "";
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: FlightApp <1: input path FlightMapper> <2: input path AirportMapper> <output path>");
            System.exit(-1);
        }

        ActorSystem system = ActorSystem.create("test");
        ActorRef storeActor = system.actorOf(Props.create(RouteActor.class));

        storeActor.tell(new StoreActor.StoreMessage("test", "test"), ActorRef.noSender());

        ActorRef testActor = system.actorOf(new RoundRobinPool(3).props(Props.create(TestRunnerActor.class)));

        ActorMaterializer actorMater =  ActorMaterializer.create(system);

        final Http http = Http.get(system);

        Flow<HttpRequest, HttpResponse, ?> handler = new Flow();
        ConnectHttp connect = ConnectHttp.toHost(HOST, PORT);

        CompletionStage<ServerBinding> srv =  http.bindAndHandle(handler, connect, actorMater);

        Patterns.ask(storeActor, "message", 0);

    }
}
