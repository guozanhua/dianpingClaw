package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static Gson gson = new Gson();

    public static <T> HashMap<String, T> jsonToMap(String jsonStr, Class<T> clazz) throws IOException {
        HashMap<String, T> map = gson.fromJson(jsonStr, new TypeToken<HashMap<String, T>>() {
        }.getType());
        return map;
    }

    public static void main(String[] args) throws Exception {
        String url = "http://www.dianping.com/mylist/ajax/shoprank?rankId=745c793e5a96e43d6dcd6619265b122830aacdebee4c4c9365dc18972daccaf7";
        Map map = jsonToMap(HttpTool.getStrDataByWebDownload(url), Object.class);

        List<Map<String, String>> list = (List<Map<String, String>>) map.get("shopBeans");
        for (Map<String, String> shopMap : list) {
            System.out.println(shopMap.get("shopId"));
        }
    }
}
