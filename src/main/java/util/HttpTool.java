package util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * @program: JsoupTED    @author: shan junwei
 * @description
 * @create: 2019-02-18 16:02
 **/
public class HttpTool {

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     *  从网络链接下载文件到输出路径
     * @param urlStr
     * @throws Exception
     */
    public static String  getStrDataByWebDownload(String urlStr) throws Exception {
        return  new String(getByteArrByWebDownload(urlStr),"utf-8");
    }

    /**
     *  从网络链接下载文件到输出路径
     * @param urlStr
     * @param filePath
     * @throws Exception
     */
    public static void downLoadFromUrl(String urlStr, String filePath) throws Exception {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String savePath = filePath.substring(0, filePath.lastIndexOf("/"));
        downLoadFromUrl(urlStr, fileName, savePath);
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        //获取自己数组
        byte[] getData = getByteArrByWebDownload(urlStr);
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        FileOutputStream fos = new FileOutputStream(new File(saveDir + File.separator + fileName));
        System.out.println();
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        System.out.println("INFO :" + urlStr + " download success" + " >> " + savePath + "/" + fileName);
    }


    public static byte[] getByteArrByWebDownload(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(10 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        byte[] result = readInputStream(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return result;
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {

    }
}
