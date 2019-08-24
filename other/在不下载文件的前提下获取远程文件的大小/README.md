# HTTP协议在不下载文件的前提下获取远程文件大小
```
/**
    * 获取网络文件大小 
    */
    private static long getFileLength(String downloadUrl) throws IOException{
		  if(downloadUrl == null || "".equals(downloadUrl)){
			  return 0L ; 
		  }
	      URL url = new URL(downloadUrl);
	      HttpURLConnection conn = null;
	      try {
	          conn = (HttpURLConnection) url.openConnection();
	          conn.setRequestMethod("HEAD");
	          conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows 7; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36 YNoteCef/5.8.0.1 (Windows)");
	          return (long) conn.getContentLength();
	      } catch (IOException e) {
	          return 0L;
	      } finally {
	          conn.disconnect();
	    }
     }
```