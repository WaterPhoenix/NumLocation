package com.lication.activity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.location.util.ActivityUtil;
import com.location.util.Constant;

public class NumLocationActivity extends Activity {
    /** Called when the activity is first created. */
	/**
	 * ��ǰ�����Ļ���
	 */
	private Context currentContext = NumLocationActivity.this;
	/**
	 * �ֻ����������
	 */
	private EditText editTextNum;
	/**
	 * ��ѯ��ť
	 */
	private Button searchButton;
	/**
	 * �������ı���
	 */
	private TextView textViewLocation;
	/**
	 * ��Ӫ���ı���
	 */
	private TextView textViewService;
	/**
	 * �������ı���
	 */
	private TextView textViewCardType;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
		 * ȥ��������
		 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mobilenum);
        /**
         * ��ʼ���ؼ�
         */
        initView();
    }
	/**
	 * �ж��Ƿ�����������
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	         if (mNetworkInfo != null) {  
	             return mNetworkInfo.isAvailable();  
	         }  
	     }  
	     return false;  
	 }
    /**
     * ��ʼ���ؼ�
     */
    private void initView() {
		// TODO Auto-generated method stub
    	editTextNum = (EditText) this.findViewById(R.id.textPhoneNum);
    	searchButton = (Button) this.findViewById(R.id.buttonSearch);
    	searchButton.setOnClickListener(buttonOnClickListener);
    	textViewLocation = (TextView) this.findViewById(R.id.textLocation);
    	textViewService = (TextView) this.findViewById(R.id.textService);
    	textViewCardType = (TextView) this.findViewById(R.id.textType);
	}
    /**
     * ��ѯ��ť��Ӧ�¼�����
     */
    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if(!isNetworkConnected(currentContext)){
				ActivityUtil.showMessageByToast(currentContext, "����������!");
				return;
			}
			String num = editTextNum.getText().toString();
			if(num == null || "".equals(num)){
				ActivityUtil.showMessageByToast(currentContext, "�������ֻ�������ٲ�ѯ!");
			}else if(num.length() < 7){
				ActivityUtil.showMessageByToast(currentContext, "����������7λ�ֻ�����!");
			}else{
				textViewLocation.setText("");
				textViewService.setText("");
				textViewCardType.setText("");
				SearchLocationTask searchLocationTask = new SearchLocationTask(Constant.GET_LOCATION_METHOD_NAME,Constant.GET_LOCATION_SOAP_ACTION,num,Constant.GET_LOCATION_RESULT_NAME);
		        searchLocationTask.execute();
			}
		}
	};
	@SuppressWarnings("unchecked")
	public class SearchLocationTask extends AsyncTask{
		private ProgressDialog pdialog;
		
		private String methodName;
		private String soapAction;
		private String param;
		private String getPropertyName;
		public SearchLocationTask(String methodName,String soapAction,String param,String getPropertyName){
			this.methodName = methodName;
			this.soapAction = soapAction;
			this.param = param;
			this.getPropertyName = getPropertyName;
			
			pdialog = new ProgressDialog(currentContext);
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pdialog.setTitle("��ȡ��...");
			pdialog.setIcon(R.drawable.icon);
			pdialog.show();
		}
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			SoapObject detail = null;
			Object result = null;
			try{
				SoapObject rpc = new SoapObject(Constant.NAMESPACE, methodName);
				rpc.addProperty("mobileCode", param);
				Looper.prepare();
				HttpTransportSE ht = new HttpTransportSE(Constant.URL);
				ht.debug = true;
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.bodyOut = rpc;
				envelope.dotNet = true;
				envelope.setOutputSoapObject(rpc);
				ht.call(soapAction, envelope);
				detail = (SoapObject) envelope.bodyIn;
				//result = (Object) envelope.getResponse();
				result =  detail.getProperty(getPropertyName);
				//result = ((SoapObject) result).getProperty(0).toString();  
			}catch(Exception e){
				e.printStackTrace();
				ActivityUtil.showMessageByToast(currentContext, "���¹��̳���:"+e.getMessage());
			}
			return result;
		}
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			/**
			 * ��������
			 */
			String resultStr = result.toString();
			String[] resultList = resultStr.split(" ");
			if(resultList.length == 1){
				ActivityUtil.showMessageByToast(currentContext, resultList[0]);
			}else{
				/*for(String str : resultList){
					ActivityUtil.showMessageByToast(currentContext, str);
				}*/
				String province = Constant.GetChineseWord(resultList[0]).get(0);
				String city = resultList[1];
				int length = province.length()+2;
				String service = resultList[2].substring(0,length);
				String cardType = resultList[2].substring(length);
				textViewLocation.setText(province+city);
				textViewService.setText(service);
				textViewCardType.setText(cardType);
			}
			pdialog.cancel();
		}
	}
}