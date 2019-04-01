package org.translator;

import org.translator.dao.FraseDAO;
import org.translator.dao.FraseDAOImpl;
import org.translator.model.Frase;
import org.translator.utils.YandexException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.List;

public class Translator {

    private static final String key = "trnsl.1.1.20190329T061250Z.49bd2b06f39642e7.41a34d2fdda78ebd3103a5d2b952ea8a366a55e0";
    private static final String yandex = "https://translate.yandex.net/api/v1.5/tr/translate?";

    public synchronized void translate() throws Exception {
        FraseDAO frase = new FraseDAOImpl();
        List<Frase> all = frase.getAll();
        for (Frase fr: all) {
            String text = fr.getText().replaceAll(" ", "+");
            String from = fr.getTranslateFrom();
            String to = fr.getTranslateTo();
            String stringUrl =
                    yandex + "lang=" + from + "-" + to +
                    "&key=" + key + "&text=" + text;
            URL url = new URL(stringUrl);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setConnectTimeout(1000);
            connection.connect();
            int code = connection.getResponseCode();
            System.out.println(code);
            if(code == 200) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(connection.getInputStream());
                Element element = doc.getDocumentElement();
                String translation = element.getTextContent();
                fr.setResult(translation);
                frase.add(fr);
            }else{
                switch(code){
                    case(400):
                        throw new YandexException("400: Bad request");
                    case(401):
                        throw new YandexException("401: Bad api-key");
                    case(402):
                        throw new YandexException("402: api-key blocked");
                    case(404):
                        throw new YandexException("404: Daily limit on translated text exceeded");
                    case(413):
                        throw new YandexException("413: Maximum text size exceeded");
                    case(422):
                        throw new YandexException("422: Text cannot be translated");
                    case(501):
                        throw new YandexException("501: Specified translation direction is not supported");
                }
            }
        }
    }


    public static void main(String[] args) {
        Translator tr = new Translator();
        try {
            tr.translate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}