package akka;

import akka.actor.AbstractActor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestRunnerActor {
    @Override
    public AbstractActor.Receive createReceive(){
        return receiveBuilder().match(
                Object, test -> {
                    RunTest();
                    sender().tell
                }
        ).build();
    }


    public String RunTest(String jscript, String functionName, Object params) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(jscript);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, params).toString();
    }
}
