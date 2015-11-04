
## 注意事项

> 该接口运行长时间运行会出现连接释放不了问题，猜测应该是Redis层使用不当造成的，目前的解决方法是定时重启该接口。

> 长时间运行后，curl测试该接口时出现如下错误：

```
curl: (56) Recv failure: Connection reset by peer
```