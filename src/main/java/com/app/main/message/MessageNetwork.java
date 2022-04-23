/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.main.message;

import java.io.Serializable;

/**
 *
 * @author ronya
 */
public class MessageNetwork implements Serializable{

    public String message = "mensaje";
    public String nonSerializable = "nothing here";

    public MessageNetwork(String msj, String non) {
        this.message = msj;
        this.nonSerializable = non;
    }
    public MessageNetwork(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNonSerializable() {
        return nonSerializable;
    }

    public void setNonSerializable(String nonSerializable) {
        this.nonSerializable = nonSerializable;
    }

}
