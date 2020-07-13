package com.son.SpringFilter.security.util;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            IOUtils.copy(request.getInputStream(), outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new ServletInputStream() {

            @Override
            public int readLine(byte[] b, int off, int len) throws IOException {
                return inputStream.read(b, off, len);
            }

            @Override
            public boolean isFinished() {
                return inputStream.available() > 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener arg0) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

}
