````
/**
   * 下载大文件,使用流接收
   *
   * @param url
   * @param targetDir
   */
  public void downloadBigFileToPath(String url, String targetDir){
    downloadBigFileToPath(url,targetDir,null);
  }
  /**
   * 下载大文件,使用流接收
   *
   * @param url
   * @param targetDir
   */
  public void downloadBigFileToPath(String url, String targetDir, Map<String, String> params) {
    Instant now = Instant.now();
    String completeUrl = addGetQueryParam(url, params);
    try {
      String path = getAndCreateDownloadDir(url, targetDir);
      //定义请求头的接收类型
      RequestCallback requestCallback = request -> request.getHeaders()
              .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
      ResponseExtractor<Void> responseExtractor = response -> {
        Files.copy(response.getBody(), Paths.get(path));
        return null;
      };
      httpClientTemplate.execute(completeUrl, HttpMethod.GET, requestCallback, responseExtractor);
    } catch (IOException e) {
      log.error("[下载文件] 写入失败:", e);
    }
    log.info("[下载文件] 完成,耗时:{}", ChronoUnit.MILLIS.between(now, Instant.now()));
  }
````
这里与单线程下载小文件的区别是使用了`ResponseExtractor`，其`extractData`接口入参是`ClientHttpResponse`对象，通过`ClientHttpResponse`的`getBody`方法可以获取返回内容的输入流，然后我们可以读比如1024字节进内存，然后把这1024字节写到指定的文件中，如此往复直到响应输入流全部读完。