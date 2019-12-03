````
RestTemplate restTemplate = new RestTemplate();

final String APPLICATION_PDF = "application/pdf";
HttpHeaders headers = new HttpHeaders();
InputStream inputStream = null;
OutputStream outputStream = null;
try {
    List list = new ArrayList<>();
    list.add(MediaType.valueOf(APPLICATION_PDF));
    headers.setAccept(list);

    ResponseEntity<byte[]> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        new HttpEntity<byte[]>(headers),
        byte[].class);

    byte[] result = response.getBody();

    inputStream = new ByteArrayInputStream(result);

    File file = new File("/Users/feixiaobo/Desktop/test3.pdf");
    if (!file.exists())
    {
        file.createNewFile();
    }

    outputStream = new FileOutputStream(file);
    int len = 0;
    byte[] buf = new byte[1024];
    while ((len = inputStream.read(buf, 0, 1024)) != -1) {
        outputStream.write(buf, 0, len);
    }
    outputStream.flush();

}finally {
    if(inputStream != null){
        inputStream.close();
    }
    if(outputStream != null){
        outputStream.close();
    }
}
````
这种方式虽然实现简单，但是有一个缺点是把下载的文件全量读进了内存，所以只能用来下载小文件。