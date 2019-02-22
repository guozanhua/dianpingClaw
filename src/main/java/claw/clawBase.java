package claw;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HttpTool;

/**
 * @program: LiteraryHanSpider    @author: shan junwei
 * @description
 * @create: 2019-02-22 13:55
 **/
public class clawBase {


    public static void clawByUrl(String url, String cssSelector) {
        try {
            Document doc = getPageDocument(url);
            if (doc == null) return;
            Element element = doc.select(cssSelector).get(0);
            String  suffixUrl =    element.attr("href");
            String  rankUrl  =   "http://www.dianping.com"+ suffixUrl;
            System.out.println(rankUrl);

            Document  rankDoc  =   getPageDocument(rankUrl);
            System.out.println(rankDoc);
            //  ץȡ�Ȱ�������ӵ�ַ
            Elements  elements  =  getSelectElements(rankDoc,"body > div> div > div > section > table > tbody > tr > td > a");

            System.out.println(elements);
            for(Element  ele  :  elements ){
                String  shopName  =  ele.text();
                String  shopUrl  =   ele.attr("href");
                System.out.println("������>>"+shopName+"  ��������>>"+shopUrl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param url
     * @return  ��ȡ��ҳ�ĵ�
     */
    public static Document getPageDocument(String url) {
        try {
            return Jsoup.connect(url)
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36")
                    .header("Host","")
                    .get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * @return  ��ȡ��ҳ�ĵ�
     */
    public static Element getSelectElement(Document  doc ,String cssSelector) {
        return doc.select(cssSelector).get(0);
    }

    public static Elements getSelectElements(Document  doc ,String cssSelector) {
        return doc.select(cssSelector);
    }


    public static void main(String[] args) throws Exception {
        // http://www.dianping.com/changsha/food  ��ʼҳ
       // clawByUrl("http://www.dianping.com/changsha/food","#shop-rank > div.title > a[href]");
        String  url = "http://www.dianping.com/mylist/ajax/shoprank?rankId=745c793e5a96e43d6dcd6619265b122830aacdebee4c4c9365dc18972daccaf7";
        //System.out.println(HttpTool.doGet(url));
        HttpTool.downLoadFromUrl(url,"data/result.txt");
        //System.out.println(getPageDocument(url));
    }
}
