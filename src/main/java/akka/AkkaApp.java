package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.*;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatcher0;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import javax.annotation.processing.Completion;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import scala.concurrent.Future;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.PathMatchers.segment;

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

        Route router = createRouter(storeActor, testActor);

        Flow<HttpRequest, HttpResponse, ?> handler = router.flow(system, actorMater);
        ConnectHttp connect = ConnectHttp.toHost(HOST, PORT);
        CompletionStage<ServerBinding> srv =  http.bindAndHandle(handler, connect, actorMater);
    }

    private static Route createRouter(ActorRef storeActor,  ActorRef testActor ) {
        return route(
                get(() -> concat(
                        path("get_result", () -> parameter("packageID", key -> {
                            Future<Object> res = Patterns.ask(storeActor, "message", 0);
                            return completeOKWithFuture(res, Jackson.marshaller());
                        }))),
                post(() -> concat(
                        path("get_result", () -> parameter("packageID", key -> {
                            Future<Object> res = Patterns.ask(storeActor, "message", 0);
                            return completeOKWithFuture(res, Jackson.marshaller());
                        })),
                        path("run", ()->
                                get(()->  {

                                    Object test;
                                    testActor.tell(test, storeActor);
                                    return complete(StatusCodes.OK);
                                })))
                ));
    };
}
