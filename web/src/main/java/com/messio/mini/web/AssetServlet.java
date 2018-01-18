package com.messio.mini.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messio.mini.bean.AssetManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jpc on 10/29/14.
 */
@WebServlet("/asset")
public class AssetServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetServlet.class);
    private final AssetManager assetManager;

    public AssetServlet(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final List<String> list = new ArrayList<>();
        for (final Part part: req.getParts()) if (part.getContentType() != null){
            System.out.println("part=" +  part.getContentType() + " name=" + part.getName());
            list.add(assetManager.saveData(part));
        }
        res.setContentType("application/json");
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(res.getOutputStream(), list);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        if (req.getUserPrincipal() == null) throw new ServletException("not authenticated");
        final String uuid = req.getParameter("uuid");
        if (uuid != null && uuid.length() > 0){
            assetManager.loadData(uuid, res);
        } else {
            res.getOutputStream().write("<!DOCTYPE html><html><head/><body><ul>".getBytes());
            for (final Map.Entry<String, String> entry: assetManager.getUuids().entrySet()){
                res.getOutputStream().write(String.format("<li><a href='?uuid=%s'>%s</a></li>", entry.getKey(), entry.getValue()).getBytes());
            }
            res.getOutputStream().write("</ul></body></html>".getBytes());
        }
    }
}
