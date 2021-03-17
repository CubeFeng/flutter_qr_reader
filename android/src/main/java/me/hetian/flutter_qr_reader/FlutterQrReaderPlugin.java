package me.hetian.flutter_qr_reader;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.io.File;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import me.hetian.flutter_qr_reader.factorys.QrReaderFactory;

/**
 * FlutterQrReaderPlugin
 */
public class FlutterQrReaderPlugin implements FlutterPlugin, MethodCallHandler {

    private static final String CHANNEL_NAME = "me.hetian.flutter_qr_reader";
    private static final String CHANNEL_VIEW_NAME = "me.hetian.flutter_qr_reader.reader_view";

    public FlutterQrReaderPlugin() {
    }

    public FlutterQrReaderPlugin(Registrar registrar) {
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL_NAME);
        registrar.platformViewRegistry().registerViewFactory(CHANNEL_VIEW_NAME, new QrReaderFactory(registrar));
        final FlutterQrReaderPlugin instance = new FlutterQrReaderPlugin(registrar);
        channel.setMethodCallHandler(instance);
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        MethodChannel channel = new MethodChannel(binding.getBinaryMessenger(), CHANNEL_NAME);
        channel.setMethodCallHandler(this);

        binding.getPlatformViewRegistry().registerViewFactory(CHANNEL_VIEW_NAME, new QrReaderFactory(binding));
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {

    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("imgQrCode")) {
            imgQrCode(call, result);
        } else {
            result.notImplemented();
        }
    }

    @SuppressLint("StaticFieldLeak")
    void imgQrCode(MethodCall call, final Result result) {
        final String filePath = call.argument("file");
        if (filePath == null) {
            result.error("Not found data", null, null);
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            result.error("File not found", null, null);
        }

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                // 解析二维码/条码
                return QRCodeDecoder.syncDecodeQRCode(filePath);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (null == s) {
                    result.error("not data", null, null);
                } else {
                    result.success(s);
                }
            }
        }.execute(filePath);
    }
}
