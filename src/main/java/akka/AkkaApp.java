package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.*;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.util.ArrayList;
import java.util.concurrent.CompletionStage;
import scala.concurrent.Future;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.PathMatchers.segment;

public class AkkaApp {
    private final static String HOST = "localhost";
    private final static int PORT = 8080;
    private final static int NR = 3;
    private final static int TIMEOUT = 5000;

    private final static String RESULT_QUERY = "packageID";

    private final static String RESULT_PATH_ = "get_result";
    private final static String TEST_RUN_PATH = "run";

    private static Route createRouter(ActorRef storeActor,  ActorRef testActor ) {
        return route(
                get(() -> concat(
                        path(RESULT_PATH_, () -> parameter(RESULT_QUERY, key -> {
                            Future<Object> res = Patterns.ask(storeActor, key, TIMEOUT);
                            return completeOKWithFuture(res, Jackson.marshaller());
                        }))
                )),
                post(() -> concat(
                        path(TEST_RUN_PATH, ()->
                                entity(
                                        Jackson.unmarshaller(TestInputData.class), body ->  {
                                            ArrayList<TestInformation> tests = body.GetTests();
                                            for (TestInformation t: tests) {
                                                testActor.tell(t, storeActor);
                                            }
                                            return complete(StatusCodes.OK);
                                        })))
                ));
    };

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("test");
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class));
        ActorRef testActor = system.actorOf(new RoundRobinPool(NR).props(Props.create(TestRunnerActor.class)));

        ActorMaterializer actorMater =  ActorMaterializer.create(system);

        final Http http = Http.get(system);

        Route router = createRouter(storeActor, testActor);

        Flow<HttpRequest, HttpResponse, ?> handler = router.flow(system, actorMater);
        ConnectHttp connect = ConnectHttp.toHost(HOST, PORT);
        CompletionStage<ServerBinding> srv =  http.bindAndHandle(handler, connect, actorMater);

        System.out.println("start");
        System.in.read();
        srv.thenCompose(ServerBinding::unbind).thenAccept(unbound -> system.terminate());
    }
}
