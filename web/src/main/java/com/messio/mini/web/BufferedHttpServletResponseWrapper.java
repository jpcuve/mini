package com.messio.mini.web;

import javax.activation.DataSource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * Created by jpc on 12/16/13.
 */
public class BufferedHttpServletResponseWrapper extends HttpServletResponseWrapper implements DataSource {
    private final String name;
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final ServletOutputStream servletOutputStream;
    private PrintWriter printWriter;

    public BufferedHttpServletResponseWrapper(final String name, HttpServletResponse response) {
        super(response);
        this.name = name;
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }
        };
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter == null) printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(servletOutputStream, getCharacterEncoding())));
        return printWriter;
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void flushBuffer() throws IOException {
        getWriter().flush();
    }

    public InputStream getInputStream() throws IOException {
        flushBuffer();
        getWriter().close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Override
    public String getName() {
        return name;
    }
}
