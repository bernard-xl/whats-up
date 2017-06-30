package xl.application.social.whatsup;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.*;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Customized {@link RestTemplate} to support automatic cookies setting,
 * send {@code application/x-www-form-urlencoded} request by default,
 * and suppress any exception due to HTTP status code.
 */
public class WhatsUpHttpClient extends RestTemplate {

    private volatile List<String> cookies;

    public WhatsUpHttpClient() {
        super(Arrays.asList(new FormHttpMessageConverter(), new MappingJackson2HttpMessageConverter()));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        cookies = Collections.emptyList();
        setErrorHandler(new SuppressErrorHandler());
        setRequestFactory(requestFactory);
    }

    @Override
    protected <T extends Object> T doExecute(
            URI url,
            HttpMethod method,
            final RequestCallback requestCallback,
            final ResponseExtractor<T> responseExtractor) throws RestClientException {

        return super.doExecute(url, method, callbackWithCookies(requestCallback), extractCookies(responseExtractor));
    }

    private RequestCallback callbackWithCookies(RequestCallback callback) {
        return request -> {
            for (String cookie : cookies) {
                request.getHeaders().add("Cookie", cookie);
            }
            callback.doWithRequest(request);
        };
    }

    private <T> ResponseExtractor<T> extractCookies(ResponseExtractor<T> extractor) {
        return response -> {
            List<String> setCookies = response.getHeaders().get("Set-Cookie");
            if (setCookies != null && !setCookies.isEmpty()) {
                cookies = setCookies;
            }
            return extractor.extractData(response);
        };
    }

    private static class SuppressErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // do nothing
        }
    }
}
