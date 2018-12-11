package top.cuihp.shortlinks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import top.cuihp.library.RespResult;
import top.cuihp.library.ShortLinkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShortLinkUtils.newIns().toTransformation("https://blog.csdn.net/jennyliliyang/article/", new ShortLinkUtils.CallBack() {
            @Override
            public void onResult(RespResult respResult) {
                Toast.makeText(MainActivity.this, respResult.getUrl_short(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {

            }
        });
    }
}
