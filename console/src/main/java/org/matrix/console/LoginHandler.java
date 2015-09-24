package org.matrix.console;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.matrix.androidsdk.HomeserverConnectionConfig;
import org.matrix.androidsdk.MXSession;
import org.matrix.androidsdk.rest.callback.SimpleApiCallback;
import org.matrix.androidsdk.rest.client.LoginRestClient;
import org.matrix.androidsdk.rest.model.MatrixError;
import org.matrix.androidsdk.rest.model.login.Credentials;
import org.matrix.androidsdk.ssl.CertUtil;
import org.matrix.androidsdk.ssl.Fingerprint;
import org.matrix.androidsdk.ssl.UnrecognizedCertificateException;


public class LoginHandler {
    private static final String LOG_TAG = "LoginHandler";

    public void login(Context ctx, final HomeserverConnectionConfig hsConfig, final String username, final String password,
                              final SimpleApiCallback<HomeserverConnectionConfig> callback) {
        final Context appCtx = ctx.getApplicationContext();
        LoginRestClient client = new LoginRestClient(hsConfig);

        client.loginWithPassword(username, password, new SimpleApiCallback<Credentials>() {
            @Override
            public void onSuccess(Credentials credentials) {
                Log.e(LOG_TAG, "client loginWithPassword succeeded.");
                hsConfig.setCredentials(credentials);
                MXSession session = Matrix.getInstance(appCtx).createSession(hsConfig);
                Matrix.getInstance(appCtx).addSession(session);
                callback.onSuccess(hsConfig);
            }

            @Override
            public void onNetworkError(final Exception e) {
                UnrecognizedCertificateException unrecCertEx = CertUtil.getCertificateException(e);
                if (unrecCertEx != null) {
                    final Fingerprint fingerprint = unrecCertEx.getFingerprint();
                    Log.d(LOG_TAG, "Found fingerprint: SHA-256: " + fingerprint.getBytesAsHexString());
                    // TODO: Handle this. For example by displaying a "Do you trust this cert?" dialog

                    UnrecognizedCertHandler h = new UnrecognizedCertHandler(hsConfig, fingerprint, false);
                    h.show(new UnrecognizedCertHandler.Callback() {
                        @Override
                        public void onAccept() {
                            login(appCtx, hsConfig, username, password, callback);
                        }

                        @Override
                        public void onIgnore() {
                            callback.onNetworkError(e);
                        }

                        @Override
                        public void onReject() {
                            callback.onNetworkError(e);
                        }
                    });
                } else {
                    callback.onNetworkError(e);
                }
            }

            @Override
            public void onUnexpectedError(Exception e) {
                callback.onUnexpectedError(e);
            }

            @Override
            public void onMatrixError(MatrixError e) {
                callback.onMatrixError(e);
            }
        });
    }
}
