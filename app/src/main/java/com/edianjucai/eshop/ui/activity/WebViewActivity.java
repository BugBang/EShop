package com.edianjucai.eshop.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.base.BaseActivity;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.entity.Show_ArticleActModel;
import com.edianjucai.eshop.server.InterfaceServer;
import com.edianjucai.eshop.util.ToastUtils;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-26.
 */
public class WebViewActivity extends BaseActivity {
    /**
     * webview 要加载的链接
     */
    public static final String EXTRA_URL = "extra_url";
    /**
     * webview 界面标题
     */
    public static final String EXTRA_TITLE = "extra_title";
    /**
     * 要显示的文章的ID
     */
    public static final String EXTRA_ARTICLE_ID = "extra_article_id";
    /**
     * 要显示的HTML内容
     */
    public static final String EXTRA_HTML_CONTENT = "extra_html_content";
    @BindView(R.id.act_project_detail_webview_pgb_progress)
    ProgressBar mActProjectDetailWebviewPgbProgress;
    @BindView(R.id.act_project_detail_webview_web)
    WebView mActProjectDetailWebviewWeb;
    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private String mStrUrl = null;
    private String mStrTitle = null;
    private String mStrArticleId = null;
    private String mStrHtmlContent = null;


    private ProgressDialog mDialog;
    private String mFileName;

    private void init() {
        App.getApplication().getmRuntimeConfig().setProjectDetailWebviewActivity(this);
        initIntentData();
        initTitle();
        initWebView();
        startLoadData();
    }

    @Override
    public void initParms(Bundle parms) {

    }
    @Override
    public int bindLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return R.layout.act_project_detail_webview;
    }

    @Override
    public void doBusiness(Context mContext) {
        init();
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    private void startLoadData() {
        if (mStrHtmlContent != null) {
            loadHtmlContent(mStrHtmlContent);
            return;
        }
        if (mStrArticleId != null) {
            loadArticleDetail(mStrArticleId);
            return;
        }
        if (mStrUrl != null) {
            loadUrl(mStrUrl);
            return;
        }
    }

    private void initIntentData() {
        mStrUrl = getIntent().getStringExtra(EXTRA_URL);
        mStrTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mStrArticleId = getIntent().getStringExtra(EXTRA_ARTICLE_ID);
        mStrHtmlContent = getIntent().getStringExtra(EXTRA_HTML_CONTENT);
    }

    private void initWebView() {
        WebSettings settings = mActProjectDetailWebviewWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
                settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
         settings.setUseWideViewPort(true);
                 settings.setLoadWithOverviewMode(true);
        mActProjectDetailWebviewWeb.setWebViewClient(new ProjectDetailWebviewActivity_WebViewClient());
        mActProjectDetailWebviewWeb.setWebChromeClient(new ProjectDetailWebviewActivity_WebChromeClient());
        mActProjectDetailWebviewWeb.setDownloadListener(new MyWebViewDownLoadListener());
    }

    private void loadHtmlContent(String htmlContent) {
        if (htmlContent != null) {
            mActProjectDetailWebviewWeb.loadDataWithBaseURL("about:blank", htmlContent, "text/html", "utf-8", null);
        }
    }

    private void loadUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            mActProjectDetailWebviewWeb.loadUrl(mStrUrl);
        }
    }


    @OnClick(R.id.iv_back)
    public void onClick() {
        if (mActProjectDetailWebviewWeb.canGoBack()) {
            mActProjectDetailWebviewWeb.goBack();
        }else {
            finish();
        }
    }


    class ProjectDetailWebviewActivity_WebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
        }
    }
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mValueCallback;
    private int selectImgMax = 1;//选取图片最大数量
    private int photosType = 0;//图库类型
    private static int FILECHOOSER_RESULTCODE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
//            if (this.photosType <= 0) {//调用自定义图库
//                uploadImgFromMyPhotos();
//            } else {//调用系统图库
                uploadImgFromSysPhotos(resultCode, intent);
//            }
        }
    }

    /**
     * 上传图片,调用系统图库 与h5 file标签交互
     * @param resultCode
     * @param intent
     */
    private void uploadImgFromSysPhotos(int resultCode, Intent intent) {
        if (mUploadMessage != null) {//5.0以下
            Uri result = intent == null || resultCode != RESULT_OK ? null
                    : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (mValueCallback != null) {//5.0+
            Uri[] uris = new Uri[1];
            uris[0] = intent == null || resultCode != RESULT_OK ? null
                    : intent.getData();
            if (uris[0]!=null){
                mValueCallback.onReceiveValue(uris);
            }
            mValueCallback.onReceiveValue(null);
            mValueCallback = null;
        }
    }


    /**
     * 上传图片，调用自己图库 与h5 file标签交互
     */
//    private void uploadImgFromMyPhotos() {
//        if (mValueCallback != null) {//5.0+
//            Uri[] uris = MediaSelectHelper.getImgPathToUriArray();
//            if (uris != null){
//                mValueCallback.onReceiveValue(uris);
//            }
//            mValueCallback = null;
//        } else if (mUploadMessage != null) {//5.0及以下
//            Uri uri = MediaSelectHelper.getImgPathToUri();
//            mUploadMessage.onReceiveValue(uri);
//            mUploadMessage = null;
//        }
//    }


    /**
     * js调用 setSelectImgMax 设置本地图片选取图片数量的最大值
     * @param selectImgMax 默认值为1
     * @param photosType   type<=0?调用自己的图库:调用系统图库
     */

    @JavascriptInterface
    public void setSelectImgMax(int selectImgMax, int photosType) {
        this.selectImgMax = selectImgMax;
        this.photosType = photosType;
    }

    class ProjectDetailWebviewActivity_WebChromeClient extends WebChromeClient {

        // For Android 5.0+
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback,android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
            mValueCallback = valueCallback;
            selectImgMax = selectImgMax > 1 ? selectImgMax : 1;
            goToPhotos(selectImgMax);
            return true;
        }
        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg) {
            mUploadMessage = uploadMsg;
            selectImgMax = 1;
            goToPhotos(selectImgMax);
        }
        //3.0--版本
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            openFileChooser(uploadMsg);
        }

        // For Android 4.1
        public void openFileChooser(ValueCallback uploadMsg, String acceptType,String capture) {
            openFileChooser(uploadMsg);
        }
        /**
         * 进入本地图库
         * @param select_image_max //设置选取最大值
         */
        private void goToPhotos(int select_image_max) {
            Intent i;
//            if (photosType <= 0) {//进入自定义图库
//                i = new Intent(WebViewActivity.this, MediaSelectActivity.class);
//                i.putExtra("select_mode", 2);
//                i.putExtra("select_image_max", select_image_max);
//                WebViewActivity.this.startActivityForResult(i, FILECHOOSER_RESULTCODE);
//            } else {//进入系统图库
                i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                WebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
//            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mActProjectDetailWebviewPgbProgress.setVisibility(View.GONE);
            } else {
                if (mActProjectDetailWebviewPgbProgress.getVisibility() == View.GONE) {
                    mActProjectDetailWebviewPgbProgress.setVisibility(View.VISIBLE);
                }
                mActProjectDetailWebviewPgbProgress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mActProjectDetailWebviewWeb.canGoBack()) {
            mActProjectDetailWebviewWeb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initTitle() {
        if (mStrTitle != null) {
            mTvTitle.setText(mStrTitle);
        }
    }

    protected void loadArticleDetail(String articleId) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "show_article");
        mapData.put("id", articleId);
        RequestModel model = new RequestModel(mapData);
        SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {
            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                Show_ArticleActModel actModel = JSON.parseObject(content, Show_ArticleActModel.class);
                return actModel;
            }

            @Override
            public void onStartInMainThread(Object result) {
                showLoadingDialog("请稍候...");
            }

            @Override
            public void onFinishInMainThread(Object result) {
                hideLoadingDialog();
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                Show_ArticleActModel actModel = (Show_ArticleActModel) result;
                if (actModel!=null) {
                    if (actModel.getResponse_code() == 1) {
                        if (TextUtils.isEmpty(actModel.getContent())) {
                            ToastUtils.showToast("没有文章详情!");
                        } else {
                            loadHtmlContent(actModel.getContent());
                        }

                        if (!TextUtils.isEmpty(actModel.getTitle())) {
                            mTvTitle.setText(actModel.getTitle());
                        } else {
                            if (!TextUtils.isEmpty(mStrTitle)) {
                                mTvTitle.setText(mStrTitle);
                            }
                        }
                    } else {
                        ToastUtils.showToast("获取文章详情失败!");
                    }
                }
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler, true);
    }

    @Override
    protected void onDestroy() {
         App.getApplication().getmRuntimeConfig().setProjectDetailWebviewActivity(null);
        if(mActProjectDetailWebviewWeb != null) {
            mActProjectDetailWebviewWeb.destroy();
            mActProjectDetailWebviewWeb = null;
        }
        super.onDestroy();
    }


    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                ToastUtils.showToast("需要SD卡");
                return;
            }
            DownloaderTask task=new DownloaderTask();
            task.execute(url);
        }

    }
    //内部类
    private class DownloaderTask extends AsyncTask<String, Void, String> {

        public DownloaderTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            String url=params[0];
            mFileName = url.substring(url.lastIndexOf("&")+1);
            mFileName = URLDecoder.decode(mFileName);

            File directory=Environment.getExternalStorageDirectory();
            File file=new File(directory, mFileName);
            if(file.exists()){
                return mFileName;
            }
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response = client.execute(get);
                if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                    HttpEntity entity = response.getEntity();
                    InputStream input = entity.getContent();

                    writeToSDCard(mFileName,input);

                    input.close();
                    return mFileName;
                }else{
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            closeProgressDialog();
            if(result==null){
                ToastUtils.showToast("连接错误！请稍后再试！");
                return;
            }
            ToastUtils.showToast("文件 "+mFileName+" 已保存到SD卡");
            File directory=Environment.getExternalStorageDirectory();
            File file=new File(directory,result);
            Intent intent = getFileIntent(file);

            startActivity(intent);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    private void showProgressDialog(){
        if(mDialog==null){
            mDialog = new ProgressDialog(this);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
            mDialog.setMessage("正在加载 ，请等待...");
            mDialog.setIndeterminate(false);//设置进度条是否为不明确
            mDialog.setCancelable(true);//设置进度条是否可以按退回键取消
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    mDialog=null;
                }
            });
            mDialog.show();

        }
    }

    public void writeToSDCard(String fileName,InputStream input){

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File directory=Environment.getExternalStorageDirectory();
            File file=new File(directory,fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[2048];
                int j = 0;
                while ((j = input.read(b)) != -1) {
                    fos.write(b, 0, j);
                }
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i("tag", "NO SDCard.");
        }
    }

    private void closeProgressDialog(){
        if(mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
    }


    public Intent getFileIntent(File file){
        //       Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    private String getMIMEType(File f){
        String type="";
        String fName=f.getName();
      /* 取得扩展名 */
        String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();

      /* 依扩展名的类型决定MimeType */
        if(end.equals("pdf")){
            type = "application/pdf";//
        }
        else if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
                end.equals("xmf")||end.equals("ogg")||end.equals("wav")){
            type = "audio/*";
        }
        else if(end.equals("3gp")||end.equals("mp4")){
            type = "video/*";
        }
        else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||
                end.equals("jpeg")||end.equals("bmp")){
            type = "image/*";
        }
        else if(end.equals("apk")){
        /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        }
        //      else if(end.equals("pptx")||end.equals("ppt")){
        //        type = "application/vnd.ms-powerpoint";
        //      }else if(end.equals("docx")||end.equals("doc")){
        //        type = "application/vnd.ms-word";
        //      }else if(end.equals("xlsx")||end.equals("xls")){
        //        type = "application/vnd.ms-excel";
        //      }
        else{
            //如果无法直接打开，就跳出软件列表给用户选择
            type="*/*";
        }
        return type;
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }
}
