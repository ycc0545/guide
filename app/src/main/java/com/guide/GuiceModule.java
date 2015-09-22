package com.guide;

import android.content.SharedPreferences;
import android.net.Uri;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.guide.net.HttpClientProvider;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.net.URI;

import roboguice.inject.SharedPreferencesProvider;
import roboguice.util.Ln;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HttpClient.class).toProvider(MyHttpClientProvider.class).in(Singleton.class);
        bind(SharedPreferences.class).annotatedWith(Names.named("ptr_time")).toProvider(new SharedPreferencesProvider("ptr_time"));


    }

    static class MyHttpClientProvider implements Provider<HttpClient> {
        @Inject
        private HttpClientProvider httpClientProvider;

        @Inject
        private UriInterceptor uriInterceptor;

        @Override
        public HttpClient get() {
            AbstractHttpClient httpClient = httpClientProvider.get();
            httpClient.addRequestInterceptor(uriInterceptor, 0);
            return httpClient;
        }
    }

    static class UriInterceptor implements HttpRequestInterceptor {

        @Override
        public void process(HttpRequest httpRequest, HttpContext httpContext) {
            try {
                if (httpRequest instanceof RequestWrapper) {

                    Object attr = httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                    if (attr instanceof HttpHost) {
                        HttpHost host = (HttpHost) attr;
                        // TODO check api host

                    }

                    RequestWrapper requestWrapper = (RequestWrapper) httpRequest;
                    final Uri uri = Uri.parse(requestWrapper.getURI().toASCIIString());
                    Uri.Builder builder = uri.buildUpon();
                    builder.appendQueryParameter("utm_version_code", String.valueOf(BuildConfig.VERSION_CODE));
                    builder.appendQueryParameter("utm_version_name", BuildConfig.VERSION_NAME);
//                    builder.appendQueryParameter("utm_channel", BaseConfig.channel);
//                    builder.appendQueryParameter("utm_device", BaseConfig.deviceId);
                    builder.appendQueryParameter("utm_platform", "android");

                    requestWrapper.setURI(new URI(builder.toString()));

                }
            } catch (Exception e) {
                Ln.e(e);
            }

        }


    }
}
