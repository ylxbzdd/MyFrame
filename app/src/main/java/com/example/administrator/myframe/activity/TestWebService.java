package com.example.administrator.myframe.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myframe.R;
import com.example.administrator.myframe.utils.LogUtils;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


/**
 * Android平台调用WebService（手机号码归属地查询）
 *
 * @author chao
 * @date 2015-10-25
 */
public class TestWebService extends AppCompatActivity {

    private EditText phoneSecEditText;
    private TextView resultView;
    private Button queryButton;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        phoneSecEditText = (EditText) findViewById(R.id.phone_sec);
        resultView = (TextView) findViewById(R.id.result_text);
        queryButton = (Button) findViewById(R.id.query_btn);

        queryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneSec = phoneSecEditText.getText().toString().trim();
                //启动后台异步线程进行连接webService操作，并且根据返回结果在主线程中改变UI
                QueryAddressTask queryAddressTask = new QueryAddressTask();
                //启动后台任务
                queryAddressTask.execute(phoneSec);

            }
        });
    }

    public String getRemoteInfo(String phoneSec) throws Exception{
        String WSDL_URI = "http://39.96.66.180:9991/WebMain.asmx?WSDL";//wsdl 的uri
        String namespace = "http://tempuri.org/";//命名空间
        String methodName = "TestMethod ";//要调用的方法名称
        SoapObject request = new SoapObject(namespace, methodName);
        // 设置需调用WebService接口需要传入的参数
        request.addProperty("testContent", phoneSec);
        request.addProperty("TestMethodResult", phoneSec);

        //创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);
        envelope.bodyOut = request;//由于是发送请求，所以是设置bodyOut
        envelope.dotNet = true;//由于是.net开发的webservice，所以这里要设置为true

        HttpTransportSE httpTransportSE = new HttpTransportSE(WSDL_URI);
        httpTransportSE.call(null, envelope);//调用

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        result = object.getProperty(0).toString();
        LogUtils.i_zz(result);
        return result;

    }

    class QueryAddressTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 查询手机号码（段）信息*/
            try {
                result = getRemoteInfo(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //将结果返回给onPostExecute方法
            return result;
        }

        @Override
        //此方法可以在主线程改变UI
        protected void onPostExecute(String result) {
            // 将WebService返回的结果显示在TextView中
            resultView.setText(result);
        }
    }
}

