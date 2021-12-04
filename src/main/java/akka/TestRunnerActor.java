package akka;

import akka.actor.AbstractActor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;

public class TestRunnerActor extends AbstractActor {
    @Override
    public Receive createReceive(){
        return receiveBuilder().match(
                TestInformation.class, test -> {
                    String result = RunTest(test.getJscript(), test.getFunctionName(), test.getArgs());
                    sender().tell(new TestInformation(test,  result), self());
                }
        ).build();
    }


    public String RunTest(String jscript, String functionName, ArrayList<Integer> params) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(jscript);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, params).toString();
    }
}
