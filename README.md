该demo是为了验证国产CPU的多核运行情况，Windows需使用任务管理器监测、Linux需使用top命令并按1执行

执行的逻辑为根据当前CPU实际拥有的逻辑处理器数量，启动对应数量的线程。

每个线程，均进行素数的判定。数值范围为[start,start+size)

```sh
java -jar CpuMulticoreTest-1.0.jar <start> <size>
```
