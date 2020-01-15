# vitej
go-vite java sdk

## 文档

[wiki](https://vite.wiki/zh/api/javasdk_v2/)
[demo](https://github.com/vitelabs/vitej-demo)

## 添加maven依赖
在项目`pom.xml`中添加依赖
```
<dependency>
  <groupId>org.vite</groupId>
  <artifactId>vitej</artifactId>
  <version>${release-version}</version>
</dependency>
<dependency>
   <groupId>org.vite</groupId>
   <artifactId>vitej-dependencies</artifactId>
   <version>${release-version}</version>
   <type>pom</type>
</dependency>
```

## 使用示例

### getting started
目前只支持http client。

`Vitej`包装了go-vite的大部分RPC接口

创建Vitej的方式如下：
```
默认连接到http://127.0.0.1:48132
Vitej vitej = new Vitej(new HttpService());
指定go-vite http url
Vitej vitej = new Vitej(new HttpService("http://127.0.0.1:48132"));
指定go-vite http url和默认地址，后续发交易或者查询时默认使用keyPair地址
KeyPair keyPairDefault = new Wallet(Arrays.asList("alarm", "canal", "scheme", "actor", "left", "length", "bracket", "slush", "tuna", "garage", "prepare", "scout", "school", "pizza", "invest", "rose", "fork", "scorpion", "make", "enact", "false", "kidney", "mixed", "vast")).deriveKeyPair();
Vitej vitej = new Vitej(new HttpService("http://127.0.0.1:48132"), keyPairDefault);
```

同步查询用户账户链
```
AccountBlocksResponse response = vitej.getAccountBlocksByAddress(
        new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"), 0, 10
    ).send();
```
异步查询用户账户链
```
CompletableFuture<AccountBlocksResponse> future = vitej.getAccountBlocksByAddress(
        new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"), 0, 10
).sendAsync();
AccountBlocksResponse response = future.get();
```

### 常用方法

#### 发交易

```
Request<?, EmptyResponse> request = vitej.sendTransaction(
    new TransactionParams()
            .setToAddress(new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"))
            .setAmount(new BigInteger("100")),
    false);
sendBlockHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
request.send();
```

#### 查询用户账户链
```
AccountBlocksResponse response = vitej.getAccountBlocksByAddress(
        new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"), 0, 10
    ).send();
```
#### 订阅快照块事件
```
vitej.snapshotBlockFlowable().subscribe(msg -> {
            System.out.println("snapshotBlock: " + JSON.toJSONString(msg));
        });
```
