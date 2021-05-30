# Jobsity Challenge

## About Unit Tests

When running Unit Tests in Android Studio there is a chance to get the following error, caused by Mockk failing to initialize

```
java.lang.ExceptionInInitializerError
	at com.rodrigocibils.jobsity.UnitTest.setup(UnitTest.kt:93)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:24)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)
	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)
	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)
Caused by: java.lang.IllegalStateException: Error during attachment using: net.bytebuddy.agent.ByteBuddyAgent$AttachmentProvider$Compound@6615435c
	at net.bytebuddy.agent.ByteBuddyAgent.install(ByteBuddyAgent.java:613)
	at net.bytebuddy.agent.ByteBuddyAgent.install(ByteBuddyAgent.java:586)
	at net.bytebuddy.agent.ByteBuddyAgent.install(ByteBuddyAgent.java:538)
	at net.bytebuddy.agent.ByteBuddyAgent.install(ByteBuddyAgent.java:515)
	at io.mockk.proxy.jvm.JvmMockKAgentFactory.initInstrumentation(JvmMockKAgentFactory.kt:127)
```
In that case go to `Run/Edit Configurations` in Android Studio window and add the flag `-XX:+StartAttachListener` in the section `VM Options`, as shown in the following image.

<p align="center">
	<img src="https://github.com/rodcibils/jobsity_challenge/blob/main/images/mockk_error.png" />
</p>

This step should be made <b>for every unit test in the list</b> for them to run properly.

## About APK file

APK file coult be located in `distribution` folder in the root of this repo.