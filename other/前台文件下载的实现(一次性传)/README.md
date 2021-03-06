# 浏览器前台下载文件的实现
```
/**
 * 前台下载文件
 *
 * @param fileId   文件标识
 * @param fileName 文件名
 * @param response 客户端响应
 * @throws IOException io流异常
 */
@Auth(authType = Auth.Type.TOKEN, value = "80011001001003")
@RequestMapping(method = RequestMethod.GET, path = "/downloadFile")
public void downloadFile(@RequestParam String fileId, @RequestParam String fileName, HttpServletResponse response) throws IOException {
    if(fileId == null || "".equals(fileId)){
        log.warn("FileId is null.");
        return ;
    }
    File tmpFile = new File(UUID.randomUUID().toString());
    storageService.download(tmpFile, fileId);
    OutputStream output = response.getOutputStream();
    byte[] buffer = new byte[1024];
    InputStream is = new FileInputStream(tmpFile);
    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
    int len;
    int totalLength = 0;
    while ((len = is.read(buffer)) != -1) {
        output.write(buffer, 0, len);
        totalLength += len;
    }
    response.addHeader("Content-Length", "" + totalLength);
    output.flush();
    output.close();
    is.close();
    boolean deleteFlag = tmpFile.delete();
    if (!deleteFlag) {
        log.warn("temporary file {} is not deleted successfully.", tmpFile.getName());
    }
}
```