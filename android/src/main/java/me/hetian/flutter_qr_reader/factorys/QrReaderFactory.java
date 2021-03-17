package me.hetian.flutter_qr_reader.factorys;

import android.content.Context;

import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import me.hetian.flutter_qr_reader.views.QrReaderView;

public class QrReaderFactory extends PlatformViewFactory {

    private PluginRegistry.Registrar registrar;
    private FlutterPlugin.FlutterPluginBinding binding;

    public QrReaderFactory(FlutterPlugin.FlutterPluginBinding binding) {
        super(StandardMessageCodec.INSTANCE);
        this.binding = binding;
    }

    public QrReaderFactory(PluginRegistry.Registrar registrar) {
        super(StandardMessageCodec.INSTANCE);
        this.registrar = registrar;
    }


    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new QrReaderView(context, binding, id, params);
//        return new QrReaderView(context, registrar, id, params);
    }

}
