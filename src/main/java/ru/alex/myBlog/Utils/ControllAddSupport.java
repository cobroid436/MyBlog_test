package ru.alex.myBlog.Utils;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lesha
 */

@Named
@RequestScoped
public class ControllAddSupport {

    // ======================================
    // =             Attributes             =
    // ======================================

    private String referer;

    private static URI uri = UriBuilder.fromUri("http://localhost:/RSCounters/rs/v1").port(8080).build();
    private static final String XML = "<page><url>index</url></page>";
    private static final String JSON = "{\"url\":\"index\"}";
    private static Client client = ClientBuilder.newClient();
    private static Response response;


    // ======================================
    // =           Public Methods           =
    // ======================================

    // ======================================
    // =          Getters & Setters         =
    // ======================================


    public String getReferer() {
        StringBuilder res = new StringBuilder();

        // Make JSON included URL page
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String url = request.getRequestURI();
        if (request.getQueryString() != null) {
            url = url + "?" + request.getQueryString();
        }
        JsonObject obj = Json.createObjectBuilder().add("url", url).build();

        // POSTs a pageCounter

        Response response = client.target(uri).path("pageCounterUp").request(MediaType.TEXT_PLAIN).post(Entity.entity(obj, MediaType.APPLICATION_JSON));
        if (Response.Status.OK.getStatusCode() == response.getStatusInfo().getStatusCode()) {
//            res.append("Ok : " + url + " result: ");
            res.append(response.readEntity(String.class));
        } else {
            res.append("ERR, ");
            res.append(response.getStatusInfo().getStatusCode());
            res.append(url);
        }

        return res.toString();
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public static String convertISto(InputStream stream, String charset) throws IOException {
        InputStreamReader test = new InputStreamReader(stream, charset);
        BufferedReader reader;
        StringBuilder builder = new StringBuilder();
        ;
        if (test != null) {
            reader = new BufferedReader(test);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line);
            }
            reader.close();
        }
        return builder.toString();
    }


    public static String getInfo(Map<String, Object> map) {

        StringBuilder req = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            req.append("KEY: " + key + "\n");

            Object value = entry.getValue();

                req.append("Value: " + value.toString() + "\n");

            req.append("-----------------------------------------------------------\n");
        }
        return req.toString();
    }

    public static HashMap<String, String> getFileInfo (Part part){
        String filename="";
        String extension="";
        HashMap<String,String> map=new HashMap<>();
        for (String pfile : part.getHeader("content-disposition")
                .split(";")) {

            if (pfile.trim().startsWith("filename")) {
                filename = pfile.substring(pfile.indexOf('=') + 1)
                        .trim().replace("\"", "");

                filename = filename.substring(filename.lastIndexOf('/') + 1)
                        .substring(filename
                                .lastIndexOf('\\') + 1); // MSIE fix
                extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
            }
        }
        map.put("filename",filename);
        map.put("extension",extension);
        return map;
    }
}