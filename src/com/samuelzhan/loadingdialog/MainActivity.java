package com.samuelzhan.loadingdialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button btnShowDialog;
	private LoadingDialog dialog;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		handler=new Handler();	
		
		btnShowDialog=(Button)findViewById(R.id.btn_show_dialog);
		btnShowDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//显示自定义样式的dialog
				dialog.show();
				
				//dialog显示后就可以进行一些耗时操作，比如网络请求，数据库处理等
				
				
				//如果10秒后加载等待还没结束(即耗时操作还没完成)，则主动取消耗时操作并使dialog消失
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(dialog.isShowing()){
							dialog.cancel();
						}
					}
				}, 10000);
			}
		});
		
		//使用带文字的构造体创建dialog
		dialog=new LoadingDialog(this, null);
		//设置不能按屏幕或物理返回键退出
		dialog.setCancelable(false);
		//设置
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub 
				
				//执行取消操作，可以中断耗时操作
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//handler依赖于activity，结束后要习惯把handler的任务取消掉，避免activity回收不了而内存泄露
		if(handler!=null){
			handler.removeCallbacksAndMessages(null);
		}
	}
}
