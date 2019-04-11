package com.glwlc.lulu.test;

import com.glwlc.lulu.framework.Flow.action.model.decorator.ControlFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.DataFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.DecisionTreeFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.DialogFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.EventFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.FlowCommonDecorator;
import com.glwlc.lulu.framework.Flow.loader.LoaderEngine;

import java.util.Map;

/**
 * @Author: Gavin
 * @Date: 2019-02-28 17:38
 */
public class ActionDecoratorTest {
    public static void main(String[] args) {

        FlowCommonDecorator controlFlowDecorator = new ControlFlowDecorator(
                "dictete", "id","nextNode", "nextNodeForException","root");
        FlowCommonDecorator dataFlowDecorator = new DataFlowDecorator(
                "method", "id","nextNode", "nextNodeForException","root");
        FlowCommonDecorator decisionTreeFlowDecorator = new DecisionTreeFlowDecorator(
                "regular",  "nextNodeForTrue",
                 "nextNodeForFalse",  "id","nextNode",  "nextNodeForException","root");
        FlowCommonDecorator dialogFlowDecorator = new DialogFlowDecorator(
                "dialog", "id","nextNode", "nextNodeForException","root");
        FlowCommonDecorator eventFlowDecorator = new EventFlowDecorator(
                "id", "nextNode", "nextNodeForException","root", "id");




        LoaderEngine loaderEngine = LoaderEngine.initLoader();
        Map<String, EventFlowDecorator> load = loaderEngine.config().load();
        System.out.println(load);
//        eventFlowDecorator.executeTemplate(null);
        controlFlowDecorator.executeTemplate(null);
        dataFlowDecorator.executeTemplate(null);
        decisionTreeFlowDecorator.executeTemplate(null);
        dialogFlowDecorator.executeTemplate(null);


    }
}
