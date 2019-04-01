package org.translator.model;

import javax.persistence.*;

@Entity
@Table(name = "FRASE")
public class Frase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "TRANSLATEFROM")
    private String translateFrom;

    @Column(name = "TRANSLATETO")
    private String translateTo;

    @Column(name = "RESULT")
    private String result;


    public Frase(String text, String translateFrom, String translateTo, String result) {
        this.text = text;
        this.translateFrom = translateFrom;
        this.translateTo = translateTo;
        this.result = result;
    }

    public Frase(long id, String text, String translateFrom, String translateTo, String result) {
        this.id = id;
        this.text = text;
        this.translateFrom = translateFrom;
        this.translateTo = translateTo;
        this.result = result;
    }

    public Frase() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslateFrom() {
        return translateFrom;
    }

    public void setTranslateFrom(String translateFrom) {
        this.translateFrom = translateFrom;
    }

    public String getTranslateTo() {
        return translateTo;
    }

    public void setTranslateTo(String translateTo) {
        this.translateTo = translateTo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
