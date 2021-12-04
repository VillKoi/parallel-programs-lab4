package akka;

import akka.actor.AbstractActor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;

public class TestRunnerActor extends AbstractActor {
    private final static String ENGINE_NAME = "nashorn";

    @Override
    public Receive createReceive(){
        return receiveBuilder().match(
                TestInformation.class, test -> {
                    String result = RunTest(test.getJscript(), test.getFunctionName(), test.getArgs());
                    System.out.println(result);
                    sender().tell(test.setResult(result), self());
                }
        ).build();
    }

    public String RunTest(String jscript, String functionName, ArrayList<Integer> params) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(ENGINE_NAME);
        engine.eval(jscript);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, params.toString()).toString();
    }
}
