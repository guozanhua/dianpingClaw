package util;

/**
 * @program: LiteraryHanSpider    @author: shan junwei
 * @description
 * @create: 2019-02-22 16:18
 **/
public class HttpToolTest {

    public static void main(String[] args) throws Exception {
        String url = "http://www.dianping.com/mylist/ajax/shoprank?rankId=745c793e5a96e43d6dcd6619265b122830aacdebee4c4c9365dc18972daccaf7";
        String data = new String(HttpTool.getByteArrByWebDownload(url),"utf-8");
        System.out.println(data);
    }

}
