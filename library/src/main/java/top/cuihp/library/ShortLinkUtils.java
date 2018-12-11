package top.cuihp.library;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShortLinkUtils {

    private static ShortLinkUtils shortLinkUtils;

    private ShortLinkUtils() {

    }

    private String longUrl;

    public static ShortLinkUtils newIns() {
        if (shortLinkUtils == null) {
            shortLinkUtils = new ShortLinkUtils();
        }
        return shortLinkUtils;

    }

    private CallBack callBack;

    public interface CallBack {
        void onResult(RespResult respResult);

        void onError();
    }

    /**
     * @param url      长连接
     * @param callBack 回调方法
     */
    public void toTransformation(String url, CallBack callBack) {
        this.longUrl = url;
        this.callBack = callBack;
        new LinkAsyncTask().execute(url);

    }

    private static final String TAG = ShortLinkUtils.class.getSimpleName();

    class LinkAsyncTask extends AsyncTask<String, Integer, RespResult> {

        @Override
        protected RespResult doInBackground(String... strings) {
            String longUrl = strings[0];
            RespResult respResult = new RespResult();
            try {
                String baseUrl = "http://api.t.sina.com.cn/short_url/shorten.json?source=31641035&url_long=";
                String requestUrl = baseUrl + longUrl;
                // 新建一个URL对象
                URL url = new URL(requestUrl);
                // 打开一个HttpURLConnection连接
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                // 设置连接主机超时时间
                urlConn.setConnectTimeout(5 * 1000);
                //设置从主机读取数据超时
                urlConn.setReadTimeout(5 * 1000);
                // 设置是否使用缓存  默认是true
                urlConn.setUseCaches(true);
                // 设置为Post请求
                urlConn.setRequestMethod("GET");
                //urlConn设置请求头信息
                //设置客户端与服务连接类型
                urlConn.addRequestProperty("Connection", "Keep-Alive");
                urlConn.addRequestProperty(" Content-type", "application/json");
                // 开始连接
                urlConn.connect();
                // 判断请求是否成功
                if (urlConn.getResponseCode() == 200) {
                    // 获取返回的数据

                    String strResp = convertStreamToString(urlConn.getInputStream());
                    strResp = strResp.replace("[", "").replace("]", "").replace("/n", "");
                    Gson gson = new Gson();
                    respResult = gson.fromJson(strResp, RespResult.class);
                    Log.e(TAG, "Get方式请求成功，result--->" + strResp);
                } else {
                    Log.e(TAG, "Get方式请求失败");
                }
                // 关闭连接
                urlConn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            return respResult;
        }

        @Override
        protected void onPostExecute(RespResult respResult) {
            if (callBack != null) {
                if (respResult != null) {
                    if (TextUtils.isEmpty(respResult.getUrl_short())) {
                        respResult.setUrl_long(longUrl);
                        respResult.setUrl_short(longUrl);
                        respResult.setType(-1);
                    }
                    callBack.onResult(respResult);
                } else {
                    callBack.onError();
                }
            }
        }
    }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();


        String line = null;

        try {

            while ((line = reader.readLine()) != null) {

                sb.append(line + "/n");

            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return sb.toString();
    }


}
