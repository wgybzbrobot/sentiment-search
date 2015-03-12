
# 與情搜索服务统一接口

```
带解决问题
2015-03-12 20:18:17,664 [qtp1905570548-9-selector-ServerConnectorManager@5dbd3f6f/3] WARN  org.eclipse.jetty.io.SelectorManager [Line:705] - Could not process key for channel java.nio.channels.SocketChannel[connected local=/192.168.32.17:2900 remote=/192.168.6.139:12936]
java.lang.IllegalStateException: Invalid state: CHANGING
        at org.eclipse.jetty.io.SelectChannelEndPoint.onSelected(SelectChannelEndPoint.java:110) ~[jetty-io-9.3.0.M0.jar:9.3.0.M0]
        at org.eclipse.jetty.io.SelectorManager$ManagedSelector.processKey(SelectorManager.java:682) [jetty-io-9.3.0.M0.jar:9.3.0.M0]
        at org.eclipse.jetty.io.SelectorManager$ManagedSelector.select(SelectorManager.java:642) [jetty-io-9.3.0.M0.jar:9.3.0.M0]
        at org.eclipse.jetty.io.SelectorManager$ManagedSelector.run(SelectorManager.java:580) [jetty-io-9.3.0.M0.jar:9.3.0.M0]
        at org.eclipse.jetty.util.thread.NonBlockingThread.run(NonBlockingThread.java:52) [jetty-util-9.3.0.M0.jar:9.3.0.M0]
        at org.eclipse.jetty.util.thread.QueuedThreadPool.runJob(QueuedThreadPool.java:610) [jetty-util-9.3.0.M0.jar:9.3.0.M0]
        at org.eclipse.jetty.util.thread.QueuedThreadPool$3.run(QueuedThreadPool.java:539) [jetty-util-9.3.0.M0.jar:9.3.0.M0]
        at java.lang.Thread.run(Thread.java:745) [na:1.7.0_75]
```